package com.office.house.comment;

import lombok.Data;

@Data
public class CommentDto {
    private int c_no;
    private String u_id;
    private int b_no;
    private String c_comment;
    private String c_target_u_id;
    private int c_target_c_no;
    private int c_state;
    private String c_reg_date;
    private String c_mod_date;

    private int u_no;
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
