package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.po.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface CartsMapper {
    @Select("select quantity from action_carts where user_id = #{userId}")
    ArrayList<Integer> selectQuantityByuserId(int userId);

    @Update("update action_carts set quantity = #{count} where user_id = #{userId} and product_id = #{productId}")
    int updateCommodityQuantity(int userId, int productId,int count);

    @Select("select * from action_carts where user_id = #{userId} and product_id = #{productId}")
    Cart selectCartByuserIdAndproductId(int userId, Integer productId);

    @Select("select * from action_carts where user_id = #{userId} ")
    Cart[] selectCartByUserId(int userId);

    @Delete("delete from action_carts where user_id = #{userId}")
    void clearCart(int userId);

    @Delete("delete from action_carts where user_id = #{userId} and product_id = #{productId}")
    void deleteCommodity(int userId,int productId);

    @Insert("insert into action_carts(user_id, product_id, quantity) " +
            "values(#{userId}, #{product_id}, #{quantity}) ")
    int insertCart(Cart cart);
}
