package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.NoticeDto;
import com.ucamp.fm.mapper.NoticeMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString


@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public List<NoticeDto> noticelist() { return noticeMapper.noticelist(); }

    @Override
    public NoticeDto noticeselect(int n_no) { return noticeMapper.noticeselect(n_no); }

    @Override
    public void noticeupdate(HashMap<String, Object> map) {
        noticeMapper.noticeupdate(map);
    }

    @Override
    public void noticeinsert(HashMap<String, Object> map) {
        noticeMapper.noticeinsert(map);
    }

    @Override
    public void noticedelete(int n_no) { noticeMapper.noticedelete(n_no); }
    //
    @Override
    public void countup(int n_no) { noticeMapper.noticecountup(n_no);

    }


}
