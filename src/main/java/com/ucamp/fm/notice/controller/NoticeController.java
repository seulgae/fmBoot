package com.ucamp.fm.notice.controller;

import com.ucamp.fm.notice.dto.NoticeDto;
import com.ucamp.fm.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;//리스?�로 ?�동, 목록 출력

    @GetMapping("/noticehome")
    public String noticeList(Model model, HttpSession session) {
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id",m_id);
        model.addAttribute("notices", noticeService.noticelist());
        return "notice/noticehome";
    }


    @GetMapping("/noticeread/{n_no}")
    public String noticeRead(Model model, @PathVariable("n_no") int n_no, HttpSession session) {
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id",m_id);
        model.addAttribute("notices", noticeService.noticeselect(n_no));
        noticeService.countup(n_no);
        return "notice/noticeread";
    }

    @RequestMapping("/noticedelete/{n_no}")
    public String noticeDelete(@PathVariable("n_no") int n_no) {
        noticeService.noticedelete(n_no);
        return "redirect:/notice/noticehome";
    }
    // 글?�기 ??
    @GetMapping("/noticewrite")
    public String noticeWrite(HttpSession session) {
        String n_id = (String) session.getAttribute("m_id");
        if(n_id == null){
            return "redirect:/login/login";
        }
        return "notice/noticewrite";
    }
    // 글?�기 ?�력
    @PostMapping("/noticeinsert")
    public String noticeInsert(@RequestParam("n_title") String n_title, HttpSession session,
                               @RequestParam("n_content") String n_content) {
        String n_id = (String) session.getAttribute("m_id");
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("n_id", n_id);
        map.put("n_title", n_title);
        map.put("n_content", n_content);

        noticeService.noticeinsert(map);
        return "redirect:/notice/noticehome";
    }
    
    // 글?�정 ??
    @GetMapping("/noticemod/{n_no}")
    public String noticemod(@PathVariable("n_no") int n_no,
                            HttpSession session, Model model) {
        String n_id = (String) session.getAttribute("m_id");
        if(n_id == null){
            return "redirect:/login/login";
        }

        model.addAttribute("notice",noticeService.noticeselect(n_no));

        return "notice/noticemod";
    }

    @PostMapping("/noticemodac")
    public String noticemod_ac(HttpSession session,
                               @RequestParam("n_no") int n_no,
                               @RequestParam("n_title") String n_title,
                               @RequestParam("n_content") String n_content) {
        String n_id = (String) session.getAttribute("m_id");
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("n_no", n_no);
        map.put("n_id", n_id);
        map.put("n_title", n_title);
        map.put("n_content", n_content);

        if(n_id == null){
            return "redirect:/login/login";
        }

        noticeService.noticeupdate(map);
        return "redirect:/notice/noticehome";
    }
}

