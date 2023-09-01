package com.office.house.user.product;

import com.office.house.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDaoMapper {
    //List<ProductDto> selectProductsByType(String type);
    List<ProductDto> selectProducts(String[] category, String sort, String filter, String keyword, int skip, int amount);

    void insertProducts(ProductDto dto);

    int selectProductsCnt(String[] category, String sort, String filter, String keyword, int skip, int amount);
}
