package bjfu.six.mall.user.service;

import bjfu.six.mall.entity.Users;
import bjfu.six.mall.mapper.OrdersMapper;
import bjfu.six.mall.mapper.UsersMapper;
import bjfu.six.mall.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    OrdersMapper ordersMapper;

    public List<Users>  selectUser(String id){
        return usersMapper.selectById(id);
    }

    public boolean deleteUser(String id){
        if(ordersMapper.getByUserid(id).size()>0){
            return false;
        }
        if(usersMapper.deleteById(id)==0){
            return false;
        }
        return true;
    }
    public List<Users> getAllUser(){
        return usersMapper.getAll();
    }

    public boolean updateUser(Users user) {
        if(usersMapper.updateUsers(user)==0){
            return false;
        }
        return true;
    }

    public List<Users> getUserByAccount(String account){
        return usersMapper.selectByAccount(account);
    }
}
