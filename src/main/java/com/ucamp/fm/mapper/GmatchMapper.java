package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.GmatchDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GmatchMapper {

    @Insert("insert into gmatch(g_mno, g_no, g_vsno, g_game, g_gf, g_ga, g_gamedate, g_wdate) values (mno_seq.NEXTVAL, #{g_no}, #{g_vsno}, #{g_game}, #{g_gf}, #{g_ga}, #{g_gamedate}, sysdate)")
    public void gmatchInsert(GmatchDto gDto);

    @Select("select count(*) from gmatch where g_no = #{g_no}")
    public String selectAll(String g_no);

    @Select("select count(*) from gmatch where g_no = #{g_no} and g_game = 1")
    public String selectWin(String g_no);

    @Select("select count(*) from gmatch where g_no = #{g_no} and g_game = 2")
    public String selectDraw(String g_no);

    @Select("select count(*) from gmatch where g_no = #{g_no} and g_game = 3")
    public String selectLose(String g_no);

    @Select("select sum(gmatch.g_gf) from gmatch where g_no = #{g_no}")
    public String selectGf(String g_no);

    @Select("select sum(gmatch.g_ga) from gmatch where g_no = #{g_no}")
    public String selectGa(String g_no);
}
