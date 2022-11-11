package com.ucamp.fm.controller;

import com.ucamp.fm.dto.TeamDto;
import com.ucamp.fm.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/teammanage")
public class TeamController {
    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("/teammanage")
    public String teamForm(@RequestParam(defaultValue = "") String keyword, Model model) {
        if (keyword.equals("")) {
            model.addAttribute("teamlist", teamService.getTeamList());
        } else {
            keyword = "%" + keyword + "%";
            model.addAttribute("teamlist", teamService.searchTeam(keyword));
        }

        return "/team/teammanage";
    }

    @GetMapping("/teamcreate")
    public String teamCreate(HttpServletRequest request, Model model) {
		String t_id = (String)request.getSession().getAttribute("m_id");
		model.addAttribute("team", t_id);

        return "/team/teamcreate";
    }

    @RequestMapping("/teaminsert")
    @ResponseBody
    public String teaminsert(HttpServletRequest request, String t_name, String t_age, String t_skill, String t_kind) {
        String t_id = (String) request.getSession().getAttribute("m_id");
        teamService.teamInsert(t_name, t_age, t_skill, t_kind, t_id);

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/teamupinsert")
    @ResponseBody
    public String teamupinsert() {
        

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/teamupdate")
    @ResponseBody
    public String teamupdate(HttpServletRequest request, String t_no, String t_name, String t_age, String t_skill, String t_kind, Model model) {
        TeamDto updateTeam = teamService.selectTeam(t_no);
        String t_id = (String) request.getSession().getAttribute("m_id");
        teamService.teamUpdate(t_no, t_name, t_age, t_skill, t_kind, t_id);
        model.addAttribute("team", t_id);

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/teamdetail")
    public String teamdetail(String t_no, Model model) {
        model.addAttribute("team", teamService.selectTeam(t_no));

        return "/team/teamdetail";
    }
}
