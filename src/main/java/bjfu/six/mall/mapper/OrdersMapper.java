package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersMapper {
    int insertOrders(Orders orders);

    int deleteById(String id);

    public Orders getByOrderno(String orederno);

    public List<Orders> getByUserid(String uid);

    int updateOrders(Orders orders);
}
