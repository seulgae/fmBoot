package com.ucamp.fm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	@Insert("insert into test values (#{m_id})")
	void test(String m_id);

}
