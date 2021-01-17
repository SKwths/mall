package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Params;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ParamsMapper {
    int insertParams(Params params);

    public List<Params> getByLevel(String level);

    int deleteById(String id);

    int updateParams(Params params);
}
