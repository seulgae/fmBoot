package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.TeamDto;
import com.ucamp.fm.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamMapper teamMapper;

    @Override
    public TeamDto teamCreate(String m_id) {
        return teamMapper.teamCreate(m_id);
    }

    @Override
    public void teamInsert(String t_name, String t_region, String t_age, String t_skill, String t_uniform, String t_kind, String t_introduce, String t_id) {

        teamMapper.teamInsert(t_name, t_region, t_age, t_skill, t_uniform, t_kind, t_introduce, t_id);
    }

    @Override
    public List<TeamDto> getTeamList() {
        return teamMapper.getTeamList();
    }

    @Override
    public List<TeamDto> searchTeam(String keyword) {
        return teamMapper.searchTeam(keyword);
    }

    @Override
    public void teamUpdate(TeamDto tDto) {
        teamMapper.teamUpdate(tDto);
    }

    @Override
    public TeamDto selectTeam(String t_no) {
        return teamMapper.selectTeam(t_no);
    }

    @Override
    public List<MemberDto> findMember(String m_id) {
        return teamMapper.findMember(m_id);
    }

    @Override
    public List<TeamDto> findTeam(String teamName) {
        return teamMapper.findTeam(teamName);
    }

    @Override
    public void deleteTeam(String t_no) {
        teamMapper.deleteTeam(t_no);
    }

    @Override
    public void addTeamPhoto(TeamDto tDto) {
        teamMapper.addTeamPhoto(tDto);
    }

    @Override
    public void insertMember(String str_member, String t_no) {
        teamMapper.insertMember(str_member,t_no);
    }

    @Override
    public String getMember(String t_no) {
        return teamMapper.getMember(t_no);
    }


}
