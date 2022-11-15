package com.ucamp.fm.controller;

import com.ucamp.fm.dto.GmatchDto;
import com.ucamp.fm.dto.TeamDto;
import com.ucamp.fm.service.GmatchServiceImpl;
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

    @Autowired
    GmatchServiceImpl gmatchService;

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
    public String teaminsert(HttpServletRequest request, String t_name, String t_region, String t_age, String t_skill, String t_uniform, String t_kind, String t_introduce) {
        String t_id = (String) request.getSession().getAttribute("m_id");
        teamService.teamInsert(t_name, t_region, t_age, t_skill, t_uniform, t_kind, t_introduce, t_id);

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/teamupdate")
    public String teamupdate(TeamDto dto, Model model) {
        model.addAttribute("team", teamService.selectTeam(dto.getT_no()));

        return "/team/teamupdate";
    }

    @RequestMapping("/teamdetail")
    public String teamdetail(String t_no, Model model) {

        int all = Integer.parseInt(gmatchService.selectAll(t_no) == null ? "0" : gmatchService.selectAll(t_no));
        int win = Integer.parseInt(gmatchService.selectWin(t_no) == null ? "0" : gmatchService.selectWin(t_no));
        int draw = Integer.parseInt(gmatchService.selectDraw(t_no) == null ? "0" : gmatchService.selectDraw(t_no));
        int lose = Integer.parseInt(gmatchService.selectLose(t_no) == null ? "0" : gmatchService.selectLose(t_no));
        int gf = Integer.parseInt(gmatchService.selectGf(t_no) == null ? "0" : gmatchService.selectGf(t_no));
        int ga = Integer.parseInt(gmatchService.selectGa(t_no) == null ? "0" : gmatchService.selectGa(t_no));

        double rate1 = (((double)win / all) * 100);
        int rate;
        if(Double.isNaN(rate1)) {
            rate = 0;
        } else {
            rate = (int)rate1;
        }

        model.addAttribute("team", teamService.selectTeam(t_no));
        model.addAttribute("all", all);
        model.addAttribute("win", win);
        model.addAttribute("draw", draw);
        model.addAttribute("lose", lose);
        model.addAttribute("rate", rate);
        model.addAttribute("gf", gf);
        model.addAttribute("ga", ga);

        return "/team/teamdetail";
    }

    @RequestMapping("/teamupdateset")
    @ResponseBody
    public String teamupdateset(TeamDto tDto, HttpServletRequest request) {
        String t_id = (String) request.getSession().getAttribute("m_id");
        teamService.teamUpdate(tDto);

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/teamdelete")
    public String teamdelete(String t_no) {
        teamService.deleteTeam(t_no);

        return "redirect:/teammanage/teammanage";
    }

    @RequestMapping("/addmatch")
    public String addmatch(String t_no, Model model) {
        model.addAttribute("t_no", t_no);

        return "/team/gmatch";
    }

    @RequestMapping("/findteam")
    @ResponseBody
    public String findteam(String teamName) {
        List<TeamDto> tDto = teamService.findTeam("%" + teamName + "%");
        String str = "";
        for(TeamDto t : tDto) {
            str += "<li value='"+t.getT_no()+"' onclick='find(\""+t.getT_name()+"\",\""+t.getT_no()+"\")'> "+t.getT_name() + "</li>";
        }

        return str;
    }

    @RequestMapping("/addmember")
    public  String addMember(){
        return "/team/addmember";
    }

    @RequestMapping("/findMember")
    public String findMember(String m_id, Model model){
        model.addAttribute("findMem",teamService.findMember("%"+m_id+"%"));
        return "/team/findmember";
    }

    @RequestMapping("/gmatchinsert")
    @ResponseBody
    public String gmatchinsert(GmatchDto gDto) {
        gmatchService.gmatchInsert(gDto);

        return "<script>window.opener.location.reload(); window.close();</script>";
    }
}
