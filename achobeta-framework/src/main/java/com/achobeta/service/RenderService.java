package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.PictureUpdateDto;
import com.achobeta.domain.entity.Render;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 渲染页面的图片(Render)表服务接口
 *
 * @author makejava
 * @since 2023-11-07 23:43:59
 */
public interface RenderService extends IService<Render> {

    ResponseResult updatePicture(PictureUpdateDto pictureUpdateDto);

    ResponseResult getPicture(Long id);
}

