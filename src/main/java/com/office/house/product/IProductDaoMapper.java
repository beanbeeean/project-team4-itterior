package com.office.house.product;

import com.office.house.like.LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IProductDaoMapper {
    //List<ProductDto> selectProductsByType(String type);
    List<ProductDto> selectProducts(String[] category, String sort, String filter, String keyword, int skip, int amount);

    void insertProducts(ProductDto dto);

    int selectProductsCnt(String[] category, String sort, String filter, String keyword, int skip, int amount);

    int isExistProduct(String p_link);

    void updateProducts(ProductDto dto);

    int insertLikeCount(String type, String no, String u_id);

    int deleteLikeCount(String type, String no, String u_id);

    int selectLikeCount(String type, String no);

    int updateLikeCountForProduct(String no, int likeCnt);

    List<LikeDto> selectLikedProduct(List<Integer> likeList);

    ProductDto selectProductByNo(String no);

    int updateHitCount(String no);

    List<ProductDto> selectLikeProducts(Map<String, Object> map);

    int selectLikeProductsCnt(int skip, int amount, String u_id);

    List<ProductDto> selectMainProducts();
}
