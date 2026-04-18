package com.ucamp.fm.common.controller;

import com.ucamp.fm.login.dto.MemberDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 모든 뷰에서 공통으로 사용할 로그인 사용자 정보를 모델에 주입한다.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginMember")
    public MemberDto loginMember(HttpServletRequest request) {
        Object loginMember = request.getAttribute("loginMember");
        return loginMember instanceof MemberDto memberDto ? memberDto : null;
    }

    @ModelAttribute("currentMemberId")
    public String currentMemberId(HttpServletRequest request) {
        Object currentMemberId = request.getAttribute("currentMemberId");
        return currentMemberId instanceof String memberId ? memberId : null;
    }
}
