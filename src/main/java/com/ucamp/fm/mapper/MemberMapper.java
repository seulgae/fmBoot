package com.ucamp.fm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

	@Select("select count(*) from member where m_id = #{m_id}")
	public int idCheck(String m_id);

}
