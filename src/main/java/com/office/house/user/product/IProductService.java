package com.office.house.user.product;

import java.util.Map;

public interface IProductService {
    //public Map<String, Object> getProducts(String type);
    public Map<String, Object> getProducts(String[] category, String sort, String filter);

}
