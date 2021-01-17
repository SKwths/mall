package bjfu.six.mall.mapper;


import bjfu.six.mall.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddressMapper {
    int deleteById(String id);

    int insertAddress(Address address);

    Address getById(String id);

    int updateAddress(Address address);

    public List<Address> getByUserid(String user_id);
}
