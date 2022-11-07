package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmentDto {
    private String c_no;
    private String m_id;
    private String c_title;
    private String c_content;
    private String c_date;
    private String c_dep;
}
