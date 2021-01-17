package bjfu.six.mall.mapper;


import bjfu.six.mall.entity.po.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AddressMapper {
    @Select("select * from action_address where id = #{addr_id}")
    Address selectAddrById(int addr_id);

    @Select("select * from action_address")
    Address[] selectAllAddr();

    @Select("select * from action_address where del_state = 0")
    Address[] selectDelAddr();

    @Update("update action_address set del_state = 1 where id = #{delid}")
    int delAddr(int delid);

    @Insert({"insert into action_address(user_id, name, mobile, province, city, district, addr, zip) values(#{user_Id}, #{name}, #{mobile},#{province},#{city},#{district},#{addr},#{zip})"})
    @SelectKey(before = false,keyColumn = "id",keyProperty = "id",
            statement = "select last_insert_id()",resultType = Integer.class)
    int insertAddr(Address addr);

    @Select("select * from action_address where user_id = #{user_id} and del_state = 0")
    Address [] selectFindAddr(int user_id);

    @Update("update action_address set `default_addr` = 0 where user_id = #{user_id}")
    int setDefaultAddr1(int id,int user_id);

    @Update("update action_address set `default_addr` = 1 where id = #{id}")
    int setDefaultAddr2(int id,int user_id);
}
