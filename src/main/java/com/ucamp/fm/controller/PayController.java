package com.ucamp.fm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PayController {

	@GetMapping("/pay_reservation")
	public String pay_reservation(Model model) {

		return "pay_reservation";
	}

	@GetMapping("/placeRead")
	public String placeRead(Model model) {

		return "placeRead";
	}

}
