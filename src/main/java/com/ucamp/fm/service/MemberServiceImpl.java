package com.ucamp.fm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ucamp.fm.mapper.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberMapper memberMapper;

	public int idCheck(String m_id) {
		return memberMapper.idCheck(m_id);
	}
	
}
