package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class GmatchDto {
    String g_vsno;
    String g_gamedate;
    String g_game;
    String g_gf;
    String g_ga;
    String g_no;
    String g_mno;
    String g_wdate;
}
