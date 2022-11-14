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

    public ImageDto(String i_sort,String i_board, String i_fname, String i_fsize){
        this.i_sort = i_sort;
        this.i_board = i_board;
        this.i_fname = i_fname;
        this.i_fsize = i_fsize;
    }
}
