package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.BlogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper {

    List<BlogDto> bloglist(BlogDto blogDto);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    @Select("select * from teamblog where tb_no = #{tb_no}")
    BlogDto blogone(String td_no);

    void bloginsert(BlogDto blogDto);

    void blogdelete(String tb_no);

}
