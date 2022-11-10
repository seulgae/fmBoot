package com.ucamp.fm.service;

import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements  PaymentService{
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public String getM_Phone(String m_id) {
        return paymentMapper.getM_Phone(m_id);
    }

    @Override
    public List<PlaceDto> selectAll() {
        return paymentMapper.selectAll();
    }
}
