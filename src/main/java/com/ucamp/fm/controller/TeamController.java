package com.ucamp.fm.controller;

import com.ucamp.fm.dto.GmatchDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.TeamDto;
import com.ucamp.fm.service.GmatchServiceImpl;
import com.ucamp.fm.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
            List<TeamDto> teamList = teamService.getTeamList();
            model.addAttribute("teamlist", teamList);
            model.addAttribute("tno_list", teamService.getTno());
        } else {
            keyword = "%" + keyword + "%";
            model.addAttribute("teamlist", teamService.searchTeam(keyword));
        }

        /*for(TeamDto team : tnolist) {
            String t_no = team.getT_no();

            int all = Integer.parseInt(gmatchService.selectAll(t_no) == null ? "0" : gmatchService.selectAll(t_no));
            int win = Integer.parseInt(gmatchService.selectWin(t_no) == null ? "0" : gmatchService.selectWin(t_no));
            int draw = Integer.parseInt(gmatchService.selectDraw(t_no) == null ? "0" : gmatchService.selectDraw(t_no));
            int lose = Integer.parseInt(gmatchService.selectLose(t_no) == null ? "0" : gmatchService.selectLose(t_no));

            model.addAttribute("all", all);
            model.addAttribute("win", win);
            model.addAttribute("draw", draw);
            model.addAttribute("lose", lose);
        }*/


        return "/team/teammanage";
    }

    @RequestMapping("/countmatch")
    @ResponseBody
    public String[] countmatch(String t_no) {
        String all = gmatchService.selectAll(t_no) == null ? "0" : gmatchService.selectAll(t_no);
        String win = gmatchService.selectWin(t_no) == null ? "0" : gmatchService.selectWin(t_no);
        String draw = gmatchService.selectDraw(t_no) == null ? "0" : gmatchService.selectDraw(t_no);
        String lose = gmatchService.selectLose(t_no) == null ? "0" : gmatchService.selectLose(t_no);
        String[] s = { t_no, all, win, draw, lose };

        return s;
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
    public String teamdetail(HttpServletRequest request, String t_no, Model model) {

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

        ArrayList<String> memberList = new ArrayList<>();
        String m = teamService.getMember(t_no);
        if(m != null) {
            String[] mem =  m.split(" ");
            for(int i = 0; i < mem.length; i++){
                memberList.add(mem[i]);
            }
        }

        String m_id = (String)request.getSession().getAttribute("m_id");
        int userCheck = 0;
        if(m_id != null) {
            userCheck = teamService.userCheck(m_id, t_no);
        }

        model.addAttribute("userCheck", userCheck);
        model.addAttribute("memberList",memberList);
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
    public  String addMember(String t_no,Model model){
        model.addAttribute("t_no",t_no);
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

    @RequestMapping("/teamprofile")
    public String teamprofile(Model model, String t_no) {
        model.addAttribute("team", teamService.selectTeam(t_no));

        return "team/addprofile";
    }

    @RequestMapping("/addprofile")
    @ResponseBody
    public String addprofile(HttpServletRequest request, @RequestParam("t_thum") MultipartFile t_thum, String t_no) throws IllegalStateException, IOException {
        String t_id = (String) request.getSession().getAttribute("m_id");

        String PATH = request.getSession().getServletContext().getRealPath("/") + "uploadImg/teamProfileImg/";

        // 프로젝트 내 webapp 폴더를 찾아줌, webapp 폴더 없을 경우 appdate안의 톰캣 캐시 임시저장 폴더에 저장시킴
        // transferTo : 파일 데이터를 지정한 file로 저장
        // getOriginalFilename : 클라이언트의 원본 파일명 반환

        if (!t_thum.getOriginalFilename().isEmpty()) {
            t_thum.transferTo(new File(PATH + t_thum.getOriginalFilename()));
        }

        teamService.addTeamPhoto(new TeamDto(t_no, t_thum.getOriginalFilename()));

        return "<script>window.opener.location.reload(); window.close();</script>";
    }

    @RequestMapping("/insertMember")
    @ResponseBody
    public String insertMember(String str_member,String t_no){
        teamService.insertMember(str_member,t_no);
        return "<script>alert('등록이 완료되었습니다.');window.opener.location.reload(); window.close();</script>";
    }
}
