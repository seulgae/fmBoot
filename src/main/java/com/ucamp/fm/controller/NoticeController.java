package com.ucamp.fm.controller;

import com.ucamp.fm.dto.NoticeDto;
import com.ucamp.fm.service.NoticeService;
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
    NoticeService noticeService;//리스트로 이동, 목록 출력

    @GetMapping("/noticehome")
    public String noticeList(Model model, HttpSession session) {
//        if(m_id == null){
//            m_id =
//        }
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id",m_id);
        model.addAttribute("notices", noticeService.noticelist());
        return "noticebbs/noticehome";
    }


    @GetMapping("/noticeread/{n_no}")
    public String noticeRead(Model model, @PathVariable("n_no") int n_no) {
        model.addAttribute("notices", noticeService.noticeselect(n_no));
        noticeService.countup(n_no);
        return "noticebbs/noticeread";
    }

    @RequestMapping("/noticedelete/{n_no}")
    public String noticeDelete(@PathVariable("n_no") int n_no) {
        noticeService.noticedelete(n_no);
        return "redirect:/notice/noticehome";
    }
    // 글쓰기 폼
    @GetMapping("/noticewrite")
    public String noticeWrite(HttpSession session) {
        String n_id = (String) session.getAttribute("m_id");
        if(n_id == null){
            return "redirect:/login/login";
        }
        return "noticebbs/noticewrite";
    }
    // 글쓰기 입력
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
    
    // 글수정 폼
    @GetMapping("/noticemod/{n_no}")
    public String noticemod(@PathVariable("n_no") int n_no,
                            HttpSession session, Model model) {
        String n_id = (String) session.getAttribute("m_id");
        if(n_id == null){
            return "redirect:/login/login";
        }
        model.addAttribute("notice",noticeService.noticeselect(n_no));

        return "noticebbs/noticemod";
    }

    @PostMapping("/noticemodac")
    public String noticemod_ac(HttpSession session,
                               @RequestParam("n_title") String n_title,
                               @RequestParam("n_content") String n_content) {
        String n_id = (String) session.getAttribute("m_id");
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("n_id", n_id);
        map.put("n_title", n_title);
        map.put("n_content", n_content);

        if(n_id == null){
            return "redirect:/login/login";
        }

        noticeService.noticeinsert(map);
        return "redirect:/notice/noticehome";
    }
}

