package com.office.house.like;

import lombok.Data;

@Data
public class LikeDto {
    private int l_no;
    private int l_type;
    private int l_content_no;
    private String u_id;
    private String l_reg_date;
    private String l_mod_date;
}
