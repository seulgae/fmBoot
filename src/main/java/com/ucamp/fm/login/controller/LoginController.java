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


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @RequestMapping("/join")
    public String join () {
        return "login/join";
    }

    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck (String m_id){
        int flag = memberService.idCheck(m_id);
        return flag;
    }

    @RequestMapping("/joinInsert")
    public String joinInsert (MemberDto member){
        member.setM_pw(passwordEncoder.encode(member.getM_pw()));
        memberService.join(member);
        return "redirect:/";
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public String loginCheck (String m_id, String m_pw, Model model, HttpServletRequest request){
        if(!passwordEncoder.matches(m_pw,memberService.getPw(m_id))){
            return "0";
        }else{
            return "1";
        }
    }

    @RequestMapping("/loginOk")
    public String loginOk (String m_id, HttpServletRequest request){
        request.getSession().setAttribute("m_id", m_id);

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout (HttpServletRequest request){
        request.getSession().removeAttribute("m_id");
        return "redirect:/";
    }

    @RequestMapping("/findId")
    public String findId (){
        return "/login/findId";
    }
    @RequestMapping("/wannaGetId/{checkedValue}/{findValue}")
    @ResponseBody
    public String wannaGetId(@PathVariable String checkedValue,@PathVariable String findValue){
        String m_id = memberService.findGetId(checkedValue,findValue);
        if(m_id==null){
            return "<script>alert('Ï°¥Ïû¨?òÎäî ?ÑÏù¥?îÍ? ?ÜÏäµ?àÎã§.');location.href='/login/findId';</script>";
        }else{
            return "<script>alert('"+m_id+" ?ÖÎãà??');window.close();</script>";
        }
    }

    @RequestMapping("/wannaGetPw/{pw_id}/{pw_email}")
    @ResponseBody
    public String wannaGetPw(@PathVariable String pw_id,@PathVariable String pw_email) throws MessagingException, IOException {
        int count = memberService.getCount(pw_id,pw_email);
        if(count==0){
            return "<script>alert('Ï°¥Ïû¨?òÎäî ?ïÎ≥¥Í∞Ä ?ÜÏäµ?àÎã§.');location.href='/login/findId';</script>";
        }else{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(pw_email);
            helper.setSubject("?ãÏÇ¥ Îß§Îãà?Ä ?àÌéò?¥Ï? ÎπÑÎ?Î≤àÌò∏ Î≥ÄÍ≤?");
            helper.setText("<html><a href='http://localhost:8085/login/pwChange?m_id="+pw_id+"'>ÎπÑÎ?Î≤àÌò∏ Î≥ÄÍ≤ΩÌïòÍ∏?/a></html>",true);
            javaMailSender.send(message);

            return "<script>alert('Î©îÏùº???ïÏù∏?¥Ï£º?∏Ïöî.'); window.close();</script>";
        }
    }

    @RequestMapping("/pwChange")
    public String pwChange(String m_id,Model model){
        model.addAttribute("m_id",m_id);
        return "/login/pwChange";
    }

    @RequestMapping("/pwChangeDo")
    @ResponseBody
    public String pwChangeDo(String m_id,String m_pw){
        memberService.changePw(m_id,passwordEncoder.encode(m_pw));
        return "<script>alert('ÎπÑÎ?Î≤àÌò∏Í∞Ä Î≥ÄÍ≤ΩÎêò?àÏäµ?àÎã§. ?§Ïãú Î°úÍ∑∏???¥Ï£º?∏Ïöî.');location.href='/'</script>";
    }

}

