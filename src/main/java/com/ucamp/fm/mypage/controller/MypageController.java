package com.ucamp.fm.mypage.controller;

import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.login.service.MemberService;
import com.ucamp.fm.mypage.dto.ImageDto;
import com.ucamp.fm.mypage.dto.JoinDto;
import com.ucamp.fm.mypage.dto.PlaceDto;
import com.ucamp.fm.mypage.service.PlaceService;
import groovy.util.logging.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    MemberService memberService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/mypage")
    public String mypage (HttpServletRequest request, Model model) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){
            return "redirect:/login/login";
        }else{
            MemberDto member =  memberService.getMember(m_id);
            if (member.getM_level().equals("1")){
                //?�약 ?�이�??�성 ???�업
                List<JoinDto> reser = memberService.getList1(m_id);
                model.addAttribute("list1", reser);
            }else{
                model.addAttribute("list", memberService.getList(m_id));
            }
                model.addAttribute("member",member);

            return "mypage/mypage";
        }
    }
    //구장 ?�청 ?�출
    @RequestMapping("/mypage_request")
    public String mypage_request(Model model, HttpServletRequest request) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            return "mypage/mypage_request";
        }
    }

    //구장 ?�청 ?�출
    @RequestMapping("/mypage_request.do")
    public String mypage_request_do(@RequestParam("uploadfile") MultipartFile[] uploadfile,
                                    HttpServletRequest request, PlaceDto placeDto) throws IllegalStateException, IOException {
        String m_id = (String) request.getSession().getAttribute("m_id");
        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg/place/";

        String str = "";
        for(MultipartFile file : uploadfile){
            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

            String oriName = formatedNow + "-" + file.getOriginalFilename();
            if (!file.getOriginalFilename().isEmpty()) {
                file.transferTo(new File(PATH + oriName));
            }
            placeService.insertImage(new ImageDto("place","place",oriName,String.valueOf(file.getSize())));
            String seq = placeService.getSeq();
            str += seq + " ";
        }
        if(placeDto.getP_op1() == null){
            placeDto.setP_op1("0");
        }
        if(placeDto.getP_op2() == null){
            placeDto.setP_op2("0");
        }
        if(placeDto.getP_op3() == null){
            placeDto.setP_op3("0");
        }
        if(placeDto.getP_op4() == null){
            placeDto.setP_op4("0");
        }
        if(placeDto.getP_op5() == null){
            placeDto.setP_op5("0");
        }
        if(placeDto.getP_op6() == null){
            placeDto.setP_op6("0");
        }

        placeDto.setI_no(str);

        memberService.mypage_request(placeDto);

        return "redirect:/mypage/mypage";
    }

    @RequestMapping("/mypage_update/{p_no}")
    public String mypage_update(Model model, HttpServletRequest request,@PathVariable String p_no) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){
            return "redirect:/login/login";
        }else{
            PlaceDto pDto = memberService.getDto(p_no);
            ArrayList<String> images = new ArrayList<>();
            String[] imgArr = pDto.getI_no().split(" ");
            for(String p : imgArr){
                String fName = placeService.getFname(p);
                images.add("../../uploadImg/place/"+fName);
            }
            model.addAttribute("images",images);
            model.addAttribute("dto",pDto);
            model.addAttribute("key",'1');
            return "mypage/mypage_update";
        }
    }

    //구장 ?�청 ?�출
    @RequestMapping("/mypage_update.do")
    public String mypage_update_do(@RequestParam("uploadfile") MultipartFile[] uploadfile, HttpServletRequest request, PlaceDto placeDto) throws IOException {
        String m_id = (String) request.getSession().getAttribute("m_id");

        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg/place/";

        String str = "";
        for(MultipartFile file : uploadfile){
            if(file.isEmpty()){
                break;
            }
            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

            String oriName = formatedNow + "-" + file.getOriginalFilename();
            if (!file.getOriginalFilename().isEmpty()) {
                file.transferTo(new File(PATH + oriName));
            }
            placeService.insertImage(new ImageDto("place","place",oriName,String.valueOf(file.getSize())));
            String seq = placeService.getSeq();
            str += seq + " ";
        }
        if(str==""){
            str = placeService.getI_no(placeDto.getP_no());
        }
        placeDto.setI_no(str);
        if(placeDto.getP_op1() == null){
            placeDto.setP_op1("0");
        }
        if(placeDto.getP_op2() == null){
            placeDto.setP_op2("0");
        }
        if(placeDto.getP_op3() == null){
            placeDto.setP_op3("0");
        }
        if(placeDto.getP_op4() == null){
            placeDto.setP_op4("0");
        }
        if(placeDto.getP_op5() == null){
            placeDto.setP_op5("0");
        }
        if(placeDto.getP_op6() == null){
            placeDto.setP_op6("0");
        }

        memberService.mypage_update_do(placeDto);

        return "redirect:/mypage/mypage";
    }
    // 구장 ??��
    @RequestMapping("/place_delete/{p_no}")
    public String place_delete(HttpServletRequest request, @PathVariable String p_no) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            memberService.place_delete(p_no);
            return "redirect:/mypage/mypage";
        }
    }

    //?�진 추�?
    @RequestMapping("/addphoto")
    public String addphoto(Model model, HttpServletRequest request) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            model.addAttribute("m_id", m_id);
            return "mypage/addphoto";
        }
    }

    //?�진 추�?
    @RequestMapping("/addphoto.do")
    @ResponseBody
    public String addphoto_do(HttpServletRequest request,
                           @RequestParam("m_thum") MultipartFile m_thum) throws IllegalStateException, IOException {
        String m_id = (String) request.getSession().getAttribute("m_id");

        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg/profileImg/";

        // ?�로?�트 ??webapp ?�더�?찾아�? webapp ?�더 ?�을 경우 appdate?�의 ?�캣 캐시 ?�시?�???�더???�?�시??
        // transferTo : ?�일 ?�이?��? 지?�한 file�??�??
        // getOriginalFilename : ?�라?�언?�의 ?�본 ?�일�?반환

        if (!m_thum.getOriginalFilename().isEmpty()) {
            m_thum.transferTo(new File(PATH + m_thum.getOriginalFilename()));
        }

        memberService.addPhoto(new MemberDto(m_id,m_thum.getOriginalFilename()));

        return "<script>window.opener.location.reload(); window.close();</script>";

    }

    @RequestMapping("Information_update/{m_id}")
    public String Information_update(Model model, HttpServletRequest request,@PathVariable String m_id) {
        m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            model.addAttribute("dto",memberService.Information_update(m_id));
            return "mypage/Information_update";
        }
    }

    @RequestMapping("/Information_update.do")
    public String Information_update_do(HttpServletRequest request, MemberDto memberDto) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));

        memberService.Information_update_do(memberDto);

        return "redirect:/mypage/mypage";
    }

    @RequestMapping("/reservation_list")
    public String reservation_list (HttpServletRequest request, Model model) {
        String m_id = (String) request.getSession().getAttribute("m_id");

                List<JoinDto> reser = memberService.getList2(m_id);

                model.addAttribute("list1", reser);

            return "mypage/reservation_list";
    }
}

