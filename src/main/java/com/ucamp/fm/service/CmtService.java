package com.ucamp.fm.service;

import com.ucamp.fm.dto.CmentDto;

import java.util.List;

public interface CmtService {

    List<CmentDto> cmtlist(String c_tbset);

    void cmtinsert(String c_no, String c_c_id, String c_content);

    void cmtdelete(String c_no);
}