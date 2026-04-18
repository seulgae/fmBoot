package com.ucamp.fm.team.controller;

import com.ucamp.fm.team.dto.GmatchDto;
import com.ucamp.fm.team.dto.TeamDto;
import com.ucamp.fm.team.service.GmatchService;
import com.ucamp.fm.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 팀 생성, 상세, 멤버 관리, 경기 기록 등록을 담당하는 컨트롤러.
 */
@Controller
@RequestMapping("/teammanage")
public class TeamController {
    @Autowired
    TeamService teamService;

    @Autowired
    GmatchService gmatchService;

    /**
     * 팀 목록 또는 검색 결과를 조회한다.
     */
    @GetMapping("/teammanage")
    public String teamForm(@RequestParam(defaultValue = "") String keyword, Model model) {
        if (keyword.equals("")) {
            model.addAttribute("teamlist", teamService.getTeamList());
            model.addAttribute("tno_list", teamService.getTno());
        } else {
            keyword = "%" + keyword + "%";
            model.addAttribute("teamlist", teamService.searchTeam(keyword));
        }

        return "/team/teammanage";
    }

    /**
     * 팀 전적 요약을 비동기로 조회한다.
     */
    @RequestMapping("/countmatch")
    @ResponseBody
    public String[] countmatch(String t_no) {
        String all = gmatchService.selectAll(t_no) == null ? "0" : gmatchService.selectAll(t_no);
        String win = gmatchService.selectWin(t_no) == null ? "0" : gmatchService.selectWin(t_no);
        String draw = gmatchService.selectDraw(t_no) == null ? "0" : gmatchService.selectDraw(t_no);
        String lose = gmatchService.selectLose(t_no) == null ? "0" : gmatchService.selectLose(t_no);
        return new String[] { t_no, all, win, draw, lose };
    }

    /**
     * 팀 생성 화면으로 이동한다.
     */
    @GetMapping("/teamcreate")
    public String teamCreate(HttpServletRequest request, Model model) {
        String t_id = (String)request.getSession().getAttribute("m_id");
        model.addAttribute("team", t_id);
        return "/team/teamcreate";
    }

