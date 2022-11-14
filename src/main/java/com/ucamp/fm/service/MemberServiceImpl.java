package com.ucamp.fm.service;

import com.ucamp.fm.dto.JoinDto;
import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.PlaceDto;
import com.ucamp.fm.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucamp.fm.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public MemberDto getMember(String m_id) {
		return memberMapper.getMember(m_id);
	}

	@Override
	public void mypage_request(PlaceDto placeDto) {
		memberMapper.mypage_request(placeDto);
	}

	@Override
	public List<PlaceDto> getList() {
		return memberMapper.getList();
	}

	@Override
	public void addPhoto(MemberDto memberDto) {
		memberMapper.addPhoto(memberDto);
	}

	@Override
	public void place_delete(String p_no) {
		memberMapper.place_delete(p_no);
	}

	@Override
	public PlaceDto getDto(String p_no) {
		return memberMapper.getDto(p_no);
	}

	@Override
	public void mypage_update_do(PlaceDto placeDto) {
		memberMapper.mypage_update_do(placeDto);
	}

	@Override
	public List<JoinDto> getList1(String m_id) {
		return memberMapper.getList1(m_id);
	}

//	@Override
//	public PlaceDto getP_name() {
//		return memberMapper.getP_neme();
//	}


}
