package com.ucamp.fm.service;

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
    public void teamUpdate(String t_no, String t_name, String t_age, String t_skill, String t_kind, String t_id) {
        teamMapper.teamUpdate(t_no, t_name, t_age, t_skill, t_kind, t_id);
    }

    @Override
    public TeamDto selectTeam(String t_no) {
        return teamMapper.selectTeam(t_no);
    }
}
