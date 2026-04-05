package com.ucamp.fm.pay.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.pay.dto.PayDto;
import com.ucamp.fm.mypage.dto.PlaceDto;
import com.ucamp.fm.pay.dto.ReservationDto;
import com.ucamp.fm.pay.service.PaymentService;
import com.ucamp.fm.mypage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;


@Controller
@RequestMapping("/payment")
public class PayController {

	static int pageNum = 10; // ?�보�??�이지 변??
	static int addcount = 0; // 값을 증�??�켜 추�? ?��?�??�인??조건 �?
	static int maincount = 0; // 값을 비교???�?�소.

	static String keywordStack = ""; // ?�워??값을 ?�?�할 공간

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

		return "pay/pay_reservation";
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
	public String placelist(@RequestParam(value = "keyword", required = false) String keyword,
							@RequestParam(value = "pageAdd", required = false) String pageAdd,
							Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("keyword", keywordStack); // 초기�??�??"")
		map.put("pageNum", pageNum); // 초기�??�??)

//		List<PlaceDto> list = paymentService.selectPageing();

		String i_no="";

		model.addAttribute("size", paymentService.selectAll().size());

		if (pageAdd == null || keyword == null){
			// 추�? 기능???�행?��? ?�았?�면 main 카운?�도 증�??�키지 ?�음.
			if (addcount > 0) {
				maincount++;
			}

			// ?�로고침 ?�보�?문장???�행?��? ?�았?�때 ?�행?�는 로직
			if (addcount < maincount) {
				pageNum = 10;
				keywordStack = ""; // ?�체 검??
				maincount = 0; // 메인 카운??초기??
				addcount = 0; // 추�? 카운??초기??
			}
			List<PlaceDto> list = paymentService.selectPageing(map);

			for(PlaceDto p : list){
				String s = p.getI_no().split(" ")[0];
				p.setMainImg("../uploadImg/place/" + placeService.getFname(s));
			}

			model.addAttribute("lists", list);

			return "pay/placelist";
		}else {

			// 값이 ?�다�??�이지�?+ 5증�?
			pageNum += Integer.valueOf(pageAdd); // ?�이지 증�?
			keywordStack = keyword; // 검??�?받아?�기.
			addcount += 2;
			return "redirect:/payment/placelist";
		}

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
		return "pay/placeread";
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