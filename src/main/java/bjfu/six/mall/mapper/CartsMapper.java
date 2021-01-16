package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Carts;

public interface CartsMapper {
    int insertCarts(Carts carts);
    Carts getByUserid(String user_id);
    int updateCarts(Carts carts);
    int deleteById(String id);
}
