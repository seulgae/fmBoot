package com.ucamp.fm.login.dto;

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
public class MemberDto extends BaseVO {

    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_phone;
    private String m_email;
    private String m_level;
    private String m_cname;
    private String m_zip;
    private String m_addr1;
    private String m_addr2;
    private String m_pname;
    private String m_account;
    private String m_bank;
    private String m_thum;

    public MemberDto(String m_id, String m_thum) {
        this.m_id = m_id;
        this.m_thum = m_thum;
    }
}
