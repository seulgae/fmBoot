package com.ucamp.fm.controller;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.CmentDto;
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
import java.util.HashMap;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    static int pageNum = 10; // 더보기 페이지 변수
    static int addcount = 0; // 값을 증가시켜 추가 여부를 확인할 조건 값
    static int maincount = 0; // 값을 비교할 저장소.

    static String keywordStack = ""; // 키워드 값을 저장할 공간

    // 커뮤니티 글읽기 페이지
    @GetMapping("/blogread/{tb_no}")
    public String blog_read(@PathVariable String tb_no,
                            Model model, HttpSession session) {

        // 세션 값 넘겨서, 문자열 비교 후 버튼 이벤트를 위한 로직
        // session 값 null 경우 타임리프 에러로 동작하지 않아,
        // 기존에 코드 그대로 세션 아이디 valid에 담아 처리.
        String valid = (String) session.getAttribute("m_id");
        if(valid==null){
            valid = "";
        }
        model.addAttribute("m_id", valid);

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);

        return "blogbbs/blogread";
    }

    // 커뮤니티 글목록 리스트 페이지(더보기 버튼 기능으로 구현)
    @RequestMapping("/bloglist")
    public String blog_list(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "pageAdd", required = false) String pageAdd) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("keyword", keywordStack); // 초기값 대입("")
        map.put("pageNum", pageNum); // 초기값 대입()


        // 만약 pageAdd 가 null 값이면 or 다시 list를 출력한다면.
        // GetMapping 2번 실행되는 이유를 모르겠음..
        // 해당 오류에 대한 알고리즘을 설계하여 더보기 버그 해결.

        if (pageAdd == null || keyword == null) {

            // 추가 기능이 실행되지 않았다면 main 카운트도 증가시키지 않음.
            if (addcount > 0) {
                maincount++;
            }

            // addcount가 증가되지 않았다면.. 문장을 실행하지 않았다면 새로고침 했으니 다시 10개의 페이지로 이동.
            // 새로고침하면 초기값으로 셋팅 될 수 있도록 설계
            if (addcount < maincount) {
                pageNum = 10;
                keywordStack = ""; // 전체 검색
                maincount = 0; // 메인 카운트 초기화
                addcount = 0; // 추가 카운트 초기화
            }
            model.addAttribute("blogs", blogService.bloglist(map));
            // 새로고침하면 다시 초기값으로 셋팅되도록 초기화
            return "blogbbs/bloglist";
        } else {
            // 값이 있다면 페이지값 + 5증가
            pageNum += Integer.valueOf(pageAdd); // 페이지 증가
            keywordStack = keyword; // 검색 값 받아오기.
            addcount += 2;
            return "redirect:/blog/bloglist";
        }
    }

    // 커뮤니티 글목록 리스트 페이지(ajax), 페이징x
    @GetMapping("/bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto) {
        // 10개만 출력, 출력 갯수 바꿀 시 쿼리문 수정 할 것
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));
        return "blogbbs/bloglistajax";
    }

    // 커뮤니티 글쓰기 페이지 이동
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session, Model model) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
        String tb_id = (String) session.getAttribute("m_id");
        // 세션 tb_id가 없다면
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
        } else {
            // 로그인 폼으로 이동.
            return "redirect:/login/login";
        }

        return "blogbbs/blogwrite";
    }

    // 커뮤니티 글쓰기 폼 전송
    @PostMapping("/blogwriteaction")
    public String blog_write(HttpServletRequest req,
                             @RequestParam("tb_id") String tb_id,
                             @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title") String tb_title,
                             @RequestParam("tb_content") String tb_content) throws
            IllegalStateException, IOException {

        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";

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
        String tb_id = (String) session.getAttribute("m_id");
        // 1. 세션 tb_id가 없다면
        if (!(tb_id == null)) {
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

    // 커뮤니티 글 삭제
    @GetMapping("/blogdelete/{tb_no}")
    public String blog_delete(HttpSession session,
                              Model model, @PathVariable String tb_no) {

        // 세션에 있는 아이디값 커뮤니티 게시판 작성자에 저장.
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
