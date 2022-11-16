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

    // 댓글 신고 카운트 증가.

    /*@GetMapping("/blogcmt")
    @ResponseBody
    public String cmtlist(HttpSession session, HttpServletRequest req, Model model, String c_tbset){
        String m_id = (String) session.getAttribute("m_id");

        // 안주면 th:if 사용불가..
        if(m_id==null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.cmtlist(c_tbset));

        return "cmtbbs/blogcmt";
    }*/

    // 댓글 리스트
    @GetMapping("/blogcmt")
    public String cmtlist(HttpSession session, HttpServletRequest req, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // 안주면 th:if 사용불가..
        if(m_id==null){
            m_id = "";
        }

        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
        //http://localhost:8085/blog/blogread/ 제거해버림
        String c_tbset = referer.substring(36);

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.cmtlist(c_tbset));

        return "cmtbbs/blogcmt";
    }

    @GetMapping("/blogtcmt")
    public String blogtcmt(HttpSession session, HttpServletRequest req, String c_tbset, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // 안주면 th:if 사용불가..
        if(m_id==null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.tlist(c_tbset));

        return "cmtbbs/blogtcmt";
    }

    // 신고 버튼 동작, 댓글 신고 카운트 증가.
    @RequestMapping("/dec/{c_no}")
    public String dec(@PathVariable int c_no,
                      HttpServletRequest req,
                      HttpSession session){
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.

        if (!(m_id == null)) {
            cmtService.cmtdec(c_no);
            return "redirect:" + referer;
        }else {
            // 로그인 폼으로 이동.
            return "redirect:/login/login";
        }
    }
    

    @GetMapping("/cmtlistdec")
    public String cmtlist_dec(HttpSession session, Model model, CmentDto cmentDto){
        String m_id = (String) session.getAttribute("m_id");

        // 안주면 th:if 사용불가..
        if(m_id==null){
            m_id = "";
        }
        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.cmtlistdec(cmentDto));
        return "cmtbbs/blogcmtdec";
    }

    // 댓글 폼 페이지 불러오기
    @GetMapping("/blogcmtform")
    public String cmtform(String c_tbset, String c_tbno, Model model){
        model.addAttribute("c_tbno", c_tbno);
        model.addAttribute("c_tbset", c_tbset);

        return "cmtbbs/blogcmtform";
    }

    // 댓글 쓰기
    @RequestMapping("/blogcmtwrite")
    public String cmtwrite(HttpSession session, HttpServletRequest req,
                           @RequestParam(value = "c_content") String c_content, String c_tbno, String c_tbset) {
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.

        if(c_content == null){
            c_content = "";
        }
        // 로그인 조건문
        if (!(m_id == null)) {
            if(c_tbno==""){
                //http://localhost:8085/blog/blogread/ 제거해버림
                String c_no = referer.substring(36);
                cmtService.cmtinsert(c_no, m_id, c_content);
            }else {
                cmtService.cmtinsert2(c_tbset, m_id, c_content, c_tbno);
            }
            return "redirect:" + referer;
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }
    }
    
    // 댓글 삭제
    // 커뮤니티 글 삭제
    @GetMapping("/cmddelete/{c_no}")
    public String cmt_delete(HttpSession session, HttpServletRequest req,
                              Model model, @PathVariable String c_no) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String m_id = (String) session.getAttribute("m_id");

        model.addAttribute("m_id", m_id);

        String referer = req.getHeader("Referer");
        System.out.println(referer);

        if (!(m_id == null)) {
            model.addAttribute("c_no", c_no);
            cmtService.cmtdelete(c_no);
            return "redirect:" + referer;
        } else {
            return "redirect:/login/login";
        }
    }
}
