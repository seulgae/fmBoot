package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.PlaceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaymentMapper {
    @Select("select m_phone from member where m_id=#{m_id}")
    public String getM_Phone(String m_id);

    @Select("select * from place")
    List<PlaceDto> selectAll();
}
