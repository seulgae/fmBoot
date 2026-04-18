package com.ucamp.fm.blog.controller;

import com.ucamp.fm.blog.dto.BlogDto;
import com.ucamp.fm.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 커뮤니티 게시글의 목록, 상세, 등록, 수정, 삭제를 담당한다.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    // 페이지당 표시할 게시글 수와 페이지 네비게이션 블록 크기.
    private static final int PAGE_SIZE = 12;
    private static final int PAGE_BLOCK = 5;

    /**
     * 게시글 상세 화면을 조회한다.
     */
    @GetMapping("/blogread/{tb_no}")
    public String blog_read(@PathVariable String tb_no, Model model, HttpSession session) {
        String valid = (String) session.getAttribute("m_id");
        if(valid == null){
            valid = "";
        }

        model.addAttribute("m_id", valid);
        model.addAttribute("blog", blogService.blogone(tb_no));
        return "blog/blogread";
    }

    /**
     * 페이지 번호 기반 게시글 목록을 조회한다.
     */
    @RequestMapping("/bloglist")
    public String blog_list(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        String safeKeyword = (keyword == null) ? "" : keyword.trim();
        if (page < 1) {
            page = 1;
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", safeKeyword);

        int totalCount = blogService.countBlogs(map);
        int totalPages = (totalCount == 0) ? 1 : (int) Math.ceil((double) totalCount / PAGE_SIZE);
        if (page > totalPages) {
            page = totalPages;
        }

        map.put("offset", (page - 1) * PAGE_SIZE);
        map.put("pageSize", PAGE_SIZE);

        int startPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
        int endPage = Math.min(startPage + PAGE_BLOCK - 1, totalPages);

        model.addAttribute("blogs", blogService.bloglist(map));
        model.addAttribute("keyword", safeKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("hasPrev", page > 1);
        model.addAttribute("hasNext", page < totalPages);
        return "blog/bloglist";
    }

    /**
     * 페이징 없이 일부 목록 조각만 비동기로 반환한다.
     */
    @GetMapping("/bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto) {
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));
        return "blog/bloglistajax";
    }

    /**
     * 게시글 작성 화면으로 이동한다.
     */
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session, Model model) {
        String tb_id = (String) session.getAttribute("m_id");
        if (tb_id != null) {
            model.addAttribute("tb_id", tb_id);
        } else {
            return "redirect:/login/login";
        }

        return "blog/blogwrite";
    }

    /**
     * 게시글과 썸네일 파일을 함께 저장한다.
     */
    @PostMapping("/blogwriteaction")
    public String blog_write(HttpServletRequest req,
                             @RequestParam("tb_id") String tb_id,
                             @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title") String tb_title,
                             @RequestParam("tb_content") String tb_content) throws IllegalStateException, IOException {

        String path = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";
        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(path + tb_thum.getOriginalFilename()));
        }

        blogService.bloginsert(new BlogDto(tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));
        return "redirect:/blog/bloglist";
    }

    /**
     * 게시글 수정 화면으로 이동한다.
     */
    @GetMapping("/blogmod/{tb_no}")
    public String blog_modForm(HttpSession session, Model model, @PathVariable String tb_no) {
        String tb_id = (String) session.getAttribute("m_id");
        if (tb_id != null) {
            model.addAttribute("tb_id", tb_id);
        } else {
            return "redirect:/login/login";
        }

        model.addAttribute("blog", blogService.blogone(tb_no));
        return "blog/blogmod";
    }

    /**
     * 게시글 수정 내용을 저장한다.
     */
    @PostMapping("/blogmodaction")
    public String blog_mod(HttpServletRequest req,
                           @RequestParam("tb_no") String tb_no,
                           @RequestParam("tb_id") String tb_id,
                           @RequestParam("tb_thum") MultipartFile tb_thum,
                           @RequestParam("tb_title") String tb_title,
                           @RequestParam("tb_content") String tb_content) throws IllegalStateException, IOException {

        String path = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";
        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(path + tb_thum.getOriginalFilename()));
            blogService.blogupdate(new BlogDto(tb_no, tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));
        } else {
            blogService.blogupdate(new BlogDto(tb_no, tb_id, tb_title, tb_content, blogService.getFile(tb_no)));
        }

        return "redirect:/blog/bloglist";
    }

    /**
     * 로그인된 사용자의 게시글을 삭제한다.
     */
    @GetMapping("/blogdelete/{tb_no}")
    public String blog_delete(HttpSession session, Model model, @PathVariable String tb_no) {
        String tb_id = (String) session.getAttribute("m_id");
        if (tb_id != null) {
            model.addAttribute("tb_id", tb_id);
            blogService.blogdelete(tb_no);
            return "redirect:/blog/bloglist";
        } else {
            return "redirect:/login/login";
        }
    }
}
