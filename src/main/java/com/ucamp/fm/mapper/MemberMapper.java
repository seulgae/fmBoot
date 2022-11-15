package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.JoinDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {


	@Select("select count(*) from member where m_id = #{m_id}")
	public int idCheck(String m_id);

	public void join(MemberDto member);

	@Select("select count(*) from member where m_id = #{m_id} and m_pw = #{m_pw}")
	public int loginCheck(String m_id,String m_pw);


	public void mypage_request(PlaceDto placeDto);

	@Select("select m_level,m_email,m_name,m_thum from member where m_id=#{m_id}")
	MemberDto getMember(String m_id);

	@Select("select * from place where p_manager=#{m_id}")
	List<PlaceDto> getList(String m_id);

	@Update("update member set m_thum=#{m_thum} where m_id=#{m_id}")
	void addPhoto(MemberDto memberDto);

	@Delete("delete from place where p_no = #{p_no}")
	void place_delete(String p_no);

	@Select("select * from place where p_no=#{p_no}")
	PlaceDto getDto(String p_no);

	void mypage_update_do(PlaceDto placeDto);

	@Select("select r_no,r_m_id,r_p_no,r_time,r_date,r_wdate,p_pname from reservation re join place p on re.r_p_no = p.p_no where r_m_id=#{m_id}")
	List<JoinDto> getList1(String m_id);

	void addThum(PlaceDto placeDto);

	@Select("select * from member where m_id=#{m_id}")
	MemberDto getInformation_update(String m_id);

	void Information_update_do(MemberDto memberDto);

	@Select("select r_no,r_m_id,r_p_no,r_time,r_date,r_wdate,p_pname,m_phone from reservation re join place p on re.r_p_no = p.p_no join member m on re.r_m_id = m.m_id where p_manager=#{m_id}")
	List<JoinDto> getList2(String m_id);

	@Select("select m_pw from member where m_id = #{m_id}")
	String getPw(String m_id);
}
