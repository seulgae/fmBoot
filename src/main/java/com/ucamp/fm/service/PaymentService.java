package com.ucamp.fm.service;

import com.ucamp.fm.dto.PlaceDto;

import java.util.List;

public interface PaymentService {

    public String getM_Phone(String m_id);

    List<PlaceDto> selectAll();
}
