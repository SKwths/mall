package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Carts;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CartsMapper {
    int insertCarts(Carts carts);
    Carts getByUserid(String user_id);
    int updateCarts(Carts carts);
    int deleteById(String id);
}
