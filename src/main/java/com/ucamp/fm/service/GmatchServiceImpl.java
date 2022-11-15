package com.ucamp.fm.service;

import com.ucamp.fm.dto.GmatchDto;
import com.ucamp.fm.mapper.GmatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GmatchServiceImpl implements GmatchService{
    @Autowired
    GmatchMapper gmatchMapper;

    @Override
    public void gmatchInsert(GmatchDto gDto) {
        gmatchMapper.gmatchInsert(gDto);
    }

    @Override
    public String selectAll(String g_no) {
        return gmatchMapper.selectAll(g_no);
    }

    @Override
    public String selectWin(String g_no) {
        return gmatchMapper.selectWin(g_no);
    }

    @Override
    public String selectDraw(String g_no) {
        return gmatchMapper.selectDraw(g_no);
    }

    @Override
    public String selectLose(String g_no) {
        return gmatchMapper.selectLose(g_no);
    }

    @Override
    public String selectGf(String g_no) {
        return gmatchMapper.selectGf(g_no);
    }

    @Override
    public String selectGa(String g_no) {
        return gmatchMapper.selectGa(g_no);
    }

}
