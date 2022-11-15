package com.ucamp.fm.controller;

import com.ucamp.fm.dto.*;
import com.ucamp.fm.service.MemberService;
import com.ucamp.fm.service.PlaceService;
import groovy.util.logging.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    @RequestMapping("/mypage")
    public String mypage (HttpServletRequest request, Model model) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){
            return "redirect:/login/login";
        }else{
            MemberDto member =  memberService.getMember(m_id);
            if (member.getM_level().equals("1")){
                //예약 테이블 생성 후 작업
                List<JoinDto> reser = memberService.getList1(m_id);
                model.addAttribute("list1", reser);
            }else{
                model.addAttribute("list", memberService.getList(m_id));
            }
                model.addAttribute("member",member);

            return "member/mypage";
        }
    }
    //구장 신청 제출
    @RequestMapping("/mypage_request")
    public String mypage_request(Model model, HttpServletRequest request) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            return "member/mypage_request";
        }
    }

    //구장 신청 제출
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
        System.out.println(str);
        System.out.println(placeDto.getI_no());

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
            return "member/mypage_update";
        }
    }

    //구장 신청 제출
    @RequestMapping("/mypage_update.do")
    public String mypage_update_do(@RequestParam("uploadfile") MultipartFile[] uploadfile, HttpServletRequest request, PlaceDto placeDto) throws IOException {
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
    // 구장 삭제
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

    //사진 추가
    @RequestMapping("/addphoto")
    public String addphoto(Model model, HttpServletRequest request) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            model.addAttribute("m_id", m_id);
            return "member/addphoto";
        }
    }
    //사진 추가
    @RequestMapping("/addphoto.do")
    @ResponseBody
    public String addphoto_do(HttpServletRequest request,
                           @RequestParam("m_thum") MultipartFile m_thum) throws IllegalStateException, IOException {
        String m_id = (String) request.getSession().getAttribute("m_id");

        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg/profileImg/";

        // 프로젝트 내 webapp 폴더를 찾아줌, webapp 폴더 없을 경우 appdate안의 톰캣 캐시 임시저장 폴더에 저장시킴.
        // transferTo : 파일 데이터를 지정한 file로 저장
        // getOriginalFilename : 클라이언트의 원본 파일명 반환

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
            return "member/Information_update";
        }
    }


    // 메롱
    @RequestMapping("/Information_update.do")
    public String Information_update_do(Model model, HttpServletRequest request, MemberDto memberDto) {
        String m_id = (String) request.getSession().getAttribute("m_id");


        System.out.println(memberDto);
        memberService.Information_update_do(memberDto);

        return "redirect:/mypage/mypage";
    }

    @RequestMapping("/reservation_list")
    public String reservation_list (HttpServletRequest request, Model model) {
        String m_id = (String) request.getSession().getAttribute("m_id");

                List<JoinDto> reser = memberService.getList2(m_id);

                model.addAttribute("list1", reser);

            return "member/reservation_list";
    }
}

