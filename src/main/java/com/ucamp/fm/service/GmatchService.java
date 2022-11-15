package com.ucamp.fm.service;

import com.ucamp.fm.dto.GmatchDto;

import java.util.List;

public interface GmatchService {
    public void gmatchInsert(GmatchDto gDto);

    public String selectAll(String g_no);

    public String selectWin(String g_no);

    public String selectDraw(String g_no);

    public String selectLose(String g_no);

    public String selectGf(String g_no);

    public String selectGa(String g_no);
}
