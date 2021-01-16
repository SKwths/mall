package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Users;

import java.util.List;

public interface UsersMapper {
    int insertUser(Users user);
    int deleteById(String id);
    public List<Users> selectByAccount(String account);

}
