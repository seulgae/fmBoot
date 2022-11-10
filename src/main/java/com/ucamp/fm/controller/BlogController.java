package com.ucamp.fm.controller;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    // 커뮤니티 글쓰기 페이지 이동
    @GetMapping("/blogwrite")
    public String blog_writeForm() {
        return "blogbbs/blogwrite";
    }

    // 커뮤니티 글쓰기 폼 전송
    @PostMapping("/blogwriteAction")
    public String blog_write(HttpServletRequest req, @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title")String tb_title,
                             @RequestParam("tb_content")String tb_content) throws
                             IllegalStateException, IOException {
        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog";

        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(PATH + tb_thum.getOriginalFilename()));
        }

        blogService.bloginsert(new BlogDto(tb_title, tb_content, tb_thum.getOriginalFilename()));

        return "blogbbs/blogread";
    }

    @RequestMapping("/blogread")
    public String blog_read() {
        return "blogbbs/blogread";
    }

    @RequestMapping("/blogmod")
    public String blog_mod() {
        return "blogbbs/blogmod";
    }
}
