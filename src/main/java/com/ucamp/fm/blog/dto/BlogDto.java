package com.ucamp.fm.blog.dto;

import com.ucamp.fm.common.dto.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDto extends BaseVO {

    private String tb_no;
    private String tb_tbno;
    private String tb_id;
    private String tb_title;
    private String tb_content;
    private String tb_date;
    private String tb_thum;
    private String tb_state;

    public BlogDto(String tb_id, String tb_title, String tb_content, String tb_thum) {
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
