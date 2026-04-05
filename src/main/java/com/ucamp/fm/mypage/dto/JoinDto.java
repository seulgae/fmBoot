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
public class JoinDto extends BaseVO {
    private int r_no;
    private String r_m_id;
    private String r_p_no;
    private String r_time;
    private String r_date;
    private String r_wdate;
    private String p_pname;
    private String m_phone;
}
