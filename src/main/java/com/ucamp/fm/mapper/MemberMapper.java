package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

	@Select("select count(*) from member where m_id = #{m_id}")
	public int idCheck(String m_id);

	public void join(MemberDto member);

	@Select("select count(*) from member where m_id = #{m_id} and m_pw = #{m_pw}")
	public int loginCheck(String m_id,String m_pw);

}
