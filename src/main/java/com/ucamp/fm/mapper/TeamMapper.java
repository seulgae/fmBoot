package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.MemberDto;
import com.ucamp.fm.dto.TeamDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {
    @Select("select t_name, t_age, t_skill, t_kind from team where m_id = #{m_id}")
    public TeamDto teamCreate(String m_id);

    @Insert("insert into team(t_no, t_name, t_region, t_age, t_skill, t_uniform, t_kind, t_introduce, t_id, t_date) values " +
            "(team_seq.NEXTVAL, #{t_name}, #{t_region}, #{t_age}, #{t_skill}, #{t_uniform}, #{t_kind}, #{t_introduce}, #{t_id}, (SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') FROM DUAL))")
    public void teamInsert(String t_name, String t_region, String t_age, String t_skill, String t_uniform, String t_kind, String t_introduce, String t_id);

    @Select("select * from team order by t_date desc")
    public List<TeamDto> getTeamList();

    @Select("select * from team where t_name like #{keyword}")
    public List<TeamDto> searchTeam(String keyword);

    @Update("update team set t_name = #{t_name}, t_region = #{t_region}, t_age = #{t_age}, t_skill = #{t_skill}, t_uniform = #{t_uniform}, t_kind = #{t_kind}, t_introduce = #{t_introduce} where t_no = #{t_no}")
    public void teamUpdate(TeamDto tDto);

    @Select("select * from team where t_no = #{t_no}")
    public TeamDto selectTeam(String t_no);

    @Select("select * from member where m_id like #{m_id} and m_level = '1'")
    public List<MemberDto> findMember(String m_id);

    @Select("select * from team where t_name like #{teamName}")
    public List<TeamDto> findTeam(String teamName);

    @Delete("delete from team where t_no = #{t_no}")
    public void deleteTeam(String t_no);

    @Update("update team set t_thum = #{t_thum} where t_no = #{t_no}")
    public void addTeamPhoto(TeamDto tDto);

    @Update("update team set m_id=#{str_member} where t_no=#{t_no}")
    public void insertMember(String str_member,String t_no);

    @Select("select m_id from team where t_no=#{t_no}")
    public String getMember(String t_no);
}
