package com.ucamp.fm.controller;

import com.ucamp.fm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @RequestMapping("/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/join_Mod")
    public String join_Mod() {
        return "member/join_Mod";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "member/mypage";
    }

    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck(String m_id){
        int flag = memberService.idCheck(m_id);
        return flag;
    }
}
