package com.ucamp.fm.cmt.controller;

import com.ucamp.fm.cmt.dto.CmentDto;
import com.ucamp.fm.cmt.service.CmtService;
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

    // ?��? 리스??
    @GetMapping("/blogcmt")
    public String cmtlist(HttpSession session, HttpServletRequest req, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // ?�주�?th:if ?�용불�?..
        if(m_id==null){
            m_id = "";
        }

        String referer = req.getHeader("Referer"); // ?�더?�서 ?�전 ?�이지�??�는??
        //http://localhost:8085/blog/blogread/ ?�거?�버�?
        String c_tbset = referer.substring(36);

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.cmtlist(c_tbset));

        return "cmt/blogcmt";
    }

    @GetMapping("/blogtcmt")
    public String blogtcmt(HttpSession session, HttpServletRequest req, String c_tbset, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // ?�주�?th:if ?�용불�?..
        if(m_id==null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.tlist(c_tbset));

        return "cmt/blogtcmt";
    }

    // ?�고 버튼 ?�작, ?��? ?�고 카운??증�?.
    @RequestMapping("/dec/{c_no}")
    public String dec(@PathVariable int c_no,
                      HttpServletRequest req,
                      HttpSession session){
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // ?�더?�서 ?�전 ?�이지�??�는??

        if (!(m_id == null)) {
            cmtService.cmtdec(c_no);
            return "redirect:" + referer;
        }else {
            // 로그???�으�??�동.
            return "redirect:/login/login";
        }
    }
    

    @GetMapping("/cmtlistdec")
    public String cmtlist_dec(HttpSession session, Model model, CmentDto cmentDto){
        String m_id = (String) session.getAttribute("m_id");

        // ?�주�?th:if ?�용불�?..
        if(m_id==null){
            m_id = "";
        }
        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.cmtlistdec(cmentDto));
        return "cmt/blogcmtdec";
    }

    // ?��? ???�이지 불러?�기
    @GetMapping("/blogcmtform")
    public String cmtform(String c_tbset, String c_tbno, Model model){
        model.addAttribute("c_tbno", c_tbno);
        model.addAttribute("c_tbset", c_tbset);

        return "cmt/blogcmtform";
    }

    // ?��? ?�기
    @RequestMapping("/blogcmtwrite")
    public String cmtwrite(HttpSession session, HttpServletRequest req,
                           @RequestParam(value = "c_content") String c_content, String c_tbno, String c_tbset) {
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // ?�더?�서 ?�전 ?�이지�??�는??

        if(c_content == null){
            c_content = "";
        }
        // 로그??조건�?
        if (!(m_id == null)) {
            if(c_tbno==""){
                //http://localhost:8085/blog/blogread/ ?�거?�버�?
                String c_no = referer.substring(36);
                cmtService.cmtinsert(c_no, m_id, c_content);
            }else {
                cmtService.cmtinsert2(c_tbset, m_id, c_content, c_tbno);
            }
            return "redirect:" + referer;
        } else {
            // 2. 로그???�으�??�동.
            return "redirect:/login/login";
        }
    }
    
    // ?��? ??��
    // 커�??�티 글 ??��
    @GetMapping("/cmddelete/{c_no}")
    public String cmt_delete(HttpSession session, HttpServletRequest req,
                              Model model, @PathVariable String c_no) {

        // ?�션???�는 ?�이?�값 커�??�티 게시???�성?�에 ?�??
        String m_id = (String) session.getAttribute("m_id");

        model.addAttribute("m_id", m_id);

        String referer = req.getHeader("Referer");

        if (!(m_id == null)) {
            model.addAttribute("c_no", c_no);
            cmtService.cmtdelete(c_no);
            return "redirect:" + referer;
        } else {
            return "redirect:/login/login";
        }
    }
}
