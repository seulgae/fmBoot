package com.ucamp.fm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ucamp.fm.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	void test(MemberDto memberDto);

}
