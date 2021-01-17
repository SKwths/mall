package bjfu.six.mall.controller;

import bjfu.six.mall.common.Response;
import bjfu.six.mall.entity.po.User;
import bjfu.six.mall.entity.vo.CartList;
import bjfu.six.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/getcartcount.do")
    @ResponseBody
    public Response getcartcount(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }
        try {
            return Response.success(cartService.getquantity(user.getId()));
        }catch (RuntimeException e){
            return Response.error(1, e.getMessage());
        }
    }

    @RequestMapping("/cart/updatecarts.do")
    @ResponseBody
    public Response updatecarts(int productId, int count,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }

        try {
            CartList data=cartService.updateCommodity(user.getId(),productId,count);
            return Response.success(data);
        }catch (RuntimeException e){
            e.printStackTrace();
            return Response.error(1,"错误");
        }
    }

    @RequestMapping("/cart/clearcarts.do")
    @ResponseBody
    public Response clearcarts(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }
        try {
            cartService.clearCart(user.getId());
            return Response.success("成功清空购物车！");
        }catch (RuntimeException e){
            return Response.error(1,"请登录后再查看购物车！！");
        }
    }

    @RequestMapping("/cart/delcarts.do")
    @ResponseBody
    public Response delcarts(int productId,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }
        try {
            return Response.success(cartService.delCommodity(user.getId(),productId));
        }catch (RuntimeException e){
            return Response.error(1,"商品删除失败！");
        }
    }

    @RequestMapping("/cart/findallcarts.do")
    @ResponseBody
    public Response findallcarts(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }
        try {
            return Response.success(cartService.findAllCart(user.getId()));
        }catch (RuntimeException e){
            return Response.error(1,"失败！");
        }
    }

    @RequestMapping("/cart/savecart.do")
    @ResponseBody
    public Response savecart(int productId,int count,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后再查看购物车！！");
        }
        try {
            cartService.addCommodity(user.getId(),productId,count);
            return Response.success(" 商品已成功加入购物车！");
        }catch (RuntimeException e){
            return Response.error(1,"失败！");
        }
    }



}
