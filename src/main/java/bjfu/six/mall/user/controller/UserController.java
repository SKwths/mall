package bjfu.six.mall.user.controller;


import bjfu.six.mall.entity.Result;
import bjfu.six.mall.entity.Users;
import bjfu.six.mall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

//用户模块
@Controller
public class UserController {
    @Autowired
    UserService userService;

    //更新用户信息接口
    @PostMapping("/actionmall/mgr/user/updateuser.do")
    @ResponseBody
    public Result updateUser(@RequestBody Users user){
        Result result=new Result();
        if(userService.updateUser(user)){
            result.setStatus(0);
            result.setMsg("用户信息修改成功");
            result.setData(user);
        }else{
            result.setStatus(1);
            result.setMsg("用户信息修改失败");
        }
        return result;
    }
    //查找用户信息接口
    @PostMapping("/actionmall/mgr/user/finduser.do")
    @ResponseBody
    public Result findUser(@RequestBody Users user){
        Result result=new Result();
        List<Users>users=new LinkedList<>();
        users=userService.selectUser(user.getId());
        if(users.size()==0){
            result.setStatus(1);
            result.setMsg("获取用户信息失败");
        }else{
            result.setStatus(0);
            result.setMsg("获取用户信息成功");
            result.setData(users.get(0));
        }

        return result;
    }
    //删除用户接口
    @PostMapping("/actionmall/mgr/user/deleteusers.do")
    @ResponseBody
    public Result deleteUser(@RequestBody Users user){
        Result result=new Result();
        if(!userService.deleteUser(user.getId())){
            result.setStatus(1);
            result.setMsg("用户存在订单无法删除");
        }else{
            result.setStatus(0);
        }

        return result;
    }
    //查询用户列表接口
    //注意这里没有用文档上的data变量返回，为了方便而是用datas，需要注意区分
    @PostMapping("/actionmall/mgr/user/finduserlist.do")
    @ResponseBody
    public Result findAllUser(){
        Result result=new Result();
        result.setStatus(0);
        result.setDatas(userService.getAllUser());

        return result;
    }
    //后台管理用户登录接口
    @PostMapping("/actionmall/mgr/user/login.do")
    @ResponseBody
    public Result login(@RequestBody Users user){
        Result result=new Result();
        List<Users>users=new LinkedList<>();
        users=userService.getUserByAccount(user.getAccount());
        if(users.size()==0||!users.get(0).getPassword().equals(user.getPassword())){
            result.setStatus(1);
            result.setMsg("密码错误！");
        }else if(users.get(0).getRole()!=1){
            result.setStatus(1);
            result.setMsg("不是管理员,无法登录！");
        }else{
            result.setStatus(0);
            result.setMsg("登陆成功");
            result.setData(users.get(0));
        }

        return result;
    }

}
