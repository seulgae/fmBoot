package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PayDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface PaymentMapper {
    @Select("select * from member where m_id=#{m_id}")
    public MemberDto getMember(String m_id);

    @Select("select * from place")
    List<PlaceDto> selectAll();

    @Select("select * from place where p_no=#{p_no}")
    PlaceDto selectPlace(String p_no);

    @Select("select * from member where m_id=#{m_id}")
    MemberDto selectMember(String m_id);

    void insertReservation(ReservationDto reservationDto);

    @Select("select * from reservation where r_p_no = #{p_no}")
    ReservationDto reservationAll(String p_no);

    @Select("select r_time from reservation where r_p_no = #{p_no} and r_date = #{r_date}")
    List<String> reserveCheck(String r_date,String p_no);

    void InsertPay(PayDto paydto);

    @Select("select * from place where p_pname like #{keyword}")
    List<PlaceDto> searchPlace(String keyword);

    List<PlaceDto> selectPageing(HashMap<String, Object> map);

}
