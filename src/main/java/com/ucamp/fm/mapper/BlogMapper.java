package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.BlogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {

    List<BlogDto> bloglist(HashMap<String, Object> map);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    @Select("select * from teamblog where tb_no = #{tb_no}")
    BlogDto blogone(String td_no);

    void bloginsert(BlogDto blogDto);

    void blogdelete(String tb_no);

    @Update("UPDATE teamblog SET " +
            "tb_id = #{tb_id}, " +
            "tb_title = #{tb_title}, " +
            "tb_content=#{tb_content}, " +
            "tb_thum=#{tb_thum} WHERE tb_no = #{tb_no}")
    void blogupdate(BlogDto blogDto);

}
