package com.ucamp.fm.service;


import com.ucamp.fm.dto.JoinDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;

import java.util.List;

public interface MemberService {

	public int idCheck(String m_id);

	public void join(MemberDto member);

	public int loginCheck(String m_id,String m_pw);

	MemberDto getMember(String m_id);


	public void mypage_request(PlaceDto placeDto);

	List<PlaceDto> getList(String m_id);

    void addPhoto(MemberDto memberDto);

	void place_delete(String p_no);


	PlaceDto getDto(String p_no);

	public void mypage_update_do(PlaceDto placeDto);

	List<JoinDto> getList1(String m_id);

    void addThum(PlaceDto placeDto);

	MemberDto Information_update(String m_id);

	public void Information_update_do(MemberDto memberDto);

	List<JoinDto> getList2(String m_id);

	String getPw(String m_id);
}
