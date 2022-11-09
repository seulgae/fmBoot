package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.security.DenyAll;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class TeamDto {
    private String t_no;
    private String t_date;
    private String t_id;
    private String t_name;
    private String t_gamedate;
    private String t_match;
    private String t_state;
    private String t_age;
    private String t_skill;
    private String t_kind;
    private String m_id;
}
