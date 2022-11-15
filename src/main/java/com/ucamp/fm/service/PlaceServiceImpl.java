package com.ucamp.fm.service;

import com.ucamp.fm.dto.ImageDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.mapper.PlaceMapper;
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


}
