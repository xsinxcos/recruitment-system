package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.ResumeListDto;
import com.achobeta.domain.dto.ResumeSaveDto;
import com.achobeta.domain.entity.Resume;
import com.achobeta.domain.entity.UserResume;
import com.achobeta.domain.vo.AnalysisResumeVo;
import com.achobeta.domain.vo.GetResumeVo;
import com.achobeta.domain.vo.ListResumeVo;
import com.achobeta.enums.AppHttpCodeEnum;
import com.achobeta.mapper.ResumeMapper;
import com.achobeta.service.ResumeService;
import com.achobeta.service.UserResumeService;
import com.achobeta.utils.BeanCopyUtils;
import com.achobeta.utils.RedisCache;
import com.achobeta.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 简历表(Resume)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 00:27:32
 */
@Service("resumeService")
@RequiredArgsConstructor
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
    private final RedisCache redisCache;

    private final UserResumeService userResumeService;
    @Override
    public ResponseResult saveResume(ResumeSaveDto resumeSaveDto) {
        //获取userId
        Long userId = SecurityUtils.getUserId();
        //查找简历是否存在
        LambdaQueryWrapper<UserResume> userResumeWrapper = new LambdaQueryWrapper<>();
        userResumeWrapper.eq(UserResume::getUserId ,userId);
        List<UserResume> userResumes = userResumeService.list(userResumeWrapper);
        Resume draftOfResume = getDraftOfResume(userResumes);
        Resume submitOfResume = getSubmitOfResume(userResumes);
        if(Objects.nonNull(submitOfResume)){
            throw new RuntimeException("请先撤回简历再提交");
        }
        //todo 处理未撤回利用接口恶意重复投递情况
        Resume resume = BeanCopyUtils.copyBean(resumeSaveDto, Resume.class);
        if(Objects.nonNull(draftOfResume)){
            resume.setId(draftOfResume.getId());
            updateById(resume);
        }else {
            //保存简历
            LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
            save(resume);
            //将简历与用户关联起来
            Long resumeId = resume.getId();
            userResumeService.save(new UserResume(userId ,resumeId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getResume() {
        //获取userId
        Long userId = SecurityUtils.getUserId();
        //获取简历
        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(userId),UserResume::getUserId ,userId);
        List<UserResume> userResumes = userResumeService.list(wrapper);
        Resume resume = getSubmitOfResume(userResumes);
        if(Objects.isNull(resume)){
            resume = getDraftOfResume(userResumes);
        }
        if(Objects.isNull(resume)){
            return ResponseResult.errorResult(AppHttpCodeEnum.RESUME_ISNULL ,"简历不存在");
        }
        GetResumeVo getResumeVo = BeanCopyUtils.copyBean(resume, GetResumeVo.class);
        return ResponseResult.okResult(getResumeVo);
    }

    @Override
    public ResponseResult withdrawResume() {
        Long userId = SecurityUtils.getUserId();
        //获取简历
        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(userId),UserResume::getUserId ,userId);
        List<UserResume> list = userResumeService.list(wrapper);
        Resume resume = getSubmitOfResume(list);

        resume.setStatus(SystemConstants.RESUME_STATUS_DRAFT);
        //更新为草稿状态
        updateById(resume);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listResume(ResumeListDto resumeListDto) {
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(!Objects.isNull(resumeListDto.getPeriod()),Resume::getId ,resumeListDto.getPeriod())
                .like(!Objects.isNull(resumeListDto.getName()),Resume::getName ,resumeListDto.getName())
                .like(!Objects.isNull(resumeListDto.getSubject()),Resume::getSubject ,resumeListDto.getSubject())
                .like(!Objects.isNull(resumeListDto.getStation()),Resume::getStation ,resumeListDto.getStation())
                .eq(!Objects.isNull(resumeListDto.getGrade()),Resume::getGrade ,resumeListDto.getGrade())
                .eq(!Objects.isNull(resumeListDto.getStatus()),Resume::getStatus,resumeListDto.getStatus());
        List<Resume> resumes = list(wrapper);
        List<ListResumeVo> vos = BeanCopyUtils.copyBeanList(resumes, ListResumeVo.class);
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult getResumeById(Long id) {
        Resume byId = getById(id);
        GetResumeVo vo = BeanCopyUtils.copyBean(byId, GetResumeVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateResumeByAdmin(Resume resume) {
        updateById(resume);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult analysisResume(String period) {
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(!Objects.isNull(period) ,Resume::getId ,period);
        List<Resume> resumes = list(wrapper);
        List<AnalysisResumeVo> vos = BeanCopyUtils.copyBeanList(resumes, AnalysisResumeVo.class);
        return ResponseResult.okResult(vos);
    }


    private Resume getDraftOfResume(List<UserResume> userResumes){
        List<Long> list = userResumes.stream()
                .map(UserResume::getResumeId)
                .collect(Collectors.toList());
        if(list.size() == 0) return null;
        List<Resume> resumes = getBaseMapper().selectBatchIds(list);
        for (Resume resume : resumes) {
            if(resume.getStatus().equals(SystemConstants.RESUME_STATUS_DRAFT))
                return resume;
        }
        return null;
    }

    private Resume getSubmitOfResume(List<UserResume> userResumes){
        List<Long> list = userResumes.stream()
                .map(UserResume::getResumeId)
                .collect(Collectors.toList());
        if(list.size() == 0) return null;
        List<Resume> resumes = getBaseMapper().selectBatchIds(list);
        for (Resume resume : resumes) {
            if(resume.getStatus().equals(SystemConstants.RESUME_STATUS_SUBMIT))
                return resume;
        }
        return null;
    }
}

