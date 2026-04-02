package com.ucamp.fm.mypage.service;

import com.ucamp.fm.mypage.dto.ImageDto;
import com.ucamp.fm.mypage.dto.PlaceDto;

public interface PlaceService {
    public void insertImage(ImageDto imageDto);
    public String getSeq();

    public String getFname(String i_no);

    String getI_no(String p_no);

}