    /**
     * 새 팀을 생성한다.
     */
    @RequestMapping("/teaminsert")
    @ResponseBody
    public String teaminsert(HttpServletRequest request, String t_name, String t_region, String t_age,
                             String t_skill, String t_uniform, String t_kind, String t_introduce) {
        String t_id = (String) request.getSession().getAttribute("m_id");
        teamService.teamInsert(t_name, t_region, t_age, t_skill, t_uniform, t_kind, t_introduce, t_id);
        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    /**
     * 팀 수정 화면으로 이동한다.
     */
    @RequestMapping("/teamupdate")
    public String teamupdate(TeamDto dto, Model model) {
        model.addAttribute("team", teamService.selectTeam(dto.getT_no()));
        return "/team/teamupdate";
    }

    /**
     * 팀 상세 정보와 전적을 함께 조회한다.
     */
    @RequestMapping("/teamdetail")
    public String teamdetail(HttpServletRequest request, String t_no, Model model) {
        int all = Integer.parseInt(gmatchService.selectAll(t_no) == null ? "0" : gmatchService.selectAll(t_no));
        int win = Integer.parseInt(gmatchService.selectWin(t_no) == null ? "0" : gmatchService.selectWin(t_no));
        int draw = Integer.parseInt(gmatchService.selectDraw(t_no) == null ? "0" : gmatchService.selectDraw(t_no));
        int lose = Integer.parseInt(gmatchService.selectLose(t_no) == null ? "0" : gmatchService.selectLose(t_no));
        int gf = Integer.parseInt(gmatchService.selectGf(t_no) == null ? "0" : gmatchService.selectGf(t_no));
        int ga = Integer.parseInt(gmatchService.selectGa(t_no) == null ? "0" : gmatchService.selectGa(t_no));

        double rate1 = (((double) win / all) * 100);
        int rate = Double.isNaN(rate1) ? 0 : (int) rate1;

        ArrayList<String> memberList = new ArrayList<>();
        String m = teamService.getMember(t_no);
        if(m != null) {
            String[] mem = m.split(" ");
            for (String member : mem) {
                memberList.add(member);
            }
        }

        String m_id = (String) request.getSession().getAttribute("m_id");
        int userCheck = 0;
        if(m_id != null) {
            userCheck = teamService.userCheck(m_id, t_no);
        }

        model.addAttribute("userCheck", userCheck);
        model.addAttribute("memberList", memberList);
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

    /**
     * 팀 수정 내용을 저장한다.
     */
    @RequestMapping("/teamupdateset")
    @ResponseBody
    public String teamupdateset(TeamDto tDto, HttpServletRequest request) {
        teamService.teamUpdate(tDto);
        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    /**
     * 팀을 삭제한다.
     */
    @RequestMapping("/teamdelete")
    public String teamdelete(String t_no) {
        teamService.deleteTeam(t_no);
        return "redirect:/teammanage/teammanage";
    }

    /**
     * 경기 기록 추가 화면으로 이동한다.
     */
    @RequestMapping("/addmatch")
    public String addmatch(String t_no, Model model) {
        model.addAttribute("t_no", t_no);
        return "/team/gmatch";
    }

    /**
     * 팀명을 조건으로 팀 목록을 검색한다.
     */
    @RequestMapping("/findteam")
    @ResponseBody
    public String findteam(String teamName) {
        List<TeamDto> tDto = teamService.findTeam("%" + teamName + "%");
        String str = "";
        for(TeamDto t : tDto) {
            str += "<li value='" + t.getT_no() + "' onclick='find(\"" + t.getT_name() + "\",\"" + t.getT_no() + "\")'> " + t.getT_name() + "</li>";
        }
        return str;
    }

    /**
     * 팀원 추가 화면으로 이동한다.
     */
    @RequestMapping("/addmember")
    public String addMember(String t_no, Model model){
        model.addAttribute("t_no", t_no);
        return "/team/addmember";
    }

    /**
     * 팀원 검색 결과를 조회한다.
     */
    @RequestMapping("/findMember")
    public String findMember(String m_id, Model model){
        model.addAttribute("findMem",teamService.findMember("%" + m_id + "%"));
        return "/team/findmember";
    }

    /**
     * 경기 기록을 저장한다.
     */
    @RequestMapping("/gmatchinsert")
    @ResponseBody
    public String gmatchinsert(GmatchDto gDto) {
        gmatchService.gmatchInsert(gDto);
        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    /**
     * 팀 프로필 이미지 등록 화면으로 이동한다.
     */
    @RequestMapping("/teamprofile")
    public String teamprofile(Model model, String t_no) {
        model.addAttribute("team", teamService.selectTeam(t_no));
        return "team/addprofile";
    }

    /**
     * 팀 프로필 이미지를 업로드하고 저장한다.
     */
    @RequestMapping("/addprofile")
    @ResponseBody
    public String addprofile(HttpServletRequest request, @RequestParam("t_thum") MultipartFile t_thum, String t_no)
            throws IllegalStateException, IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + "uploadImg/teamProfileImg/";

        if (!t_thum.getOriginalFilename().isEmpty()) {
            t_thum.transferTo(new File(path + t_thum.getOriginalFilename()));
        }

        teamService.addTeamPhoto(new TeamDto(t_no, t_thum.getOriginalFilename()));
        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    /**
     * 검색한 사용자를 팀원으로 등록한다.
     */
    @RequestMapping("/insertMember")
    @ResponseBody
    public String insertMember(String str_member, String t_no){
        teamService.insertMember(str_member, t_no);
        return "<script>alert('등록이 완료되었습니다.');window.opener.location.reload(); window.close();</script>";
    }
}
