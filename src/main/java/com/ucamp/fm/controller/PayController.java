package com.ucamp.fm.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PayDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import com.ucamp.fm.service.PaymentService;
import com.ucamp.fm.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

	@Autowired
	PaymentService paymentService;

	@Autowired
	PlaceService placeService;

	private IamportClient api;

	public PayController() {

		this.api = new IamportClient("2425130278403717",
				"nyP4HPSxZUBklaQziLkPXcTx8HHcWlk224W1RIF3qlEZEGDnM3M0QzRc2lnrTmmPKrulQvttBkhBeNmj");
	}


	@RequestMapping ("/pay_reservation")
	public String pay_reservation(Model model, HttpServletRequest request,String time,String dateSet, @RequestParam String p_no) {
		String m_id = (String) request.getSession().getAttribute("m_id");
		if(m_id=="" || m_id.equals("")){
			return"redirect:/login/login";
		}
		MemberDto member = paymentService.getMember(m_id);

		model.addAttribute("p_no", p_no);
		model.addAttribute("member", member);
		model.addAttribute("time", time);
		model.addAttribute("dateSet", dateSet);
		model.addAttribute("p_info", paymentService.selectPlace(p_no));

		return "placebbs/pay_reservation";
	}

	//결제 진행
	@PostMapping(value = "/pay_reservation.do/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> payment(@PathVariable(value = "imp_uid") String imp_uid, HttpServletRequest request, PayDto paydto) throws IamportResponseException, IOException {
		String m_id = (String) request.getSession().getAttribute("m_id");
		paydto.setPay_id(m_id);

		paydto.setPay_code(imp_uid);

		paymentService.Insert(paydto);
		return null;
	}

	@GetMapping("/placelist")
	public String placelist(@RequestParam(defaultValue = "") String keyword, Model model) {
		if (keyword.equals("")) {
			model.addAttribute("lists", paymentService.selectAll());
			model.addAttribute("size", paymentService.selectAll().size());
		} else {
			keyword = "%" + keyword + "%";
			model.addAttribute("lists", paymentService.searchPlace(keyword));
		}
		return "placebbs/placelist";
	}

	@RequestMapping("/placeread")
	public String placeRead(HttpServletRequest request,Model model,String p_no) {
		LocalDate today = LocalDate.now();
		PlaceDto place = paymentService.selectPlace(p_no);
		String[] img = place.getI_no().split(" ");
		ArrayList<String> arrImg = new ArrayList<>();
		String firstImg = "";
		for(String s : img){
			String fName = placeService.getFname(s);
			arrImg.add("../uploadImg/place/"+fName);
			if(firstImg==""){
				firstImg = "../uploadImg/place/"+fName;
			}
		}
		model.addAttribute("firstImg",firstImg);
		model.addAttribute("arrImg",arrImg);
		model.addAttribute("place",place);
		model.addAttribute("today",today);
		return "placebbs/placeread";
	}

	@RequestMapping("/kakaoPay")
	public String insertReservation(ReservationDto rDto){
		paymentService.insertReservation(rDto);
		return "redirect:/mypage/mypage";
	}

	@RequestMapping("/rserveCheck")
	@ResponseBody
	public List<String> reserveCheck(String r_date, String p_no){
		List<String> r_time = paymentService.reserveCheck(r_date,p_no);
		return r_time;
	}
}