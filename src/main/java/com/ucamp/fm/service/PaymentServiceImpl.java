package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PayDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import com.ucamp.fm.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements  PaymentService{
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public MemberDto getMember(String m_id) {
        return paymentMapper.getMember(m_id);
    }

    @Override
    public List<PlaceDto> selectAll() {
        return paymentMapper.selectAll();
    }

    @Override
    public PlaceDto selectPlace(String p_no) {
        return paymentMapper.selectPlace(p_no);
    }

    @Override
    public MemberDto selectMember(String m_id) {
        return paymentMapper.selectMember(m_id);
    }

    @Override
    public void insertReservation(ReservationDto reservationDto) {
         paymentMapper.insertReservation(reservationDto);
    }

    @Override
    public ReservationDto reservationAll(String p_no) {
        return paymentMapper.reservationAll(p_no);
    }

    @Override
    public List<String> reserveCheck(String r_date, String p_no) {
        return paymentMapper.reserveCheck(r_date,p_no);
    }

    @Override
    public void Insert(PayDto paydto) {
        paymentMapper.InsertPay(paydto);
    }
}
