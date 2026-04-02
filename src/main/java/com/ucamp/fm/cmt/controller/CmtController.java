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

    // ?“ê? ë¦¬ìŠ¤??
    @GetMapping("/blogcmt")
    public String cmtlist(HttpSession session, HttpServletRequest req, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // ?ˆì£¼ë©?th:if ?¬ìš©ë¶ˆê?..
        if(m_id==null){
            m_id = "";
        }

        String referer = req.getHeader("Referer"); // ?¤ë”?ì„œ ?´ì „ ?˜ì´ì§€ë¥??½ëŠ”??
        //http://localhost:8085/blog/blogread/ ?œê±°?´ë²„ë¦?
        String c_tbset = referer.substring(36);

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.cmtlist(c_tbset));

        return "cmtbbs/blogcmt";
    }

    @GetMapping("/blogtcmt")
    public String blogtcmt(HttpSession session, HttpServletRequest req, String c_tbset, Model model){
        String m_id = (String) session.getAttribute("m_id");

        // ?ˆì£¼ë©?th:if ?¬ìš©ë¶ˆê?..
        if(m_id==null){
            m_id = "";
        }

        model.addAttribute("m_id", m_id);

        model.addAttribute("cments", cmtService.tlist(c_tbset));

        return "cmtbbs/blogtcmt";
    }

    // ? ê³  ë²„íŠ¼ ?™ì‘, ?“ê? ? ê³  ì¹´ìš´??ì¦ê?.
    @RequestMapping("/dec/{c_no}")
    public String dec(@PathVariable int c_no,
                      HttpServletRequest req,
                      HttpSession session){
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // ?¤ë”?ì„œ ?´ì „ ?˜ì´ì§€ë¥??½ëŠ”??

        if (!(m_id == null)) {
            cmtService.cmtdec(c_no);
            return "redirect:" + referer;
        }else {
            // ë¡œê·¸???¼ìœ¼ë¡??´ë™.
            return "redirect:/login/login";
        }
    }
    

    @GetMapping("/cmtlistdec")
    public String cmtlist_dec(HttpSession session, Model model, CmentDto cmentDto){
        String m_id = (String) session.getAttribute("m_id");

        // ?ˆì£¼ë©?th:if ?¬ìš©ë¶ˆê?..
        if(m_id==null){
            m_id = "";
        }
        model.addAttribute("m_id", m_id);
        model.addAttribute("cments", cmtService.cmtlistdec(cmentDto));
        return "cmtbbs/blogcmtdec";
    }

    // ?“ê? ???˜ì´ì§€ ë¶ˆëŸ¬?¤ê¸°
    @GetMapping("/blogcmtform")
    public String cmtform(String c_tbset, String c_tbno, Model model){
        model.addAttribute("c_tbno", c_tbno);
        model.addAttribute("c_tbset", c_tbset);

        return "cmtbbs/blogcmtform";
    }

    // ?“ê? ?°ê¸°
    @RequestMapping("/blogcmtwrite")
    public String cmtwrite(HttpSession session, HttpServletRequest req,
                           @RequestParam(value = "c_content") String c_content, String c_tbno, String c_tbset) {
        String m_id = (String) session.getAttribute("m_id");
        String referer = req.getHeader("Referer"); // ?¤ë”?ì„œ ?´ì „ ?˜ì´ì§€ë¥??½ëŠ”??

        if(c_content == null){
            c_content = "";
        }
        // ë¡œê·¸??ì¡°ê±´ë¬?
        if (!(m_id == null)) {
            if(c_tbno==""){
                //http://localhost:8085/blog/blogread/ ?œê±°?´ë²„ë¦?
                String c_no = referer.substring(36);
                cmtService.cmtinsert(c_no, m_id, c_content);
            }else {
                cmtService.cmtinsert2(c_tbset, m_id, c_content, c_tbno);
            }
            return "redirect:" + referer;
        } else {
            // 2. ë¡œê·¸???¼ìœ¼ë¡??´ë™.
            return "redirect:/login/login";
        }
    }
    
    // ?“ê? ?? œ
    // ì»¤ë??ˆí‹° ê¸€ ?? œ
    @GetMapping("/cmddelete/{c_no}")
    public String cmt_delete(HttpSession session, HttpServletRequest req,
                              Model model, @PathVariable String c_no) {

        // ?¸ì…˜???ˆëŠ” ?„ì´?”ê°’ ì»¤ë??ˆí‹° ê²Œì‹œ???‘ì„±?ì— ?€??
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
