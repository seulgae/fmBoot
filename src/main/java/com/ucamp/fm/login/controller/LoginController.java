package com.ucamp.fm.login.controller;

import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 회원가입, 로그인, 아이디/비밀번호 찾기 흐름을 처리하는 컨트롤러.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 루트 홈 화면으로 이동한다.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * 로그인 화면으로 이동한다.
     */
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    /**
     * 회원가입 화면으로 이동한다.
     */
    @RequestMapping("/join")
    public String join () {
        return "login/join";
    }

    /**
     * 아이디 중복 여부를 비동기로 확인한다.
     */
    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck (String m_id){
        return memberService.idCheck(m_id);
    }

    /**
     * 회원가입 데이터를 저장한다.
     */
    @RequestMapping("/joinInsert")
    public String joinInsert (MemberDto member){
        member.setM_pw(passwordEncoder.encode(member.getM_pw()));
        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 비밀번호 일치 여부만 간단히 검증한다.
     */
    @RequestMapping("/loginCheck")
    @ResponseBody
    public String loginCheck (String m_id, String m_pw, Model model, HttpServletRequest request){
        if(!passwordEncoder.matches(m_pw,memberService.getPw(m_id))){
            return "0";
        }else{
            return "1";
        }
    }

    /**
     * 로그인 성공 후 세션에 현재 사용자 아이디를 저장한다.
     */
    @RequestMapping("/loginOk")
    public String loginOk (String m_id, HttpServletRequest request){
        request.getSession().setAttribute("m_id", m_id);
        return "redirect:/";
    }

    /**
     * 세션에서 로그인 정보를 제거한다.
     */
    @RequestMapping("/logout")
    public String logout (HttpServletRequest request){
        request.getSession().removeAttribute("m_id");
        return "redirect:/";
    }

    /**
     * 아이디 찾기 화면으로 이동한다.
     */
    @RequestMapping("/findId")
    public String findId (){
        return "/login/findId";
    }

    /**
     * 조건에 맞는 아이디를 찾아 팝업 스크립트로 응답한다.
     */
    @RequestMapping("/wannaGetId/{checkedValue}/{findValue}")
    @ResponseBody
    public String wannaGetId(@PathVariable String checkedValue,@PathVariable String findValue){
        String m_id = memberService.findGetId(checkedValue,findValue);
        if(m_id==null){
            return "<script>alert('존재하는 아이디가 없습니다.');location.href='/login/findId';</script>";
        }else{
            return "<script>alert('" + m_id + " 입니다.');window.close();</script>";
        }
    }

    /**
     * 회원 정보 확인 후 비밀번호 재설정 메일을 발송한다.
     */
    @RequestMapping("/wannaGetPw/{pw_id}/{pw_email}")
    @ResponseBody
    public String wannaGetPw(@PathVariable String pw_id,@PathVariable String pw_email) throws MessagingException, IOException {
        int count = memberService.getCount(pw_id,pw_email);
        if(count==0){
            return "<script>alert('존재하는 정보가 없습니다.');location.href='/login/findId';</script>";
        }else{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(pw_email);
            helper.setSubject("풋살 매니저 카페인 비밀번호 변경");
            helper.setText("<html><a href='http://localhost:8080/login/pwChange?m_id=" + pw_id + "'>비밀번호 변경하기</a></html>",true);
            javaMailSender.send(message);

            return "<script>alert('메일을 확인해주세요.'); window.close();</script>";
        }
    }

    /**
     * 비밀번호 변경 화면으로 이동한다.
     */
    @RequestMapping("/pwChange")
    public String pwChange(String m_id,Model model){
        model.addAttribute("m_id",m_id);
        return "/login/pwChange";
    }

    /**
     * 새 비밀번호를 암호화해서 저장한다.
     */
    @RequestMapping("/pwChangeDo")
    @ResponseBody
    public String pwChangeDo(String m_id,String m_pw){
        memberService.changePw(m_id,passwordEncoder.encode(m_pw));
        return "<script>alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.');location.href='/'</script>";
    }
}
