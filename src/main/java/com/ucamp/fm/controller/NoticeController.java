package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/noticelist")
    public String qnaForm() {
        return "noticebbs/noticelist";
    }

    @GetMapping("/noticewrite")
    public String qnaWrite() {
        return "noticebbs/noticewrite";
    }

    @GetMapping("/noticeread")
    public String qnaRead() {
        return "noticebbs/noticeread";
    }

}

