package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDto {

    private String tb_no; // 커뮤니티 게시판 글 번호
    private String tb_tbno; // 커뮤니티 게시판 고유번호
    private String tb_id; // 작성자 아이디
    private String tb_title; // 글 제목
    private String tb_content; // 글 내용
    private String tb_date; // 작성일자
    private String tb_thum; // 섬네일 이름
    private String tb_state; // 게시물 상태

    public BlogDto(String tb_id,String tb_title, String tb_content, String tb_thum) {
        this.tb_id = tb_id;
        this.tb_title = tb_title;
        this.tb_content = tb_content;
        this.tb_thum = tb_thum;
    }

}
