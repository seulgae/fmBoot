package com.ucamp.fm.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 전용 화면 진입을 담당하는 컨트롤러.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 관리자 메인 화면으로 이동한다.
     */
    @RequestMapping("/admin")
    public String adm(){
        return "admin/admin";
    }
}
