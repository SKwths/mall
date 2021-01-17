package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Products;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductsMapper {
    int insertProducts(Products products);
    int deleteById(String id);
    int updateProducts(Products products);
    public List<Products> getByProductid(String product_id);
}
