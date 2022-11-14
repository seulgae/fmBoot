package com.ucamp.fm.service;


import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;

import java.util.List;

public interface MemberService {

	public int idCheck(String m_id);

	public void join(MemberDto member);

	public int loginCheck(String m_id,String m_pw);

	MemberDto getMember(String m_id);


	public void mypage_request(PlaceDto placeDto);

	List<PlaceDto> getList();
}
