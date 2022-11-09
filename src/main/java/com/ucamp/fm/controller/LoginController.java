package com.ucamp.fm.controller;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @RequestMapping("/join")
    public String join () {
        return "member/join";
    }

    @GetMapping("/join_Mod")
    public String join_Mod () {
        return "member/join_Mod";
    }

    @GetMapping("/mypage")
    public String mypage () {
        return "member/mypage";
    }

    @GetMapping("/mypage_request")
    public String pay_reservation(Model model) {
        return "member/mypage_request";
    }

    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck (String m_id){
        int flag = memberService.idCheck(m_id);
        return flag;
    }

    @RequestMapping("/joinInsert")
    public String joinInsert (MemberDto member){
        memberService.join(member);
        return "redirect:/";
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public String loginCheck (String m_id, String m_pw, Model model, HttpServletRequest request){
        return Integer.toString(memberService.loginCheck(m_id, m_pw));
    }

    @RequestMapping("/loginOk")
    public String loginOk (String m_id, HttpServletRequest request){
        request.getSession().setAttribute("m_id", m_id);
        return "redirect:/";
    }

}
