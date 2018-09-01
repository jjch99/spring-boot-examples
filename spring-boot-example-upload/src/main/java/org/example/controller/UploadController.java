package org.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.example.request.HelloRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UploadController {

    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object upload(MultipartFile file, String hash) {

        try {
            File tmp = new File(SystemUtils.getUserHome(), "tmp");
            if (!tmp.exists()) {
                tmp.mkdirs();
            }

            log.info(file.getName()); // 参数名
            log.info(file.getOriginalFilename()); // 原始文件名
            log.info(file.getContentType());
            log.info(String.valueOf(file.isEmpty()));
            log.info(hash);

            File destFileName = new File(tmp, file.getOriginalFilename());
            file.transferTo(destFileName);
            return "ok";
        } catch (IOException e) {
            log.error("", e);
            return "fail";
        }

    }

    @ResponseBody
    @PostMapping(value = "/batch-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String batchUpload(MultipartHttpServletRequest request) {
        log.info(request.getContentType());

        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            List<MultipartFile> fileList = map.get(key);
            for (int i = 0; i < fileList.size(); i++) {
                MultipartFile file = fileList.get(i);
                log.info("-----------------");
                log.info(file.getName());
                log.info(file.getOriginalFilename());
                log.info(file.getContentType());
            }
        }

        return "ok";
    }

    @ResponseBody
    @PostMapping(value = "/batch-upload-with-json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String batchUploadWithJson(@RequestPart("hello") HelloRequest request, @RequestParam MultipartFile[] file) {
        log.info(JSON.toJSONString(request));

        for (int i = 0; i < file.length; i++) {
            MultipartFile f = file[i];
            log.info(String.valueOf(i));
            log.info(f.getName());
            log.info(f.getOriginalFilename());
            log.info(f.getContentType());
        }
        return "ok";
    }

    @ResponseBody
    @PostMapping(value = "/batch-upload-with-json-2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String batchUploadWithJson2(@RequestParam String hello, @RequestParam MultipartFile[] file) {

        log.info(hello);

        HelloRequest helloRequest = JSON.parseObject(hello, HelloRequest.class);
        log.info(JSON.toJSONString(helloRequest));

        for (int i = 0; i < file.length; i++) {
            MultipartFile f = file[i];
            log.info(String.valueOf(i));
            log.info(f.getName());
            log.info(f.getOriginalFilename());
            log.info(f.getContentType());
        }
        return "ok";
    }

    @ResponseBody
    @PostMapping(value = "/batch-upload-with-param", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String batchUploadWithParam(@ModelAttribute HelloRequest request, @RequestParam MultipartFile[] file) {
        log.info(JSON.toJSONString(request));

        for (int i = 0; i < file.length; i++) {
            MultipartFile f = file[i];
            log.info(String.valueOf(i));
            log.info(f.getName());
            log.info(f.getOriginalFilename());
            log.info(f.getContentType());
        }
        return "ok";
    }

}
