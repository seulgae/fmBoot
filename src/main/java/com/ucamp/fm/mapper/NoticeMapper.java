package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.NoticeDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface NoticeMapper {

    @Select("SELECT * FROM notice Order By n_id Desc")
    List<NoticeDto> noticelist();
    @Select("select * from notice where n_no = #{n_no}")
    NoticeDto noticeselect(int n_no);

    @Update("update notice set n_id = #{n_id}, n_title = #{n_title}, n_content=#{n_content} WHERE n_no = #{n_no}")
    void noticeupdate(HashMap<String, Object> map);

    void noticeinsert(HashMap<String, Object> map);

    @Delete("delete from notice where n_no = #{n_no}")
    void noticedelete(int n_no);

    @Update( "UPDATE notice SET n_count = n_count+0.5 WHERE n_no=#{n_no}")
    void noticecountup(int n_no);

}
