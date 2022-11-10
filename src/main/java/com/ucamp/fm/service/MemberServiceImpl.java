package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
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

	public void join(MemberDto member){
		memberMapper.join(member);
	}

	public int loginCheck(String m_id, String m_pw){
		return memberMapper.loginCheck(m_id,m_pw);
	}
}
