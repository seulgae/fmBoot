package com.ucamp.fm.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeDto {
    private int n_no; // ê¸€ ?œì„œ
    private String n_title; // ê¸€ ?œëª©
    private String n_id; // ?‘ì„±???„ì´??
    private String n_content; // ê¸€ ?´ìš©

    private String n_date; // ?‘ì„±?¼ì
    private int n_count; // ì¡°íšŒ??
    private int rownum;
    public NoticeDto(String n_id, String n_title,String n_content){
        this.n_id = n_id;
        this.n_title = n_title;
        this.n_content = n_content;
    }
}
