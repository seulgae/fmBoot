package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {

    private String tb_no;
    private String tb_title;
    private String tb_content;
    private String tb_date;
    private String tb_thum;
    private int tb_filesize;
    private String tb_state;
}
