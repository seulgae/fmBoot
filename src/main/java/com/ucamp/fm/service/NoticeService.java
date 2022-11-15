package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.NoticeDto;

import java.util.HashMap;
import java.util.List;


public interface NoticeService {
    List<NoticeDto> noticelist();

    NoticeDto noticeselect (int n_no);


    void noticeinsert(HashMap<String, Object> map);

    void noticedelete(int n_no);

    void countup(int n_no);

}
