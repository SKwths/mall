package bjfu.six.mall.mapper;


import bjfu.six.mall.entity.po.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface OrdersMapper {
    @Update("UPDATE action_orders SET status = 3 WHERE order_no = #{order_no}")
    int confirmReceipt(String order_no);

    @Update("UPDATE action_orders SET status = 1 WHERE order_no = #{order_no}")
    int payReceipt(String order_no);

    @Update("UPDATE action_orders SET status = 2 WHERE order_no = #{order_no}")
    int deliverReceipt(String order_no);

    @Update("UPDATE action_orders SET status = 5 WHERE order_no = #{order_no}")
    int cancelOrder(String order_no);

    @Select("select * from action_orders where order_no = #{orderNo}")
    Order getByOrderNo(Long orderNo);
    @Select("select * from action_orders")
    Order[] getAllOrders();

    @Select("select * from action_orders where uid = #{userId} and status=#{status}")
    Order[] getByOrderListByStatusUserId(int status,int userId);

    @Insert("insert into action_orders(order_no,uid,addr_id,amount,type,freight,status) values(#{orderNo},#{userId},#{addrId},#{amount},#{type},#{freight},#{status})")
    @SelectKey(before = false,keyColumn = "id",keyProperty = "id",
            statement = "select last_insert_id()",resultType = Integer.class)
    int insertOrder(Order order);
}
