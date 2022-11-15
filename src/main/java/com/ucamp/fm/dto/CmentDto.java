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
    private int c_dec;

    public CmentDto(String c_tbset,String c_c_id,String c_content) {
        this.c_tbset = c_tbset;
        this.c_c_id = c_c_id;
        this.c_content = c_content;
    }

    public CmentDto(String c_no, int c_dec) {
        this.c_no = c_no;
        this.c_dec = c_dec;
    }

}
