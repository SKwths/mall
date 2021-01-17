package bjfu.six.mall.service;


import bjfu.six.mall.entity.po.Cart;
import bjfu.six.mall.entity.po.Products;
import bjfu.six.mall.entity.vo.CartList;
import bjfu.six.mall.entity.vo.Lists;
import bjfu.six.mall.mapper.CartsMapper;
import bjfu.six.mall.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartsMapper cartsMapper;
    @Autowired
    private ProductsMapper productsMapper;

    /**
     * 获取购物车中商品数量
     * @param userId      用户的ID
     * @return quantity   该用户购物车中商品数量
     */
    public int getquantity(int userId){
        ArrayList<Integer> quantity=cartsMapper.selectQuantityByuserId(userId);
        int totalquantity=0;
        for(int i=0;i<quantity.size();i++){
            totalquantity+=quantity.get(i);
        }
        return totalquantity;
    }

    /**
     * 更新购物车中某商品数量
     * @param userId      用户的ID
     * @param productId   商品的ID
     * @param count       商品数量
     * @return CartList  购物车
     */
    public CartList updateCommodity(int userId, int productId, int count){
        //更新商品的数量
        cartsMapper.updateCommodityQuantity(userId,productId,count);
        //返回商品信息
        Cart cart=cartsMapper.selectCartByuserIdAndproductId(userId,productId);
        Products commodity;

        Lists[] lists=new Lists[1];

        lists[0]=new Lists();
        lists[0].setId(cart.getId());
        lists[0].setUserId(userId);
            commodity= productsMapper.selectProductsById(cart.getProduct_id());
        lists[0].setProductId(cart.getProduct_id());
        lists[0].setName(commodity.getName());
        lists[0].setQuantity(cart.getQuantity());
        lists[0].setPrice(commodity.getPrice());
        lists[0].setStatus(commodity.getStatus());
        lists[0].setTotalPrice(commodity.getPrice()*cart.getQuantity());
        lists[0].setStock(commodity.getStock());
        lists[0].setIconUrl(commodity.getIconUrl());

        CartList cartList=new CartList();
        cartList.setLists(lists);
        cartList.setTotalPrice(lists[0].getTotalPrice());

        return cartList;

    }

    /**
     * 清空购物车
     * @param userId      用户的ID
     */
    public void clearCart(int userId){
        //清空该用户的购物车
        cartsMapper.clearCart(userId);
    }

    /**
     * 购物车删除商品
     * @param userId      用户的ID
     * @param productId   商品ID
     */
    public CartList delCommodity(int userId,int productId){
        //在数据库中删除
        cartsMapper.deleteCommodity(userId,productId);
        //返回List
        Cart[] cart;
        cart=cartsMapper.selectCartByUserId(userId);
        Products commodity;

        Lists[] lists=new Lists[cart.length];
        double totalPrice=0;

        for (int i = 0; i <cart.length ; i++) {
            lists[i]=new Lists();
            lists[i].setId(cart[i].getId());
            lists[i].setUserId(userId);
            commodity= productsMapper.selectProductsById(cart[i].getProduct_id());
            lists[i].setProductId(cart[i].getProduct_id());
            lists[i].setName(commodity.getName());
            lists[i].setQuantity(cart[i].getQuantity());
            lists[i].setPrice(commodity.getPrice());
            lists[i].setStatus(commodity.getStatus());
            lists[i].setTotalPrice(commodity.getPrice()*cart[i].getQuantity());
            lists[i].setStock(commodity.getStock());
            lists[i].setIconUrl(commodity.getIconUrl());
            totalPrice+=lists[i].getTotalPrice();
        }



        CartList cartList=new CartList();
        cartList.setLists(lists);
        cartList.setTotalPrice(totalPrice);
        return cartList;

    }

    /**
     * 购物车商品列表
     * @param userId      用户的ID
     * @return cartList   购物车列表
     */
    public CartList findAllCart(int userId){
        Cart[] cart=cartsMapper.selectCartByUserId(userId);

        Lists[] lists=new Lists[cart.length];
        double totalPrice=0;
        for (int i = 0; i <cart.length ; i++) {
            lists[i]=new Lists();
            lists[i].setId(cart[i].getId());
            lists[i].setUserId(userId);
            Products commodity= productsMapper.selectProductsById(cart[i].getProduct_id());
            lists[i].setProductId(cart[i].getProduct_id());
            lists[i].setName(commodity.getName());
            lists[i].setQuantity(cart[i].getQuantity());
            lists[i].setPrice(commodity.getPrice());
            lists[i].setStatus(commodity.getStatus());
            lists[i].setTotalPrice(commodity.getPrice()*cart[i].getQuantity());
            lists[i].setStock(commodity.getStock());
            lists[i].setIconUrl(commodity.getIconUrl());

            totalPrice+=lists[i].getTotalPrice();
        }

        CartList cartList=new CartList();
        cartList.setLists(lists);
        cartList.setTotalPrice(totalPrice);
        return cartList;
    }

    /**
     * 新增购物车商品
     * @param userId      用户的ID
     * @param productId   商品ID
     * @param count       数量
     */
    public void addCommodity(int userId,int productId,int count){
        Cart cartInSystem = cartsMapper.selectCartByuserIdAndproductId(userId, productId);
        if(cartInSystem != null){
            int quantityInSystem=cartInSystem.getQuantity();
            cartsMapper.updateCommodityQuantity(userId, productId, count+quantityInSystem);
        }
        else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProduct_id(productId);
            cart.setQuantity(count);
            cartsMapper.insertCart(cart);
        }
    }


    public Cart[] findCartsByUserId(int userId){
        return cartsMapper.selectCartByUserId(userId);
    }

}
