package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.po.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ItemsMapper {
    @Select("select * from action_order_items where order_no = #{order_id}")
    Item[] getItemsByOrderId(int order_id);
    @Insert("insert into action_order_items(order_no,goods_id,quantity) values(#{orderId},#{productId},#{quantity})")
    @SelectKey(before = false,keyColumn = "id",keyProperty = "id",
            statement = "select last_insert_id()",resultType = Integer.class)
    int insertItem(Item item);
}
