package com.ucamp.fm.controller;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck (String m_id){
        int flag = memberService.idCheck(m_id);
        return flag;
    }

    @RequestMapping("/joinInsert")
    public String joinInsert (MemberDto member){
        member.setM_pw(passwordEncoder.encode(member.getM_pw()));
        memberService.join(member);
        return "redirect:/";
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public String loginCheck (String m_id, String m_pw, Model model, HttpServletRequest request){
        if(!passwordEncoder.matches(m_pw,memberService.getPw(m_id))){
            return "0";
        }else{
            return "1";
        }
    }

    @RequestMapping("/loginOk")
    public String loginOk (String m_id, HttpServletRequest request){
        request.getSession().setAttribute("m_id", m_id);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout (HttpServletRequest request){
        request.getSession().removeAttribute("m_id");
        return "redirect:/";
    }

}

