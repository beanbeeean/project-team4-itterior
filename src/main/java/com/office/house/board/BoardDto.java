package com.office.house.board;

import lombok.Data;

@Data
public class BoardDto {
    private int b_no;
    private String u_id;
    private String u_img;
    private String b_title;
    private String b_content;
    private String b_thumbnail;
    private int b_hit;
    private String b_reg_date;
    private String b_mod_date;

}
