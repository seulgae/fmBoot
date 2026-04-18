package com.ucamp.fm.mypage.dto;

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
public class PlaceDto extends BaseVO {
    private String p_no;
    private String p_code;
    private String p_manager;
    private String p_userid;
    private String p_pname;
    private String p_place;
    private String p_explain;
    private String p_book;
    private String p_price;
    private String i_no;
    private String p_max;
    private String p_area;
    private String p_post;
    private String p_plus;
    private String p_op1;
    private String p_op2;
    private String p_op3;
    private String p_op4;
    private String p_op5;
    private String p_op6;
    private String p_date;
    private String mainImg;
    private int reservedCount;
}
