package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.ResumeListDto;
import com.achobeta.domain.dto.ResumeSaveDto;
import com.achobeta.domain.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 简历表(Resume)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 00:27:31
 */
public interface ResumeService extends IService<Resume> {

    ResponseResult saveResume(ResumeSaveDto resumeSaveDto);

    ResponseResult getResume();

    ResponseResult withdrawResume();

    ResponseResult listResume(ResumeListDto resumeListDto);

    ResponseResult getResumeById(Long id);

    ResponseResult updateResumeByAdmin(Resume resume);

    ResponseResult analysisResume(String period);
}

