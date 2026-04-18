package com.ucamp.fm.cmt.controller;

import com.ucamp.fm.cmt.dto.CmentDto;
import com.ucamp.fm.cmt.service.CmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 블로그/팀 게시글에 연결된 댓글 기능을 담당한다.
 */
@Controller
@RequestMapping("/cmt")
public class CmtController {

    @Autowired
    CmtService cmtService;

    /**
     * 일반 댓글 목록을 조회한다.
     */
    @GetMapping("/blogcmt")
    public String cmtlist(HttpSession session, HttpServletRequest req, Model model){
        String m_id = (String) session.getAttribute("m_id");
        if(m_id == null){
            m_id = "";
        }

        String referer = req.getHeader("Referer");
        String c_tbset = referer.substring(36);

        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.cmtlist(c_tbset));
        return "cmt/blogcmt";
    }

    /**
     * 대댓글 목록을 조회한다.
     */
    @GetMapping("/blogtcmt")
    public String blogtcmt(HttpSession session, HttpServletRequest req, String c_tbset, Model model){
        String m_id = (String) session.getAttribute("m_id");
        if(m_id == null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.tlist(c_tbset));
        return "cmt/blogtcmt";
    }

    /**
     * 댓글 신고 수를 증가시킨다.
     */
    @RequestMapping("/dec/{c_no}")
    public String dec(@PathVariable int c_no, HttpServletRequest req, HttpSession session){
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer");

        if (m_id != null) {
            cmtService.cmtdec(c_no);
            return "redirect:" + referer;
        } else {
            return "redirect:/login/login";
        }
    }

    /**
     * 신고된 댓글 목록을 조회한다.
     */
    @GetMapping("/cmtlistdec")
    public String cmtlist_dec(HttpSession session, Model model, CmentDto cmentDto){
        String m_id = (String) session.getAttribute("m_id");
        if(m_id == null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.cmtlistdec(cmentDto));
        return "cmt/blogcmtdec";
    }

    /**
     * 댓글 작성 폼 조각을 반환한다.
     */
    @GetMapping("/blogcmtform")
    public String cmtform(String c_tbset, String c_tbno, Model model){
        model.addAttribute("c_tbno", c_tbno);
        model.addAttribute("c_tbset", c_tbset);
        return "cmt/blogcmtform";
    }

    /**
     * 댓글 또는 대댓글을 저장한다.
     */
    @RequestMapping("/blogcmtwrite")
    public String cmtwrite(HttpSession session, HttpServletRequest req,
                           @RequestParam(value = "c_content") String c_content, String c_tbno, String c_tbset) {
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer");

        if(c_content == null){
            c_content = "";
        }

        if (m_id != null) {
            if(c_tbno == ""){
                String c_no = referer.substring(36);
                cmtService.cmtinsert(c_no, m_id, c_content);
            } else {
                cmtService.cmtinsert2(c_tbset, m_id, c_content, c_tbno);
            }
            return "redirect:" + referer;
        } else {
            return "redirect:/login/login";
        }
    }

    /**
     * 댓글을 삭제한다.
     */
    @GetMapping("/cmddelete/{c_no}")
    public String cmt_delete(HttpSession session, HttpServletRequest req, Model model, @PathVariable String c_no) {
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id", m_id);

        String referer = req.getHeader("Referer");
        if (m_id != null) {
            model.addAttribute("c_no", c_no);
            cmtService.cmtdelete(c_no);
            return "redirect:" + referer;
        } else {
            return "redirect:/login/login";
        }
    }
}
