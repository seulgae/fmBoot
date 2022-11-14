package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CmentDto {
    private String c_no;
    private String c_c_id;
    private String c_m_thum;

    private String c_tbno;
    private String c_tbset;
    private String c_content;
    private String c_date;
    public CmentDto(String c_tbset,String c_c_id,String c_content) {
        this.c_tbset = c_tbset;
        this.c_c_id = c_c_id;
        this.c_content = c_content;
    }
}
