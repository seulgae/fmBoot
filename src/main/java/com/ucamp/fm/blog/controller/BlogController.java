package com.ucamp.fm.blog.controller;

import com.ucamp.fm.blog.dto.BlogDto;
import com.ucamp.fm.cmt.dto.CmentDto;
import com.ucamp.fm.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    static int pageNum = 10; // ?�보�??�이지 변??
    static int addcount = 0; // 값을 증�??�켜 추�? ?��?�??�인??조건 �?
    static int maincount = 0; // 값을 비교???�?�소.

    static String keywordStack = ""; // ?�워??값을 ?�?�할 공간

    // 커�??�티 글?�기 ?�이지
    @GetMapping("/blogread/{tb_no}")
    public String blog_read(@PathVariable String tb_no,
                            Model model, HttpSession session) {

        // ?�션 �??�겨?? 문자??비교 ??버튼 ?�벤?��? ?�한 로직
        // session �?null 경우 ?�?�리???�러�??�작?��? ?�아,
        // 기존??코드 그�?�??�션 ?�이??valid???�아 처리.
        String valid = (String) session.getAttribute("m_id");
        if(valid==null){
            valid = "";
        }
        model.addAttribute("m_id", valid);

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);

        return "blog/blogread";
    }

    // 커�??�티 글목록 리스???�이지(?�보�?버튼 기능?�로 구현)
    @RequestMapping("/bloglist")
    public String blog_list(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "pageAdd", required = false) String pageAdd) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("keyword", keywordStack); // 초기�??�??"")
        map.put("pageNum", pageNum); // 초기�??�??)


        // 만약 pageAdd 가 null 값이�?or ?�시 list�?출력?�다�?
        // GetMapping 2�??�행?�는 ?�유�?모르겠음..
        // ?�당 ?�류???�???�고리즘???�계?�여 ?�보�?버그 ?�결.

        if (pageAdd == null || keyword == null) {

            // 추�? 기능???�행?��? ?�았?�면 main 카운?�도 증�??�키지 ?�음.
            if (addcount > 0) {
                maincount++;
            }

            // addcount가 증�??��? ?�았?�면.. 문장???�행?��? ?�았?�면 ?�로고침 ?�으???�시 10개의 ?�이지�??�동.
            // ?�로고침?�면 초기값으�??�팅 ?????�도�??�계
            if (addcount < maincount) {
                pageNum = 10;
                keywordStack = ""; // ?�체 검??
                maincount = 0; // 메인 카운??초기??
                addcount = 0; // 추�? 카운??초기??
            }
            model.addAttribute("blogs", blogService.bloglist(map));
            // ?�로고침?�면 ?�시 초기값으�??�팅?�도�?초기??
            return "blog/bloglist";
        } else {
            // 값이 ?�다�??�이지�?+ 5증�?
            pageNum += Integer.valueOf(pageAdd); // ?�이지 증�?
            keywordStack = keyword; // 검??�?받아?�기.
            addcount += 2;
            return "redirect:/blog/bloglist";
        }
    }

    // 커�??�티 글목록 리스???�이지(ajax), ?�이징x
    @GetMapping("/bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto) {
        // 10개만 출력, 출력 �?�� 바�? ??쿼리�??�정 ??�?
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));
        return "blog/bloglistajax";
    }

    // 커�??�티 글?�기 ?�이지 ?�동
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session, Model model) {

        // ?�션???�는 ?�이?�값 커�??�티 게시???�성?�에 ?�??
        String tb_id = (String) session.getAttribute("m_id");
        // ?�션 tb_id가 ?�다�?
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
        } else {
            // 로그???�으�??�동.
            return "redirect:/login/login";
        }

        return "blog/blogwrite";
    }

    // 커�??�티 글?�기 ???�송
    @PostMapping("/blogwriteaction")
    public String blog_write(HttpServletRequest req,
                             @RequestParam("tb_id") String tb_id,
                             @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title") String tb_title,
                             @RequestParam("tb_content") String tb_content) throws
            IllegalStateException, IOException {

        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";

        // ?�로?�트 ??webapp ?�더�?찾아�? webapp ?�더 ?�을 경우 appdate?�의 ?�캣 캐시 ?�시?�???�더???�?�시??
        // transferTo : ?�일 ?�이?��? 지?�한 file�??�??
        // getOriginalFilename : ?�라?�언?�의 ?�본 ?�일�?반환

        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(PATH + tb_thum.getOriginalFilename()));
        }

        blogService.bloginsert(new BlogDto(tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));

        return "redirect:/blog/bloglist";
    }

    // 커�??�티 글 ?�기


    // 커�??�티 글 ?�정 ?�이지 ?�동
    @GetMapping("/blogmod/{tb_no}")
    public String blog_modForm(HttpSession session,
                               Model model,
                               @PathVariable String tb_no) {

        // ?�션???�는 ?�이?�값 커�??�티 게시???�성?�에 ?�??
        String tb_id = (String) session.getAttribute("m_id");
        // 1. ?�션 tb_id가 ?�다�?
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
        } else {
            // 2. 로그???�으�??�동.
            return "redirect:/login/login";
        }

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);


        return "blog/blogmod";
    }

    // 커�??�티 글 ?�정 ?�전??
    @PostMapping("/blogmodaction")
    public String blog_mod(HttpServletRequest req,
                           @RequestParam("tb_no") String tb_no,
                           @RequestParam("tb_id") String tb_id,
                           @RequestParam("tb_thum") MultipartFile tb_thum,
                           @RequestParam("tb_title") String tb_title,
                           @RequestParam("tb_content") String tb_content) throws
            IllegalStateException, IOException {

        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";

        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(PATH + tb_thum.getOriginalFilename()));
            blogService.blogupdate(new BlogDto(tb_no, tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));
        }else{
            blogService.blogupdate(new BlogDto(tb_no, tb_id, tb_title, tb_content, blogService.getFile(tb_no)));
        }


        return "redirect:/blog/bloglist";
    }

    // 커�??�티 글 ??��
    @GetMapping("/blogdelete/{tb_no}")
    public String blog_delete(HttpSession session,
                              Model model, @PathVariable String tb_no) {

        // ?�션???�는 ?�이?�값 커�??�티 게시???�성?�에 ?�??
        String tb_id = (String) session.getAttribute("m_id");
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
            blogService.blogdelete(tb_no);
            return "redirect:/blog/bloglist";
        } else {
            return "redirect:/login/login";
        }
    }
}
