package com.ucamp.fm.service;


import com.ucamp.fm.dto.MemberDto;

public interface MemberService {

	public int idCheck(String m_id);

	public void join(MemberDto member);

	public int loginCheck(String m_id,String m_pw);
}
