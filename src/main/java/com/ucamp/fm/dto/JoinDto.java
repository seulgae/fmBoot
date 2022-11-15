package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinDto {
    private int r_no;
    private String r_m_id;
    private String r_p_no;
    private String r_time;
    private String r_date;
    private String r_wdate;
    private String p_pname;
}
