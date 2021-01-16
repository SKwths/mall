package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Products;

import java.util.List;

public interface ProductsMapper {
    int insertProducts(Products products);
    int deleteById(String id);
    int updateProducts(Products products);
    public List<Products> getByProductid(String product_id);
}
