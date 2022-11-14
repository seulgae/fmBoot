package com.ucamp.fm.controller;

import com.ucamp.fm.dto.*;
import com.ucamp.fm.service.MemberService;
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
import java.util.List;


@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    MemberService memberService;

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
                model.addAttribute("list", memberService.getList());
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
    public String mypage_request_do(Model model, HttpServletRequest request, PlaceDto placeDto) {
        String m_id = (String) request.getSession().getAttribute("m_id");


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

        memberService.mypage_request(placeDto);

        return "redirect:/mypage/mypage";
    }

    @RequestMapping("/mypage_update/{p_no}")
    public String mypage_update(Model model, HttpServletRequest request,@PathVariable String p_no) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if ( m_id == null){

            return "redirect:/login/login";

        }else{
            model.addAttribute("dto",memberService.getDto(p_no));
            model.addAttribute("key",'1');
            return "member/mypage_update";
        }
    }

    //구장 신청 제출
    @RequestMapping("/mypage_update.do")
    public String mypage_update_do(Model model, HttpServletRequest request, PlaceDto placeDto) {
        String m_id = (String) request.getSession().getAttribute("m_id");


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

        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg\\profileImg\\";

        System.out.println(m_thum);
        // 프로젝트 내 webapp 폴더를 찾아줌, webapp 폴더 없을 경우 appdate안의 톰캣 캐시 임시저장 폴더에 저장시킴.
        // transferTo : 파일 데이터를 지정한 file로 저장
        // getOriginalFilename : 클라이언트의 원본 파일명 반환

        if (!m_thum.getOriginalFilename().isEmpty()) {
            m_thum.transferTo(new File(PATH + m_thum.getOriginalFilename()));
        }

        memberService.addPhoto(new MemberDto(m_id,m_thum.getOriginalFilename()));

        return "<script>window.opener.location.reload(); window.close();</script>";

    }
}

