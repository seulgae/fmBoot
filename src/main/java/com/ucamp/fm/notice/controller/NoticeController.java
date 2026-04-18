package com.ucamp.fm.notice.controller;

import com.ucamp.fm.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 공지사항 목록, 상세, 등록, 수정, 삭제를 담당하는 컨트롤러.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    /**
     * 공지사항 목록을 조회한다.
     */
    @GetMapping("/noticehome")
    public String noticeList(Model model, HttpSession session) {
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id",m_id);
        model.addAttribute("notices", noticeService.noticelist());
        return "notice/noticehome";
    }

    /**
     * 공지사항 상세를 조회하고 조회수를 증가시킨다.
     */
    @GetMapping("/noticeread/{n_no}")
    public String noticeRead(Model model, @PathVariable("n_no") int n_no, HttpSession session) {
        String m_id = (String) session.getAttribute("m_id");
        model.addAttribute("m_id",m_id);
        model.addAttribute("notices", noticeService.noticeselect(n_no));
        noticeService.countup(n_no);
        return "notice/noticeread";
    }

    /**
     * 공지사항을 삭제한다.
     */
    @RequestMapping("/noticedelete/{n_no}")
    public String noticeDelete(@PathVariable("n_no") int n_no) {
        noticeService.noticedelete(n_no);
        return "redirect:/notice/noticehome";
    }

    /**
     * 공지사항 작성 화면으로 이동한다.
     */
    @GetMapping("/noticewrite")
    public String noticeWrite(HttpSession session) {
        String n_id = (String) session.getAttribute("m_id");
        if(n_id == null){
            return "redirect:/login/login";
        }
        return "notice/noticewrite";
    }

    /**
     * 공지사항을 등록한다.
     */
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

    /**
     * 공지사항 수정 화면으로 이동한다.
     */
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

    /**
     * 공지사항 수정 내용을 저장한다.
     */
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
