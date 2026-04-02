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

    static int pageNum = 10; // ?”ë³´ê¸??˜ì´ì§€ ë³€??
    static int addcount = 0; // ê°’ì„ ì¦ê??œì¼œ ì¶”ê? ?¬ë?ë¥??•ì¸??ì¡°ê±´ ê°?
    static int maincount = 0; // ê°’ì„ ë¹„êµ???€?¥ì†Œ.

    static String keywordStack = ""; // ?¤ì›Œ??ê°’ì„ ?€?¥í•  ê³µê°„

    // ì»¤ë??ˆí‹° ê¸€?½ê¸° ?˜ì´ì§€
    @GetMapping("/blogread/{tb_no}")
    public String blog_read(@PathVariable String tb_no,
                            Model model, HttpSession session) {

        // ?¸ì…˜ ê°??˜ê²¨?? ë¬¸ì??ë¹„êµ ??ë²„íŠ¼ ?´ë²¤?¸ë? ?„í•œ ë¡œì§
        // session ê°?null ê²½ìš° ?€?„ë¦¬???ëŸ¬ë¡??™ì‘?˜ì? ?Šì•„,
        // ê¸°ì¡´??ì½”ë“œ ê·¸ë?ë¡??¸ì…˜ ?„ì´??valid???´ì•„ ì²˜ë¦¬.
        String valid = (String) session.getAttribute("m_id");
        if(valid==null){
            valid = "";
        }
        model.addAttribute("m_id", valid);

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);

        return "blogbbs/blogread";
    }

    // ì»¤ë??ˆí‹° ê¸€ëª©ë¡ ë¦¬ìŠ¤???˜ì´ì§€(?”ë³´ê¸?ë²„íŠ¼ ê¸°ëŠ¥?¼ë¡œ êµ¬í˜„)
    @RequestMapping("/bloglist")
    public String blog_list(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "pageAdd", required = false) String pageAdd) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("keyword", keywordStack); // ì´ˆê¸°ê°??€??"")
        map.put("pageNum", pageNum); // ì´ˆê¸°ê°??€??)


        // ë§Œì•½ pageAdd ê°€ null ê°’ì´ë©?or ?¤ì‹œ listë¥?ì¶œë ¥?œë‹¤ë©?
        // GetMapping 2ë²??¤í–‰?˜ëŠ” ?´ìœ ë¥?ëª¨ë¥´ê² ìŒ..
        // ?´ë‹¹ ?¤ë¥˜???€???Œê³ ë¦¬ì¦˜???¤ê³„?˜ì—¬ ?”ë³´ê¸?ë²„ê·¸ ?´ê²°.

        if (pageAdd == null || keyword == null) {

            // ì¶”ê? ê¸°ëŠ¥???¤í–‰?˜ì? ?Šì•˜?¤ë©´ main ì¹´ìš´?¸ë„ ì¦ê??œí‚¤ì§€ ?ŠìŒ.
            if (addcount > 0) {
                maincount++;
            }

            // addcountê°€ ì¦ê??˜ì? ?Šì•˜?¤ë©´.. ë¬¸ì¥???¤í–‰?˜ì? ?Šì•˜?¤ë©´ ?ˆë¡œê³ ì¹¨ ?ˆìœ¼???¤ì‹œ 10ê°œì˜ ?˜ì´ì§€ë¡??´ë™.
            // ?ˆë¡œê³ ì¹¨?˜ë©´ ì´ˆê¸°ê°’ìœ¼ë¡??‹íŒ… ?????ˆë„ë¡??¤ê³„
            if (addcount < maincount) {
                pageNum = 10;
                keywordStack = ""; // ?„ì²´ ê²€??
                maincount = 0; // ë©”ì¸ ì¹´ìš´??ì´ˆê¸°??
                addcount = 0; // ì¶”ê? ì¹´ìš´??ì´ˆê¸°??
            }
            model.addAttribute("blogs", blogService.bloglist(map));
            // ?ˆë¡œê³ ì¹¨?˜ë©´ ?¤ì‹œ ì´ˆê¸°ê°’ìœ¼ë¡??‹íŒ…?˜ë„ë¡?ì´ˆê¸°??
            return "blogbbs/bloglist";
        } else {
            // ê°’ì´ ?ˆë‹¤ë©??˜ì´ì§€ê°?+ 5ì¦ê?
            pageNum += Integer.valueOf(pageAdd); // ?˜ì´ì§€ ì¦ê?
            keywordStack = keyword; // ê²€??ê°?ë°›ì•„?¤ê¸°.
            addcount += 2;
            return "redirect:/blog/bloglist";
        }
    }

    // ì»¤ë??ˆí‹° ê¸€ëª©ë¡ ë¦¬ìŠ¤???˜ì´ì§€(ajax), ?˜ì´ì§•x
    @GetMapping("/bloglistajax")
    public String blog_list_ajax(Model model, BlogDto blogDto) {
        // 10ê°œë§Œ ì¶œë ¥, ì¶œë ¥ ê°?ˆ˜ ë°”ê? ??ì¿¼ë¦¬ë¬??˜ì • ??ê²?
        model.addAttribute("blogs", blogService.bloglistajax(blogDto));
        return "blogbbs/bloglistajax";
    }

    // ì»¤ë??ˆí‹° ê¸€?°ê¸° ?˜ì´ì§€ ?´ë™
    @GetMapping("/blogwrite")
    public String blog_writeForm(HttpSession session, Model model) {

        // ?¸ì…˜???ˆëŠ” ?„ì´?”ê°’ ì»¤ë??ˆí‹° ê²Œì‹œ???‘ì„±?ì— ?€??
        String tb_id = (String) session.getAttribute("m_id");
        // ?¸ì…˜ tb_idê°€ ?†ë‹¤ë©?
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
        } else {
            // ë¡œê·¸???¼ìœ¼ë¡??´ë™.
            return "redirect:/login/login";
        }

        return "blogbbs/blogwrite";
    }

    // ì»¤ë??ˆí‹° ê¸€?°ê¸° ???„ì†¡
    @PostMapping("/blogwriteaction")
    public String blog_write(HttpServletRequest req,
                             @RequestParam("tb_id") String tb_id,
                             @RequestParam("tb_thum") MultipartFile tb_thum,
                             @RequestParam("tb_title") String tb_title,
                             @RequestParam("tb_content") String tb_content) throws
            IllegalStateException, IOException {

        String PATH = req.getSession().getServletContext().getRealPath("/") + "uploadImg/blog/";

        // ?„ë¡œ?íŠ¸ ??webapp ?´ë”ë¥?ì°¾ì•„ì¤? webapp ?´ë” ?†ì„ ê²½ìš° appdate?ˆì˜ ?°ìº£ ìºì‹œ ?„ì‹œ?€???´ë”???€?¥ì‹œ??
        // transferTo : ?Œì¼ ?°ì´?°ë? ì§€?•í•œ fileë¡??€??
        // getOriginalFilename : ?´ë¼?´ì–¸?¸ì˜ ?ë³¸ ?Œì¼ëª?ë°˜í™˜

        if (!tb_thum.getOriginalFilename().isEmpty()) {
            tb_thum.transferTo(new File(PATH + tb_thum.getOriginalFilename()));
        }

        blogService.bloginsert(new BlogDto(tb_id, tb_title, tb_content, tb_thum.getOriginalFilename()));

        return "redirect:/blog/bloglist";
    }

    // ì»¤ë??ˆí‹° ê¸€ ?½ê¸°


    // ì»¤ë??ˆí‹° ê¸€ ?˜ì • ?˜ì´ì§€ ?´ë™
    @GetMapping("/blogmod/{tb_no}")
    public String blog_modForm(HttpSession session,
                               Model model,
                               @PathVariable String tb_no) {

        // ?¸ì…˜???ˆëŠ” ?„ì´?”ê°’ ì»¤ë??ˆí‹° ê²Œì‹œ???‘ì„±?ì— ?€??
        String tb_id = (String) session.getAttribute("m_id");
        // 1. ?¸ì…˜ tb_idê°€ ?†ë‹¤ë©?
        if (!(tb_id == null)) {
            model.addAttribute("tb_id", tb_id);
        } else {
            // 2. ë¡œê·¸???¼ìœ¼ë¡??´ë™.
            return "redirect:/login/login";
        }

        BlogDto blogDto = blogService.blogone(tb_no);
        model.addAttribute("blog", blogDto);


        return "blogbbs/blogmod";
    }

    // ì»¤ë??ˆí‹° ê¸€ ?˜ì • ?¼ì „??
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

    // ì»¤ë??ˆí‹° ê¸€ ?? œ
    @GetMapping("/blogdelete/{tb_no}")
    public String blog_delete(HttpSession session,
                              Model model, @PathVariable String tb_no) {

        // ?¸ì…˜???ˆëŠ” ?„ì´?”ê°’ ì»¤ë??ˆí‹° ê²Œì‹œ???‘ì„±?ì— ?€??
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
