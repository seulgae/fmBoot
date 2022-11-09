package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping("/blogwrite")
    public String blog_write() {
        return "blogbbs/blogwrite";
    }

    @GetMapping("/blogread")
    public String blog_read() {
        return "blogbbs/blogread";
    }

    @GetMapping("/blogmod")
    public String blog_mod() {
        return "blogbbs/blogmod";
    }
}
