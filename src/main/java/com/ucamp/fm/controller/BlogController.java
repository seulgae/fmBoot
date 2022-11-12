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

    static int blogcnt = 10;
    
    // 커뮤니티 글읽기 페이지
    @GetMapping("/blogread/{tb_no}")
    public String blog_read(@PathVariable String tb_no,
                            Model model, HttpSession session){

        // 세션 값 넘겨서, 문자열 비교 후 버튼 이벤트를 위한 로직
        // session 값 null 경우 타임리프 에러로 동작하지 않아,
        // 기존에 코드 그대로 세션 아이디 valid에 담아 처리.
        String valid = (String)session.getAttribute("m_id");
        model.addAttribute("m_id", valid);

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);

        return "blogbbs/blogread";
    }

    // 커뮤니티 글목록 리스트 페이지(더보기 버튼 기능으로 구현)
    @GetMapping("/bloglist")
    public String blog_list(Model model, BlogDto blogDto){
        
        model.addAttribute("blogs", blogService.bloglist());
        return "blogbbs/bloglist";
    }

    // 커뮤니티 글목록 리스트 페이지(ajax), 페이징x
    @GetMapping("/bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto){

        // 10개만 출력, 출력 갯수 바꿀 시 쿼리문 수정 할 것
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));

        return "blogbbs/bloglistajax";
    }

    // 커뮤니티 글쓰기 페이지 이동
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session,Model model) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String)session.getAttribute("m_id");
        // 세션 tb_id가 없다면
        if(!(tb_id == null)){
            model.addAttribute("tb_id", tb_id);
        } else {
            // 로그인 폼으로 이동.
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

        return "redirect:/blog/bloglist";
    }

    // 커뮤니티 글 읽기


    // 커뮤니티 글 수정 페이지 이동
    @GetMapping("/blogmod/{tb_no}")
    public String blog_modForm(HttpSession session,
                               Model model,
                               @PathVariable String tb_no) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String)session.getAttribute("m_id");
        // 1. 세션 tb_id가 없다면
        if(!(tb_id == null)){
            model.addAttribute("tb_id", tb_id);
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);


        return "blogbbs/blogmod";
    }

    // 커뮤니티 글 수정 폼전송
    @PostMapping("/blogmodaction")
    public String blog_mod(HttpServletRequest req, RedirectAttributes rattr,
                           @RequestParam("tb_id") String tb_id,
                           @RequestParam("tb_thum") MultipartFile tb_thum,
                           @RequestParam("tb_title")String tb_title,
                           @RequestParam("tb_content")String tb_content,
                           Model model)throws
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

        return "redirect:/blog/bloglist";
    }

    // 커뮤니티 글 삭제
    @GetMapping("/blogdelete/{tb_no}")
    public String blog_delete(HttpSession session,
                              Model model, @PathVariable String tb_no){

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String)session.getAttribute("m_id");
        // 1. 세션 tb_id가 없다면
        if(!(tb_id == null)){
            model.addAttribute("tb_id", tb_id);
            blogService.blogdelete(tb_no);
            return "redirect:/blog/bloglist";
        } else {
            // 2. 로그인 폼으로 이동.
            return "redirect:/login/login";
        }
    }

    // 커뮤니티 댓글 게시판
    @GetMapping("/blogcomment")
    public String bolg_comment(){

        return "/blogbbs/blogcomment";
    }
}
