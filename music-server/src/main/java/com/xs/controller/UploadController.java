package com.xs.controller;

import com.xs.enums.FilePathEnum;
import com.xs.strategy.context.UploadStrategyContext;
import com.xs.vo.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UploadController {

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 上传头像
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("pic") MultipartFile file) {
        return R.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath()));
    }

    /**
     * 上传mp3
     */
    @PostMapping("/uploadAudio")
    public R<String> uploadAudio(@RequestParam("mp3") MultipartFile file) {
        return R.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.VOICE.getPath()));
    }
}
