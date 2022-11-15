package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.CmentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CmtMapper {

    // 팀 블로그 댓글만 조회함.
    List<CmentDto> cmtlist(String c_no);

    @Select("select * from cment where c_dec > 0")
    List<CmentDto> cmtlistdec(CmentDto cmentDto);

    @Update( "update cment SET C_dec = C_dec+1 WHERE c_no=#{c_no}")
    void cmtdec(int c_no);

    void cmtinsert(String c_no, String c_c_id, String c_content);

    void cmtinsert2(String c_tbset, String c_c_id, String c_content, String c_tbno);

    @Select("select * from cment where c_c_id=#{c_c_id}")
    CmentDto cmtone();

    void cmtdelete(String c_no);
}
