package com.ucamp.fm.service;

import com.ucamp.fm.dto.CmentDto;

import java.util.List;

public interface CmtService {

    List<CmentDto> cmtlist();

    void cmtinsert(String c_no, String c_c_id, String c_content);
}
