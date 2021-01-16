package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Params;

import java.util.List;

public interface ParamsMapper {
    int insertParams(Params params);

    public List<Params> getByLevel(String level);

    int deleteById(String id);

    int updateParams(Params params);
}
