package com.ucamp.fm.notice.dto;

import com.ucamp.fm.common.dto.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeDto extends BaseVO {
    private int n_no;
    private String n_title;
    private String n_id;
    private String n_content;
    private String n_date;
    private int n_count;

    public NoticeDto(String n_id, String n_title, String n_content) {
        this.n_id = n_id;
        this.n_title = n_title;
        this.n_content = n_content;
    }
}
