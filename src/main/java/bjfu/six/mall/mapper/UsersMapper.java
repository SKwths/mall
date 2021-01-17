package bjfu.six.mall.mapper;

import bjfu.six.mall.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UsersMapper {
    Integer insertUser(Users user);
    Integer deleteById(String id);
    public List<Users> selectByAccount(String account);
    public List<Users>  selectById(String id);
    Integer updateUsers(Users users);
    public List<Users> getAll();
}
