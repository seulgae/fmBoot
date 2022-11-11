package com.ucamp.fm.controller;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


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

    // 로그아웃 추가
    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().removeAttribute("m_id");
        return "redirect:/";
    }

    @RequestMapping("/join")
    public String join () {
        return "member/join";
    }

    @RequestMapping("/join_Mod")
    public String join_Mod () {
        return "member/join_Mod";
    }

    @RequestMapping("/mypage")
    public String mypage (HttpServletRequest request, Model model) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{

            model.addAttribute("member", memberService.getMember(m_id));
            model.addAttribute("list", memberService.getList());

            return "member/mypage";
        }
    }
    //구장 신청 제출
    @RequestMapping("/mypage_request")
    public String mypage_request(Model model, HttpServletRequest request) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            return "member/mypage_request";
        }
    }

    //구장 신청 제출
    @RequestMapping("/mypage_request.do")
    public String mypage_request_do(Model model, HttpServletRequest request, PlaceDto placeDto) {
        String m_id = (String) request.getSession().getAttribute("m_id");


        if(placeDto.getP_op1() == null){
           placeDto.setP_op1("0");
        }
        if(placeDto.getP_op2() == null){
            placeDto.setP_op2("0");
        }
        if(placeDto.getP_op3() == null){
            placeDto.setP_op3("0");
        }
        if(placeDto.getP_op4() == null){
            placeDto.setP_op4("0");
        }
        if(placeDto.getP_op5() == null){
            placeDto.setP_op5("0");
        }
        if(placeDto.getP_op6() == null){
            placeDto.setP_op6("0");
        }

        memberService.mypage_request(placeDto);

        return "redirect:/login/mypage";
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

