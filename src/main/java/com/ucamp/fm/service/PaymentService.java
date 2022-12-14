package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PayDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;

import java.util.HashMap;
import java.util.List;

public interface PaymentService {

    public MemberDto getMember(String m_id);

    List<PlaceDto> selectAll();
    PlaceDto selectPlace(String p_no);

    MemberDto selectMember(String m_id);

    void insertReservation(ReservationDto reservationDto);

    ReservationDto reservationAll(String p_no);

    List<String> reserveCheck(String r_date,String p_no);

    void Insert(PayDto paydto);

    List<PlaceDto> selectPageing(HashMap<String, Object> map);
    
}
