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

	static int pageNum = 10; // 더보기 페이지 변수
	static int addcount = 0; // 값을 증가시켜 추가 여부를 확인할 조건 값
	static int maincount = 0; // 값을 비교할 저장소.

	static String keywordStack = ""; // 키워드 값을 저장할 공간

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
	public String placelist(@RequestParam(value = "keyword", required = false) String keyword,
							@RequestParam(value = "pageAdd", required = false) String pageAdd,
							Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("keyword", keywordStack); // 초기값 대입("")
		map.put("pageNum", pageNum); // 초기값 대입()

//		List<PlaceDto> list = paymentService.selectPageing();

		String i_no="";

		model.addAttribute("size", paymentService.selectAll().size());

		if (pageAdd == null || keyword == null){
			// 추가 기능이 실행되지 않았다면 main 카운트도 증가시키지 않음.
			if (addcount > 0) {
				maincount++;
			}

			// 새로고침 더보기 문장이 실행되지 않았을때 실행되는 로직
			if (addcount < maincount) {
				pageNum = 10;
				keywordStack = ""; // 전체 검색
				maincount = 0; // 메인 카운트 초기화
				addcount = 0; // 추가 카운트 초기화
			}
			List<PlaceDto> list = paymentService.selectPageing(map);

			for(PlaceDto p : list){
				String s = p.getI_no().split(" ")[0];
				p.setMainImg("../uploadImg/place/" + placeService.getFname(s));
			}

			model.addAttribute("lists", list);

			return "placebbs/placelist";
		}else {

			// 값이 있다면 페이지값 + 5증가
			pageNum += Integer.valueOf(pageAdd); // 페이지 증가
			keywordStack = keyword; // 검색 값 받아오기.
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