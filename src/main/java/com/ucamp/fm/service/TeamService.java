package com.ucamp.fm.service;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.TeamDto;

import java.util.ArrayList;
import java.util.List;

public interface TeamService {
    TeamDto teamCreate(String m_id);

    void teamInsert(String t_name, String t_region, String t_age, String t_skill, String t_uniform, String t_kind, String t_introduce, String t_id);

    List<TeamDto> getTeamList();

    List<TeamDto> searchTeam(String keyword);

    void teamUpdate(TeamDto tDto);

    TeamDto selectTeam(String t_no);

    List<MemberDto> findMember(String m_id);

    List<TeamDto> findTeam(String teamName);

    void deleteTeam(String t_no);

    void addTeamPhoto(TeamDto tDto);

    void insertMember(String str_member,String t_no);

    String getMember(String t_no);

    ArrayList<String> getTno();

    int userCheck(String m_id, String t_no);

}
