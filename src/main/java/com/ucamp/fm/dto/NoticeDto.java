package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeDto {
    private int n_no; // 글 순서
    private String n_title; // 글 제목
    private String n_id; // 작성자 아이디
    private String n_content; // 글 내용

    private String n_date; // 작성일자
    private int n_count; // 조회수
    private int rownum;
    public NoticeDto(String n_id, String n_title,String n_content){
        this.n_id = n_id;
        this.n_title = n_title;
        this.n_content = n_content;
    }
}
