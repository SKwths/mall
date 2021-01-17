package bjfu.six.mall.service;

import bjfu.six.mall.common.MD5Utils;
import bjfu.six.mall.entity.po.User;
import bjfu.six.mall.entity.vo.FindUser;
import bjfu.six.mall.entity.vo.UserRegister;
import bjfu.six.mall.mapper.OrdersMapper;
import bjfu.six.mall.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    public User login(String account, String password){
        User user = usersMapper.selectUserByAccount(account, MD5Utils.getMD5(password));
        return user;
    }

    public User loginMgr(String account, String password){
        User user = usersMapper.selectUserByAccount(account, MD5Utils.getMD5(password));
        return user;
    }

    public void register(UserRegister userRegister) throws RuntimeException{
        if(userRegister.getAccount() == null || userRegister.getAccount().length() > 16){
            throw  new RuntimeException("账号长度不能为空且不能超过16个字符");
        }
        if(userRegister.getPassword() == null || userRegister.getPassword().length() > 32){
            throw new RuntimeException("密码长度不能为空且密码长度不能超过32个字符");
        }
        if(userRegister.getEmail() == null){
            throw new RuntimeException("请输入邮箱参数");
        }
        if(userRegister.getPhone() == null){
            throw new RuntimeException("请输入手机号参数");
        }
        if(userRegister.getQuestion() == null){
            throw  new RuntimeException("请输入密保问题");
        }
        if(userRegister.getAsw() == null){
            throw  new RuntimeException("请输入密保答案");
        }

        String emailRegex = "^([a-zA-Z0-9]+[-|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|_|.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]+$";
        String phoneRegex = "^(13[0-9]|14[01456879]|15[0-3,5-9]|16[2567]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$";
        if(! userRegister.getEmail().equals("")){
            if(!Pattern.matches(emailRegex, userRegister.getEmail())){
                throw new RuntimeException("邮箱格式不正确");
            }
            if(userRegister.getEmail().length() > 20){
                throw new RuntimeException("邮箱长度最大为20");
            }
        }
        if(! userRegister.getPhone().equals("")){
            if(! Pattern.matches(phoneRegex, userRegister.getPhone())){
                throw new RuntimeException("手机号格式不正确");
            }
        }
        User userInSystem = usersMapper.selectUser(userRegister.getAccount());
        if(userInSystem != null){
            throw  new RuntimeException("该用户已经存在");
        }

        User user = new User();
        user.setAccount(userRegister.getAccount());
        user.setPassword(MD5Utils.getMD5(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        user.setPhone(userRegister.getPhone());
        user.setQuestion(userRegister.getQuestion());
        user.setAsw(userRegister.getAsw());
        user.setName("");
        user.setRole(0);
        user.setAge(0);
        user.setSex(0);
        user.setDel(0);
        if(usersMapper.insertUser(user) == 0) {
            throw new RuntimeException("注册失败");
        }
    }

    public User userUpdateUserInfo(String email,String phone,String question,String asw,int age,int sex,int userId){
        usersMapper.updateUserInfo(email, phone, question, asw, age, sex, userId);
        User user=usersMapper.selectUserByUserId(userId);
        return user;
    }

    public void userUpdateUserPwd(int userId,String oldpwd,String newpwd){
        if(usersMapper.updateUserPwd(userId,MD5Utils.getMD5(oldpwd),MD5Utils.getMD5(newpwd))==0){
            throw new RuntimeException("修改失败");
        }
    }

    public void userResetPwdByQuestion(int userId,String newpwd){
        if(usersMapper.updateUserPwdByQuestion(userId,MD5Utils.getMD5(newpwd))==0){
            throw new RuntimeException("修改失败");
        }
    }

    public User getUserAsw(String account, String question, String asw){
        User user =usersMapper.selectUser(account);
        if(user == null){
            throw new RuntimeException("不存在此用户");
        }
        if(!user.getQuestion().equals(question)){
            throw new RuntimeException("改用户无此问题");
        }
        if(!user.getAsw().equals(asw)){
            throw new RuntimeException("错误的答案");
        }
        return user;
    }

    public String getUserQuestion(String account){
        String question=usersMapper.selectUserQuestionByAccount(account);
        return question;
    }

    public User getUserInfo(int userId){
        User user=usersMapper.selectUserByUserId(userId);
        return user;
    }

    public User getUserByAccount(String account) {
        if(account == null){
            throw new RuntimeException("请输入账号");
        }
        User user = usersMapper.selectUser(account);
        if(user == null) {
            throw new RuntimeException("不存在此用户名");
        }
        return user;
    }

    public User updateUser(int id,String name,String account,
                                 int age,String phone,String email,int sex){
        usersMapper.mgrUpdateUser(id,name,account,age,phone,email,sex);
        User user=usersMapper.findUser(id);
        return user;
    }

    public FindUser findUser(int id){

        User user=new User();
        FindUser findUser=new FindUser();
        user=usersMapper.findUser(id);

        findUser.setAccount(user.getAccount());
        findUser.setAge(user.getAge());
        findUser.setEmail(user.getEmail());
        findUser.setId(user.getId());
        findUser.setName(user.getName());
        findUser.setPhone(user.getPhone());
        findUser.setSex(user.getSex());

        return findUser;
    }

    public int deleteUsersUser(int id){
        if(usersMapper.deleteUser(id) == 0){
            return 0;
        }else{
            return 1;
        }
    }

    public List<FindUser> findUserList(){

        List<FindUser> findUsers=usersMapper.findUserList();
        return findUsers;
    }

}
