package com.ucamp.fm.service;

import com.ucamp.fm.dto.CmentDto;
import com.ucamp.fm.mapper.CmtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmtServiceImpl implements CmtService{

    @Autowired
    CmtMapper cmtMapper;

    @Override
    public List<CmentDto> cmtlistdec(CmentDto cmentDto) {
        return cmtMapper.cmtlistdec(cmentDto);
    }

    @Override
    public List<CmentDto> cmtlist(String c_tbset) {
        return cmtMapper.cmtlist(c_tbset);
    }

    @Override
    public List<CmentDto> tlist(String c_tbset) { return cmtMapper.tlist(c_tbset); }

    @Override
    public void cmtdec(int c_no) {
        cmtMapper.cmtdec(c_no);
    }

    @Override
    public void cmtinsert(String c_no ,String c_c_id, String c_content) {
        cmtMapper.cmtinsert(c_no, c_c_id, c_content);
    }

    @Override
    public void cmtinsert2(String c_tbset, String c_c_id, String c_content, String c_tbno) {
        cmtMapper.cmtinsert2(c_tbset, c_c_id, c_content, c_tbno);
    }

    @Override
    public void cmtdelete(String c_no) {
        cmtMapper.cmtdelete(c_no);
    }
}
