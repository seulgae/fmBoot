package com.ucamp.fm.mypage.service.impl;

import com.ucamp.fm.mypage.dto.ImageDto;
import com.ucamp.fm.mypage.dto.PlaceDto;
import com.ucamp.fm.mypage.mapper.PlaceMapper;
import com.ucamp.fm.mypage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService{
    @Autowired
    PlaceMapper pMapper;

    @Override
    public void insertImage(ImageDto imageDto) {
        pMapper.insertImage(imageDto);
    }

    @Override
    public String getSeq() {
        return pMapper.getSeq();
    }

    @Override
    public String getFname(String i_no) {
        return pMapper.getFname(i_no);
    }

    @Override
    public String getI_no(String p_no) {
        return pMapper.getI_no(p_no);
    }


}
