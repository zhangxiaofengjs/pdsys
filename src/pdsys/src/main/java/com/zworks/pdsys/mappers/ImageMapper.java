package com.zworks.pdsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zworks.pdsys.models.ImageModel;

/**
 * @author: ZHAI
 * @version: 2018/04/26
 */
@Mapper
public interface ImageMapper {
	void add(ImageModel imageModel);

	List<ImageModel> queryList(ImageModel image);
}
