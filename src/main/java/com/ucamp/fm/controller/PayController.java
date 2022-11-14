package com.ucamp.fm.controller;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import com.ucamp.fm.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

	@Autowired
	PaymentService paymentService;

	@RequestMapping ("/pay_reservation")
	public String pay_reservation(Model model, HttpServletRequest request,String time,String dateSet,String p_no) {

		String m_id = (String) request.getSession().getAttribute("m_id");
		if(m_id=="" || m_id.equals("")){
			return"redirect:/login/login";
		}
		MemberDto member = paymentService.getMember(m_id);

		model.addAttribute("p_no", p_no);
		model.addAttribute("member", member);
		model.addAttribute("time", time);
		model.addAttribute("dateSet", dateSet);

		return "placebbs/pay_reservation";
	}

	@GetMapping("/placelist")
	public String placelist(Model model) {
		model.addAttribute("lists", paymentService.selectAll());
		model.addAttribute("size", paymentService.selectAll().size());
		return "placebbs/placelist";
	}

	@RequestMapping("/placeread")
	public String placeRead(HttpServletRequest request,Model model,String p_no) {
		LocalDate today = LocalDate.now();

		PlaceDto place = paymentService.selectPlace(p_no);
		model.addAttribute("place",place);
		model.addAttribute("today",today);
		return "placebbs/placeread";
	}

	@RequestMapping("/kakaoPay")
	public String insertReservation(ReservationDto rDto){
		paymentService.insertReservation(rDto);
		return "/login/mypage";
	}

	@RequestMapping("/rserveCheck")
	@ResponseBody
	public List<String> reserveCheck(String r_date, String p_no){
		List<String> r_time = paymentService.reserveCheck(r_date,p_no);
		return r_time;
	}
}