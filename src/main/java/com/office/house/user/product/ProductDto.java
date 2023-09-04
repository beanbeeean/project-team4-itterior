package com.office.house.user.product;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ProductDto {
    private int p_no;
    private String p_brand;
    private String p_name;
    private String p_sales_price;
    private String p_img;
    private String p_link;
    private int p_category;
    private int p_like;
    private int p_hit;
    private int p_state;
    private String p_reg_date;
    private String p_mod_date;
}
