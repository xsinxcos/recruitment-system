package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.ResumeListDto;
import com.achobeta.domain.entity.Resume;
import com.achobeta.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping("/list")
    @SystemLog(BusinessName = "所有简历展示")
    public ResponseResult resumeList(@RequestBody ResumeListDto resumeListDto){
        return resumeService.listResume(resumeListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "指定简历详情")
    public ResponseResult getResume(@PathVariable Long id){
        return resumeService.getResumeById(id);
    }

    @PostMapping()
    @SystemLog(BusinessName = "管理员更新简历")
    public ResponseResult updateResumeByAdmin(@RequestBody Resume resume){
        return resumeService.updateResumeByAdmin(resume);
    }

    @GetMapping("/analysis")
    @SystemLog(BusinessName = "数据分析")
    public ResponseResult analysisResume(String period){
        return resumeService.analysisResume(period);
    }
}
