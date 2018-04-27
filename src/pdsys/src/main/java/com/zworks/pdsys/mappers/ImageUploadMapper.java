package com.zworks.pdsys.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.ImageModel;

/**
 * @author: ZHAI
 * @version: 2018/04/26
 */
@Mapper
public interface ImageUploadMapper {
	
	void add(ImageModel imageModel);
}
