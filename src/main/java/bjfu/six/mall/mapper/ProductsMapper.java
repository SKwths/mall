package bjfu.six.mall.mapper;


import bjfu.six.mall.entity.po.Products;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface ProductsMapper {

    @Select("select * from action_products where id = #{commodityId}")
    Products selectProductsById(int commodityId);

    @Select("select * from action_products where is_hot = 1 order by updated desc")
    Products[] selectHotProducts();

    @Select("select * from action_products where product_id = #{class_id} limit #{offset}, #{limit}")
    Products[] selectProductsByClassID(int class_id, int offset, int limit);

    @Select("select * from action_products")
    Products[] selectAllProducts1();

    @Select("select * from action_products limit #{offset}, #{limit}")
    Products[] selectAllProducts(int offset, int limit);


    @Select("select * from action_products where name like '%${keyword}%' limit #{offset}, #{limit}")
    Products[] selectProductsByName(String keyword, int offset, int limit);

    @Select("select * from action_products where name like '%${keyword}%' and product_id = ${class_id} limit #{offset}, #{limit}")
    Products[] selectProductsByParamIDAndName(int class_id, String keyword, int offset, int limit);

    @Insert("insert into action_products(name,product_id,icon_url,sub_images,detail,spec_param,price,stock,status,is_hot) values(#{name},#{classId},#{iconUrl},#{subImages},#{detail},#{specParam},#{price},#{stock},0,0)")
    @SelectKey(before = false, keyColumn = "id", keyProperty = "id",
            statement = "select last_insert_id()", resultType = Integer.class)
    int insertProducts(Products commodity);

    @Update("update action_products set name=#{name},product_id=#{class_id},icon_url=#{icon_url},sub_images=#{sub_images},detail=#{detail},spec_param=#{spec_param},price=#{price},stock=#{stock} where id=#{id}")
    int updateProductsById(Integer id, String name, Integer class_id, String icon_url, String sub_images, String detail, String spec_param, Integer price, Integer stock);

    @Update("update action_products set status = #{status},is_hot = #{hot} where id = #{id}")
    int updateProductsStatus(Integer id, Integer status, Integer hot);

    /*获取数量相关*/
    @Select("select count(*) from action_products")
    int selectAllProductsCount();

    @Select("select count(*) from action_products where name like '%${keyword}%'")
    int selectProductsByNameCount(String keyword);

    @Select("select count(*) from action_products where name like '%${keyword}%' and product_id = ${class_id}")
    int selectProductsByClassIDAndNameCount(int class_id, String keyword);

    @Select("select count(*) from action_products where product_id = #{class_id}")
    int selectProductsByClassIDCount(int class_id);

}
