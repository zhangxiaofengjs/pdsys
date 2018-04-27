package com.zworks.pdsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zworks.pdsys.mappers.ImageUploadMapper;
import com.zworks.pdsys.models.ImageModel;

@Service
public class ImageUploadService {
	@Autowired
    private ImageUploadMapper imageUploadMapper;
	
	public void add(ImageModel imageModel) {
		imageUploadMapper.add(imageModel);
	}
}
