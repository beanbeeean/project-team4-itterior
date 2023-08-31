package com.office.house.user;

import lombok.Data;

@Data
public class UserDto {
	
	private int u_no;
    private String u_id;
    private String u_pw;
    private String u_name;
    private String u_mail;
    private String u_phone;
    private String u_img;
    private int u_zipcode;
    private String u_main_addr;
    private String u_detail_addr;
    private int u_state;
    private String u_reg_date;
    private String u_mod_date;
    
}
