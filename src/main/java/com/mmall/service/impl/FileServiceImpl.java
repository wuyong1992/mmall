package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理实现类
 * Created by Administrator on 2017/6/21.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        //文件原始名称
        String filename = file.getOriginalFilename();
        //获取文件的类型.jpg或.png，+1可以不要“.”，只有jpg或png
        String fileExtensionName = filename.substring(filename.lastIndexOf(".") + 1);
        //文件的上传名字
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        //记录日志，{}站位
        logger.info("开始上传，上传的原始文件名是：{}，上传路径是：{}，新文件名是：{}",filename,path,uploadFileName);

        //创建文件夹
        File fileDir = new File(path);
        if (!fileDir.exists()){
            //不存在，创建
            //赋予权限，避免发布tomcat的权限却没有写的权限
            fileDir.setWritable(true);
            //mkdirs,可以多层创建
            fileDir.mkdirs();
        }
        //创建文件
        File targetFile = new File(path, uploadFileName);

        try {
            //生成文件
            file.transferTo(targetFile);
            //将文件上传到ftp
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完毕，将本地的upload文件夹下的文件删除
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常：",e);
            return null;
        }
        //返回目标文件名
        return targetFile.getName();
    }

}
