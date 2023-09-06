package com.office.house.product;

import java.util.Map;

public interface IProductService {

    public Map<String, Object> getProducts(String[] category, String sort, String filter, String keyword, int pageNum, int amount);


    public Map<String, Object> updateProductHit(String no);

    public int productLikeUpdate(Map<String, String> msgMap);

    public int productLikeDelete(Map<String, String> msgMap);
}
