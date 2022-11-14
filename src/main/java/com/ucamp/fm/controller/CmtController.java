package com.ucamp.fm.controller;

import com.ucamp.fm.dto.CmentDto;
import com.ucamp.fm.service.CmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cmt")
public class CmtController {

    @Autowired
    CmtService cmtService;

    // 댓글 리스트
    @GetMapping("/blogcmt")
    public String cmtlist(HttpSession session, Model model){
        model.addAttribute("cments", cmtService.cmtlist());

        return "cmtbbs/blogcmt";
    }

    // 댓글 폼 페이지 불러오기
    @GetMapping("/blogcmtform")
    public String cmtform(){

        return "cmtbbs/blogcmtform";
    }

    // 댓글 쓰기
    @RequestMapping("/blogcmtwrite")
    public String cmtwrite(HttpSession session, HttpServletRequest req,
                           @RequestParam(value = "c_content") String c_content) {
        String c_c_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.

        //http://localhost:8085/blog/blogread/ 제거해버림
        String c_no = referer.substring(36);

        if(c_content == null){
            c_content = "";
        }
        // 로그인 조건문
        if (!(c_c_id == null)) {
            cmtService.cmtinsert(c_no, c_c_id, c_content);
            return "redirect:" + referer;
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }
    }
}
