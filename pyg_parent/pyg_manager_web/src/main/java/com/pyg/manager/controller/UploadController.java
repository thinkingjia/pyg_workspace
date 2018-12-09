package com.pyg.manager.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.FastDFSClient;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${uploadServer}")
    private String uploadServer;

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
//           b.1.jpg   split  subStr
            String filename = file.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".")+1);
            String fileUrl = fastDFSClient.uploadFile(file.getBytes(), extName);
//             /group1/M00/00/00/wKgZhVv3f0qAJ-H-AAFP0yQoHiA752.jpg
            return new Result(true,uploadServer+fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
}
