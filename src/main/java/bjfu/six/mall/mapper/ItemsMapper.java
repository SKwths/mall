package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Order_items;

import java.util.List;

public interface ItemsMapper {
    int insertItems(Order_items items);

    public List<Order_items> getByUserid(String uid);

    public List<Order_items> getByOrderno(String orderno);

    int deleteByOrderno(String orderno);
}
