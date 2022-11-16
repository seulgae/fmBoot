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
    private String t_region;
    private String t_state;
    private String t_age;
    private String t_skill;
    private String t_uniform;
    private String t_kind;
    private String t_introduce;
    private String m_id;
    private String t_thum;

    public TeamDto(String t_no, String t_thum) {
        this.t_no = t_no;
        this.t_thum = t_thum;
    }
}
