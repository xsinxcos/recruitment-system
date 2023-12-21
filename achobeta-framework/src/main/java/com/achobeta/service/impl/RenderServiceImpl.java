package com.achobeta.service.impl;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.PictureUpdateDto;
import com.achobeta.domain.entity.Render;
import com.achobeta.domain.vo.GetPictureVo;
import com.achobeta.mapper.RenderMapper;
import com.achobeta.service.RenderService;
import com.achobeta.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 渲染页面的图片(Render)表服务实现类
 *
 * @author makejava
 * @since 2023-11-07 23:44:01
 */
@Service("renderService")
public class RenderServiceImpl extends ServiceImpl<RenderMapper, Render> implements RenderService {

    @Override
    public ResponseResult updatePicture(PictureUpdateDto pictureUpdateDto) {
        Render id = getById(pictureUpdateDto.getId());
        id.setUrl(pictureUpdateDto.getUrl());
        updateById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getPicture(Long id) {
        Render byId = getById(id);
        GetPictureVo vo = BeanCopyUtils.copyBean(byId, GetPictureVo.class);
        return ResponseResult.okResult(vo);
    }
}

