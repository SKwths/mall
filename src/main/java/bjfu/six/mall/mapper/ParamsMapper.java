package bjfu.six.mall.mapper;


import bjfu.six.mall.entity.po.Params;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ParamsMapper {
    @Insert("insert into action_params(parent_id, name) values(#{parentId}, #{name})")
    int insertParams(Params params);

    @Select("select * from action_params where id = #{id}")
    Params selectParamsById(int id);

    @Select("select * from action_params")
    Params[] selectAllParams();

    @Update("update action_params set name = #{name} where id = #{id}")
    int updateParam(String name,int id);

    @Select("select COUNT(*) from action_products where product_id = #{id}")
    int cmdt(int id);

    @Delete("delete from action_params where id = #{id}")
    void deleteById(int id);

    @Select("select * from action_params where parent_id=0")
    Params[] selectAllParams2();
}
