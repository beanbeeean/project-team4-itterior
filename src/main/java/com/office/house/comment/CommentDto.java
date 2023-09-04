package com.office.house.comment;

import lombok.Data;

@Data
public class CommentDto {
    private int c_no;
    private String u_id;
    private String u_img;
    private int b_no;
    private String c_comment;
    private String c_target_u_id;
    private int c_target_c_no;
    private int c_state;
    private String c_reg_date;
    private String c_mod_date;
}
