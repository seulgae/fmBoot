package com.ucamp.fm.controller;

import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

	@Autowired
	PaymentService paymentService;

	@RequestMapping ("/pay_reservation")
	public String pay_reservation(Model model, HttpServletRequest request) {

		String m_id = (String) request.getSession().getAttribute("m_id");
		String m_phone = paymentService.getM_Phone(m_id);

		model.addAttribute("m_phone", m_phone);

		return "placebbs/pay_reservation";
	}

	@GetMapping("/placelist")
	public String placelist(Model model) {
		model.addAttribute("lists", paymentService.selectAll());
		model.addAttribute("size", paymentService.selectAll().size());
		return "placebbs/placelist";
	}

	@GetMapping("/placeread")
	public String placeRead(Model model) {

		return "placebbs/placeread";
	}

}