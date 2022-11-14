package com.ucamp.fm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayDto {
    private String p_no;
    private String pay_code;
    private String pay_id;
    private String pay_name;
    private String pay_phone;
    private String pay_price;
    private String pay_date;
    private String pay_reservation;
}
