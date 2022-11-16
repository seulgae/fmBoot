package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.TeamDto;

import java.util.List;

public interface TeamService {
    public TeamDto teamCreate(String m_id);

    public void teamInsert(String t_name, String t_region, String t_age, String t_skill, String t_uniform, String t_kind, String t_introduce, String t_id);

    public List<TeamDto> getTeamList();

    public List<TeamDto> searchTeam(String keyword);

    public void teamUpdate(TeamDto tDto);

    public TeamDto selectTeam(String t_no);

    public List<MemberDto> findMember(String m_id);

    public List<TeamDto> findTeam(String teamName);

    public void deleteTeam(String t_no);

    public void addTeamPhoto(TeamDto tDto);

    void insertMember(String str_member,String t_no);

    String getMember(String t_no);
}
