package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
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

	@Select("select * from place")
	List<PlaceDto> getList();

	@Update("update member set m_thum=#{m_thum} where m_id=#{m_id}")
	void addPhoto(MemberDto memberDto);

	@Delete("delete from place where p_no = #{p_no}")
	void place_delete(String p_no);

	@Select("select * from place where p_no=#{p_no}")
	PlaceDto getDto(String p_no);

	void mypage_update_do(PlaceDto placeDto);
}
