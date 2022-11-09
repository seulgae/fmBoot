package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teammanage")
public class TeamController {

	@GetMapping("/teammanage")
	public String teamForm() {

		return "/team/teammanage";
	}

	@GetMapping("/teamcreate")
	public String teamCreate() {

		return "/team/teamcreate";
	}

	@RequestMapping("/teamdetail")
	public String teamDetail() {

		return "/team/teamdetail";
	}
}
