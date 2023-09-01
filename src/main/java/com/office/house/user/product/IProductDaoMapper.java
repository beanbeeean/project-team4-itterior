package com.office.house.user.product;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDaoMapper {
    //List<ProductDto> selectProductsByType(String type);
    List<ProductDto> selectProducts(String[] category, String sort, String filter, String keyword);

    void insertProducts(ProductDto dto);
}
