package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/write")
public class WriteController {
	
    @GetMapping("/write")
    public String noticeForm() {
        return "write";
    }
    
}
