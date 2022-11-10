package com.ucamp.fm.controller;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.service.BlogService;
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


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    // 커뮤니티 글목록 리스트 페이지(ajax)
    @GetMapping("bloglist")
    public String blog_list(Model model, BlogDto blogDto){

        model.addAttribute("blogs", blogService.bloglist(blogDto));

        return "blogbbs/bloglist";
    }


    // 커뮤니티 글목록 리스트 페이지(ajax)
    @GetMapping("bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto){

        // 10개만 출력, 출력 갯수 바꿀 시 쿼리문 수정 할 것
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));

        return "blogbbs/bloglistajax";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){

        return "<img src='/webapp/uploadImg/blog/blogwp3996081-10801920-wallpapers.jpg'>";
    }

    // 커뮤니티 글쓰기 페이지 이동
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session,Model model) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String)session.getAttribute("m_id");
        // 1. 세션 tb_id가 없다면
        if(!(tb_id == null)){
            model.addAttribute("tb_id", tb_id);
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }

        return "blogbbs/blogwrite";
    }

    // 커뮤니티 글쓰기 폼 전송
    @PostMapping("/blogwriteaction")
    public String blog_write(HttpServletRequest req, RedirectAttributes rattr,
                             @RequestParam("tb_id") String tb_id,
                             @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title")String tb_title,
                             @RequestParam("tb_content")String tb_content,
                             Model model) throws
                             IllegalStateException, IOException {

        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg\\blog\\";

        System.out.println(PATH);
        // 프로젝트 내 webapp 폴더를 찾아줌, webapp 폴더 없을 경우 appdate안의 톰캣 캐시 임시저장 폴더에 저장시킴.
        // transferTo : 파일 데이터를 지정한 file로 저장
        // getOriginalFilename : 클라이언트의 원본 파일명 반환

        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(PATH + tb_thum.getOriginalFilename()));
        }

        blogService.bloginsert(new BlogDto(tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));

        return "redirect:/";
    }

    // 커뮤니티 글 읽기


    // 커뮤니티 글 수정 페이지 이동
    @GetMapping("/blogmod")
    public String blog_modForm(HttpSession session,
                               Model model, BlogDto blogDto) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String)session.getAttribute("m_id");
        // 1. 세션 tb_id가 없다면
        if(!(tb_id == null)){
            model.addAttribute("tb_id", tb_id);
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }

        return "blogbbs/blogmod";
    }

    // 커뮤니티 글 수정 폼전송
    @PostMapping("/blogmodaction")
    public String blog_mod(){
        return "blogbbs/blogmod";
    }

    // 커뮤니티 글 삭제
}
