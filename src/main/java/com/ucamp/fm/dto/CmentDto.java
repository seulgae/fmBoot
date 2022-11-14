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
    private String c_tbno;
    private String c_content;
    private String c_date;

}
