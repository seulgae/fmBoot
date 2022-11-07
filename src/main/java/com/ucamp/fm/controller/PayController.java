package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayController {

	@RequestMapping("/pay_reservation")
	public String pay_reservation(Model model) {
		
		return "pay_reservation";
	}
	
}
