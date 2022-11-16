package com.ucamp.fm.service;

import com.ucamp.fm.dto.ImageDto;
import com.ucamp.fm.dto.PlaceDto;

public interface PlaceService {
    public void insertImage(ImageDto imageDto);
    public String getSeq();

    public String getFname(String i_no);

    String getI_no(String p_no);

}
