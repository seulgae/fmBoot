package com.ucamp.fm.service;

import com.ucamp.fm.dto.ImageDto;

public interface PlaceService {
    public void insertImage(ImageDto imageDto);
    public String getSeq();

    public String getFname(String i_no);
}
