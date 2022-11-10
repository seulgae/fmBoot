package com.ucamp.fm.controller;

import com.ucamp.fm.dto.TeamDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teammanage")
public class TeamController {


	@GetMapping("/teammanage")
	public String teamForm() {

		return "/team/teammanage";
	}

	@GetMapping("/teamcreate")
	public String teamCreate(Model model) {
		//model.addAttribute("team", );

		return "/team/teamcreate";
	}

	@RequestMapping("/teamdetail")
	public String teamDetail() {

		return "/team/teamdetail";
	}
}
