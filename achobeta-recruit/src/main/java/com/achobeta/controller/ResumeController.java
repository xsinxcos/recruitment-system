package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.ResumeSaveDto;
import com.achobeta.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping()
    @SystemLog(BusinessName = "简历保存")
    public ResponseResult saveResume(@RequestBody ResumeSaveDto resumeSaveDto){
        return resumeService.saveResume(resumeSaveDto);
    }

    @GetMapping("/detail")
    @SystemLog(BusinessName = "获取用户简历")
    public ResponseResult getResume(){
        return resumeService.getResume();
    }

    @PostMapping("/withdraw")
    @SystemLog(BusinessName = "用户简历撤回")
    public ResponseResult withdrawResume(){
        return resumeService.withdrawResume();
    }
}
