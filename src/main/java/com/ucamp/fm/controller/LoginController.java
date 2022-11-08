package com.ucamp.fm.controller;

import com.ucamp.fm.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
    MemberServiceImpl memberService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
	
	@GetMapping("/test")
	public String test () {
		String mid = "123";
        memberService.test(mid);
        System.out.println("완료됨");
        return "redirect:/";
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
