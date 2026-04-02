package com.ucamp.fm.notice.service;

import com.ucamp.fm.blog.dto.BlogDto;
import com.ucamp.fm.login.dto.MemberDto;
import com.ucamp.fm.notice.dto.NoticeDto;

import java.util.HashMap;
import java.util.List;


public interface NoticeService {
    List<NoticeDto> noticelist();

    NoticeDto noticeselect (int n_no);


    void noticeupdate(HashMap<String, Object> map);

    void noticeinsert(HashMap<String, Object> map);

    void noticedelete(int n_no);

    void countup(int n_no);

}
