package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Orders;

import java.util.List;

public interface OrdersMapper {
    int insertOrders(Orders orders);

    int deleteById(String id);

    public Orders getByOrderno(String orederno);

    public List<Orders> getByUserid(String uid);

    int updateOrders(Orders orders);
}
