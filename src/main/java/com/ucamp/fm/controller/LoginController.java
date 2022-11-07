package com.ucamp.fm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.service.MemberService;


@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
	
	@PostMapping("/test")
	public void test (MemberDto memberDto) {
		memberService.test(memberDto);
	}

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
	@PostMapping("/join")
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
