package com.itheima.reggie.controller;

import com.itheima.reggie.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author 小空
 * @create 2022-05-15 15:03
 * @description 菜品图片上传与下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class ImgUploadAnDownloadController {
    @Value("${reggie.img}")
    private String imgPath;

    /**
     * 获取文件的原始文件名, 通过原始文件名获取文件后缀
     * 通过UUID重新声明文件名, 文件名称重复造成文件覆盖
     * 创建文件存放目录
     * 将上传的临时文件转存到指定位置
     *
     * @param file MultipartFile对象
     * @return 图片文件名
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        //获取源文件名称和源文件名称后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //通过UUID指定上传的图片路径
        String uploadFileName = UUID.randomUUID().toString() + suffix;
        //判断存储菜品图片的路径是否存在,不存在则创建
        createDishImgDirectory(imgPath);
        //将源文件保存到指定目录
        try {
            file.transferTo(new File(imgPath, uploadFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将菜品图片文件名返回,因为需要将图片名称保存至数据库中
        return R.success(uploadFileName);
    }

    /**
     * 下载图片
     * 1:定义输入流，通过输入流读取文件内容
     * 2:通过response对象，获取到输出流
     * 3:通过response对象设置响应数据格式(image/jpeg)
     * 4:通过输入流读取文件数据，然后通过上述的输出流写回浏览器
     * 5:关闭资源
     *
     * @param response Response对象
     * @param name     图片名称
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(imgPath, name)))) {
            //获取到输出流对象
            ServletOutputStream os = response.getOutputStream();
            //通过response对象设置响应数据格式(image/jpeg)
            response.setContentType("image/jpeg");
            //对图片数据进行[边读边写]操作
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建指定的目录
     *
     * @param directory 目录(路径)
     */
    protected void createDishImgDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
