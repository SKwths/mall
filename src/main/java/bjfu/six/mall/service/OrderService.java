package bjfu.six.mall.service;

import bjfu.six.mall.entity.po.Order;
import bjfu.six.mall.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    public int confirmReceipt(String orderNo){
        return ordersMapper.confirmReceipt(orderNo);
    }

    public int payReceipt(String orderNo){
        return ordersMapper.payReceipt(orderNo);
    }

    public int deliverReceipt(String orderNo){
        return ordersMapper.deliverReceipt(orderNo);
    }

    public int cancelOrder(String orderNo){
        return ordersMapper.cancelOrder(orderNo);
    }
    public Order getOrderDetail(Long orderNo){
        return ordersMapper.getByOrderNo(orderNo);

    }

    public Order[] getOrderList(int status,int userid){
        return ordersMapper.getByOrderListByStatusUserId(status,userid);

    }

    public Order[] getAllOrderList(){
        return ordersMapper.getAllOrders();
    }
    public int addOrder(Order order){
        return ordersMapper.insertOrder(order);
    }
}
