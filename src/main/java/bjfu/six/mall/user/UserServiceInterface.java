package bjfu.six.mall.user;

import bjfu.six.mall.entity.Users;

import java.util.List;

public interface UserServiceInterface {
    //根据id查找用户
    public List<Users> selectUser(String id);
    public boolean updateUser(Users user);
    public boolean deleteUser(String id);
    //获取所有用户
    public List<Users> getAllUser();
    //根据账户名获取用户，用于登录
    public List<Users> getUserByAccount(String account);
}
