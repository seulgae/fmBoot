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
    public List<CmentDto> cmtlist(String c_tbset) {
        return cmtMapper.cmtlist(c_tbset);
    }

    @Override
    public void cmtinsert(String c_no ,String c_c_id, String c_content) {
        cmtMapper.cmtinsert(c_no, c_c_id, c_content);
    }

    @Override
    public void cmtdelete(String c_no) {
        cmtMapper.cmtdelete(c_no);
    }
}
