package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.CmentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CmtMapper {

    // 팀 블로그 댓글만 조회함.
    List<CmentDto> cmtlist();

    void cmtinsert(String c_no, String c_c_id, String c_content);

    @Select("select * from cment where c_c_id=#{c_c_id}")
    CmentDto cmtone();

}
