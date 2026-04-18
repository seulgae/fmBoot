package com.ucamp.fm.common.interceptor;

import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.login.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 세션의 m_id를 기준으로 현재 로그인 사용자를 request/model 공통 속성으로 노출한다.
 */
@Component
public class LoginMemberInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public LoginMemberInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("loginMember", null);
            request.setAttribute("currentMemberId", null);
            return true;
        }

        Object sessionMemberId = session.getAttribute("m_id");
        if (!(sessionMemberId instanceof String memberId) || memberId.isBlank()) {
            session.removeAttribute("loginMember");
            request.setAttribute("loginMember", null);
            request.setAttribute("currentMemberId", null);
            return true;
        }

        MemberDto loginMember = memberService.getMember(memberId);
        if (loginMember == null) {
            session.removeAttribute("loginMember");
            request.setAttribute("loginMember", null);
            request.setAttribute("currentMemberId", memberId);
            return true;
        }

        request.setAttribute("loginMember", loginMember);
        request.setAttribute("currentMemberId", memberId);
        session.setAttribute("loginMember", loginMember);
        return true;
    }
}
