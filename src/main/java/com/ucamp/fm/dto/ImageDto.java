package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private String i_no;
    private String i_sort;
    private String i_board;
    private String i_fname;
    private String i_fsize;
}
