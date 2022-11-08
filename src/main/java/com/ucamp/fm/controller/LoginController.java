package com.ucamp.fm.controller;

import com.ucamp.fm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
    MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
    

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
	@GetMapping("/join")
    public String join() {
        return "join";
    }
	
	@GetMapping("/join_Mod")
    public String join_Mod() {
        return "join_Mod";
    }
    
    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }
}
