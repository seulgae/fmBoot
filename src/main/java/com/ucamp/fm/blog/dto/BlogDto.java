package com.ucamp.fm.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDto {

    private String tb_no; // ì»¤ë??ˆí‹° ê²Œì‹œ??ê¸€ ë²ˆí˜¸
    private String tb_tbno; // ì»¤ë??ˆí‹° ê²Œì‹œ??ê³ ìœ ë²ˆí˜¸
    private String tb_id; // ?‘ì„±???„ì´??
    private String tb_title; // ê¸€ ?œëª©
    private String tb_content; // ê¸€ ?´ìš©
    private String tb_date; // ?‘ì„±?¼ì
    private String tb_thum; // ?¬ë„¤???´ë¦„
    private String tb_state; // ê²Œì‹œë¬??íƒœ
    private int rownum;
    // ê²€?‰ê³¼ ?˜ì´ì§?ê¸°ëŠ¥???˜ê²¨ë°›ê¸° ?„í•œ ë³€??? ì–¸


    public BlogDto(String tb_id,String tb_title, String tb_content, String tb_thum) {
        this.tb_id = tb_id;
        this.tb_title = tb_title;
        this.tb_content = tb_content;
        this.tb_thum = tb_thum;
    }

    public BlogDto(String tb_no, String tb_id, String tb_title, String tb_content, String tb_thum) {
        this.tb_no = tb_no;
        this.tb_id = tb_id;
        this.tb_title = tb_title;
        this.tb_content = tb_content;
        this.tb_thum = tb_thum;
    }
}
