package com.zworks.pdsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.ImageMapper;
import com.zworks.pdsys.models.ImageModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/05/17
 */
@Service
public class ImageService {
	@Autowired
    private ImageMapper imageMapper;
	
	public List<ImageModel> queryList(ImageModel image) {
		return imageMapper.queryList(image);
	}

	public void add(ImageModel image) {
		imageMapper.add(image);
	}
}
