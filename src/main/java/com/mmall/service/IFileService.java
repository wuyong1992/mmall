package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理接口
 * Created by Administrator on 2017/6/21.
 */
public interface IFileService {


    public String upload(MultipartFile file, String path);

}
