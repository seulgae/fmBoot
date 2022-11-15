package com.ucamp.fm.service;

import com.ucamp.fm.dto.CmentDto;

import java.util.List;

public interface CmtService {

    List<CmentDto> cmtlistdec(CmentDto cmentDto);

    List<CmentDto> cmtlist(String c_tbset);

    void cmtdec(int c_no);

    void cmtinsert(String c_no, String c_c_id, String c_content);

    void cmtinsert2(String c_tbset, String c_c_id, String c_content, String c_tbno);

    void cmtdelete(String c_no);
}
