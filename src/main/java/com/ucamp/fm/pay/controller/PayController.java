package com.ucamp.fm.pay.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.mypage.dto.PlaceDto;
import com.ucamp.fm.mypage.service.PlaceService;
import com.ucamp.fm.pay.dto.PayDto;
import com.ucamp.fm.pay.dto.ReservationDto;
import com.ucamp.fm.pay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 구장 목록, 예약, 결제 흐름을 담당하는 컨트롤러.
 */
@Controller
@RequestMapping("/payment")
public class PayController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PlaceService placeService;

    private final IamportClient api;

    public PayController() {
        this.api = new IamportClient("2425130278403717",
                "nyP4HPSxZUBklaQziLkPXcTx8HHcWlk224W1RIF3qlEZEGDnM3M0QzRc2lnrTmmPKrulQvttBkhBeNmj");
    }

    /**
     * 예약/결제 화면으로 이동한다.
     */
    @RequestMapping("/pay_reservation")
    public String pay_reservation(Model model, HttpServletRequest request, String time, String dateSet,
                                  @RequestParam String p_no) {
        String m_id = (String) request.getSession().getAttribute("m_id");
        if (m_id == null || m_id.isBlank()) {
            return "redirect:/login/login";
        }

        MemberDto member = paymentService.getMember(m_id);
        model.addAttribute("p_no", p_no);
        model.addAttribute("member", member);
        model.addAttribute("time", time);
        model.addAttribute("dateSet", dateSet);
        model.addAttribute("p_info", paymentService.selectPlace(p_no));
        return "pay/pay_reservation";
    }

    /**
     * 결제 완료 후 결제 이력을 저장한다.
     */
    @PostMapping(value = "/pay_reservation.do/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> payment(@PathVariable(value = "imp_uid") String imp_uid,
                                            HttpServletRequest request,
                                            PayDto paydto) throws IamportResponseException, IOException {
        String m_id = (String) request.getSession().getAttribute("m_id");
        paydto.setPay_id(m_id);
        paydto.setPay_code(imp_uid);
        paymentService.Insert(paydto);
        return null;
    }

    /**
     * 목록형 구장 화면과 단순 상세 검색 조건을 함께 처리한다.
     */
    @GetMapping("/placelist")
    public String placelist(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "region", required = false) String region,
                            @RequestParam(value = "book", required = false) String book,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            Model model) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword == null ? "" : keyword.trim());
        map.put("region", region == null ? "" : region.trim());
        map.put("book", book == null ? "" : book.trim());
        map.put("pageNum", Math.max(pageSize, 10));

        List<PlaceDto> list = paymentService.selectPageing(map);
        applyMainImages(list);

        model.addAttribute("lists", list);
        model.addAttribute("keyword", map.get("keyword"));
        model.addAttribute("region", map.get("region"));
        model.addAttribute("book", map.get("book"));
        model.addAttribute("pageSize", Math.max(pageSize, 10));
        model.addAttribute("hasMore", list.size() >= Math.max(pageSize, 10));
        return "pay/placelist";
    }

    /**
     * 구장 상세 정보를 조회한다.
     */
    @RequestMapping("/placeread")
    public String placeRead(HttpServletRequest request, Model model, String p_no) {
        LocalDate today = LocalDate.now();
        PlaceDto place = paymentService.selectPlace(p_no);
        String[] img = place.getI_no().split(" ");
        ArrayList<String> arrImg = new ArrayList<>();
        String firstImg = "";

        for (String s : img) {
            String fName = placeService.getFname(s);
            arrImg.add("../uploadImg/place/" + fName);
            if (firstImg.isEmpty()) {
                firstImg = "../uploadImg/place/" + fName;
            }
        }

        model.addAttribute("firstImg", firstImg);
        model.addAttribute("arrImg", arrImg);
        model.addAttribute("place", place);
        model.addAttribute("today", today);
        return "pay/placeread";
    }

    /**
     * 예약 정보를 저장하고 마이페이지로 이동한다.
     */
    @RequestMapping("/kakaoPay")
    public String insertReservation(ReservationDto rDto) {
        paymentService.insertReservation(rDto);
        return "redirect:/mypage/mypage";
    }

    /**
     * 날짜별 예약된 시간 목록을 조회한다.
     */
    @RequestMapping("/rserveCheck")
    @ResponseBody
    public List<String> reserveCheck(String r_date, String p_no) {
        return paymentService.reserveCheck(r_date, p_no);
    }

    /**
     * 목록 화면에서 바로 쓸 대표 이미지 경로를 계산한다.
     */
    private void applyMainImages(List<PlaceDto> list) {
        for (PlaceDto place : list) {
            String[] imageIds = place.getI_no() == null ? new String[0] : place.getI_no().split(" ");
            if (imageIds.length == 0 || imageIds[0].isBlank()) {
                place.setMainImg("../uploadImg/place/app.jpg");
                continue;
            }

            String fileName = placeService.getFname(imageIds[0]);
            if (fileName == null || fileName.isBlank()) {
                fileName = "app.jpg";
            }

            place.setMainImg("../uploadImg/place/" + fileName);
        }
    }
}
