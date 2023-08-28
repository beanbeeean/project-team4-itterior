package com.office.house.member;

import lombok.Data;

@Data
public class MemberDto {
	
	private int m_no;
    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_mail;
    private String m_phone;
    private String m_img;
    private int m_zipcode;
    private String m_main_addr;
    private String m_detail_addr;
    private int m_use;
    private String m_reg_date;
    private String m_mod_date;
    
}
