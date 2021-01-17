package bjfu.six.mall.controller;

import bjfu.six.mall.common.Response;
import bjfu.six.mall.entity.po.*;
import bjfu.six.mall.entity.vo.OrderItem;
import bjfu.six.mall.entity.vo.OrderList;
import bjfu.six.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;
    @Autowired
    AddrService addrService;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;

    @RequestMapping("/order/confirmreceipt.do")
    @ResponseBody
    public Response confirmReceipt(String orderNo) {
        if (orderService.confirmReceipt(orderNo) == 1) {
            return Response.success("订单已确认收货！");
        } else return Response.error(0, "失败！");
    }

    @RequestMapping("/order/payreceipt.do")
    @ResponseBody
    public Response payReceipt(String orderNo) {
        if (orderService.payReceipt(orderNo) == 1) {
            return Response.success("订单已付款！");
        } else return Response.error(0, "失败！");
    }

    @RequestMapping("/mgr/order/deliverreceipt.do")
    @ResponseBody
    public Response deliverReceipt(String orderNo, HttpSession session) {

        User mgr = (User) session.getAttribute("user");
        if (mgr == null || (mgr != null && mgr.getRole() != 1)) {  //1-管理员账号
            return Response.error(1, "不是管理员");
        }
        if (orderService.deliverReceipt(orderNo) == 1) {
            return Response.success("订单已发货！");
        } else return Response.error(0, "失败！");
    }


    @RequestMapping("/order/cancelorder.do")
    @ResponseBody
    public Response cancelOrder(String orderNo) {
        if (orderService.cancelOrder(orderNo) == 1) {
            return Response.success("订单已确认收货！");
        } else return Response.error(0, "失败！");
    }

    @RequestMapping("/order/getdetail.do")
    @ResponseBody
    public Response getDetail(String orderNo) {
        Order order = orderService.getOrderDetail(Long.parseLong(orderNo));
        if (order != null) {
            int order_id = order.getId();
            Item[] items = itemService.getItemsByOrderId(order_id);
            ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
            for (int i = 0; i < items.length; i++) {
                OrderItem orderItem = new OrderItem();
                Products products = productService.getDetail(items[i].getProductId());

                orderItem.setOrderId(items[i].getOrderId());
                orderItem.setCommodityId(items[i].getProductId());
                orderItem.setIconUrl(products.getIconUrl());
                orderItem.setCommodityName(products.getName());
                orderItem.setQuantity(items[i].getQuantity());
                orderItem.setCurPrice(products.getPrice());
                orderItem.setTotalPrice(items[i].getQuantity() * products.getPrice());
                orderItems.add(orderItem);
            }


            Address addr = addrService.findAddressById(order.getAddrId());
            OrderList orderList = new OrderList();
            orderList.setOrderItems(orderItems);
            orderList.setOrderNo(Long.toString(order.getOrderNo()));
            orderList.setUserId(order.getUserId());
            orderList.setAddrId(order.getAddrId());
            orderList.setAmount(order.getAmount());
            orderList.setType(order.getType());
            orderList.setFreight(order.getFreight());
            orderList.setStatus(order.getStatus());
            orderList.setCloseTime(order.getCloseTime());
            orderList.setCreateTime(order.getCreateTime());
            orderList.setDeliveryTime(order.getDeliveryTime());
            orderList.setPaymentTime(order.getPaymentTime());
            orderList.setUpdateTime(order.getUpdateTime());
            orderList.setFinishTime(order.getFinishTime());
            orderList.setAddr(addr);
            if (order.getType() == 1) {
                orderList.setTypeDesc("在线支付");
            } else {
                orderList.setTypeDesc("货到付款");
            }
            //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
            if (order.getStatus() == 0) {
                orderList.setStatusDesc("未付款");
            } else if (order.getStatus() == 1) {
                orderList.setStatusDesc("已付款");
            } else if (order.getStatus() == 2) {
                orderList.setStatusDesc("已发货");
            } else if (order.getStatus() == 3) {
                orderList.setStatusDesc("交易成功");
            } else if (order.getStatus() == 4) {
                orderList.setStatusDesc("交易关闭");
            } else {
                orderList.setStatusDesc("取消");
            }
            return Response.success(orderList);
        } else {
            return Response.error(1, "订单错误");
        }
    }


    @RequestMapping("/order/getlist.do")
    @ResponseBody
    public Response getList(String status, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.error(1, "未登录");
        } else {
            int userId = user.getId();
            Order[] orders = orderService.getOrderList(Integer.parseInt(status), userId);
            if (orders.length == 0) {
                return Response.error(1, "订单错误");
            } else {
                ArrayList<OrderList> ordersList = new ArrayList<OrderList>();
                for (int i = 0; i < orders.length; i++) {
                    Order order = orders[i];
                    int order_id = order.getId();
                    Item[] items = itemService.getItemsByOrderId(order_id);
                    ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
                    for (int j = 0; j < items.length; j++) {
                        OrderItem orderItem = new OrderItem();
                        Products products = productService.getDetail(items[j].getProductId());

                        orderItem.setOrderId(items[j].getOrderId());
                        orderItem.setCommodityId(items[j].getProductId());
                        orderItem.setIconUrl(products.getIconUrl());
                        orderItem.setCommodityName(products.getName());
                        orderItem.setQuantity(items[j].getQuantity());
                        orderItem.setCurPrice(products.getPrice());
                        orderItem.setTotalPrice(items[j].getQuantity() * products.getPrice());
                        orderItems.add(orderItem);
                    }
                    Address addr = addrService.findAddressById(order.getAddrId());
                    OrderList orderList = new OrderList();
                    orderList.setOrderItems(orderItems);
                    orderList.setOrderNo(Long.toString(order.getOrderNo()));
                    orderList.setUserId(order.getUserId());
                    orderList.setAddrId(order.getAddrId());
                    orderList.setAmount(order.getAmount());
                    orderList.setType(order.getType());
                    orderList.setFreight(order.getFreight());
                    orderList.setStatus(order.getStatus());
                    orderList.setCloseTime(order.getCloseTime());
                    orderList.setCreateTime(order.getCreateTime());
                    orderList.setDeliveryTime(order.getDeliveryTime());
                    orderList.setPaymentTime(order.getPaymentTime());
                    orderList.setUpdateTime(order.getUpdateTime());
                    orderList.setFinishTime(order.getFinishTime());
                    orderList.setAddr(addr);
                    if (order.getType() == 1) {
                        orderList.setTypeDesc("在线支付");
                    } else {
                        orderList.setTypeDesc("货到付款");
                    }
                    //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
                    if (order.getStatus() == 0) {
                        orderList.setStatusDesc("未付款");
                    } else if (order.getStatus() == 1) {
                        orderList.setStatusDesc("已付款");
                    } else if (order.getStatus() == 2) {
                        orderList.setStatusDesc("已发货");
                    } else if (order.getStatus() == 3) {
                        orderList.setStatusDesc("交易成功");
                    } else if (order.getStatus() == 4) {
                        orderList.setStatusDesc("交易关闭");
                    } else {
                        orderList.setStatusDesc("取消");
                    }

                    ordersList.add(orderList);
                }
                return Response.success(ordersList);
            }
        }
    }

    @RequestMapping("/order/createorder.do")
    @ResponseBody
    public Response createOrder(String addrId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.error(1, "未登录");
        } else {
            int userId = user.getId();
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
            s += userId;
            Long orderNo = Long.parseLong(s);
            Cart[] carts = cartService.findCartsByUserId(userId);
            if (carts.length == 0) return Response.error(1, "购物车为空，请选择要购买的商品！");
            double totalPrice = 0;
            for (int i = 0; i < carts.length; i++) {
                Products products = productService.getDetail(carts[i].getProduct_id());
                if (products == null) return Response.error(1, "购物车中某商品已经下架，不能在线购买！");
                totalPrice = totalPrice + (products.getPrice() * carts[i].getQuantity());
            }
            Order order = new Order();
            order.setUserId(userId);
            order.setOrderNo(orderNo);
            order.setAmount(totalPrice);
            order.setAddrId(Integer.parseInt(addrId));
            order.setStatus(0);
            order.setType(0);
            order.setFreight(10);
            orderService.addOrder(order);
            int orderId = order.getId();
            for (int i = 0; i < carts.length; i++) {
                Item item = new Item();
                item.setOrderId(orderId);
                item.setProductId(carts[i].getProduct_id());
                item.setQuantity(carts[i].getQuantity());
                itemService.addItems(item);
            }

            int order_id = order.getId();
            Item[] items = itemService.getItemsByOrderId(order_id);
            ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
            for (int i = 0; i < items.length; i++) {
                OrderItem orderItem = new OrderItem();
                Products products = productService.getDetail(items[i].getProductId());
                orderItem.setOrderId(items[i].getOrderId());
                orderItem.setCommodityId(items[i].getProductId());
                orderItem.setIconUrl(products.getIconUrl());
                orderItem.setCommodityName(products.getName());
                orderItem.setQuantity(items[i].getQuantity());
                orderItem.setCurPrice(products.getPrice());
                orderItem.setTotalPrice(items[i].getQuantity() * products.getPrice());
                orderItems.add(orderItem);
            }


            Address addr = addrService.findAddressById(order.getAddrId());
            OrderList orderList = new OrderList();
            orderList.setOrderItems(orderItems);
            orderList.setOrderNo(Long.toString(order.getOrderNo()));
            orderList.setUserId(order.getUserId());
            orderList.setAddrId(order.getAddrId());
            orderList.setAmount(order.getAmount());
            orderList.setType(order.getType());
            orderList.setFreight(order.getFreight());
            orderList.setStatus(order.getStatus());
            orderList.setCloseTime(order.getCloseTime());
            orderList.setCreateTime(order.getCreateTime());
            orderList.setDeliveryTime(order.getDeliveryTime());
            orderList.setPaymentTime(order.getPaymentTime());
            orderList.setUpdateTime(order.getUpdateTime());
            orderList.setFinishTime(order.getFinishTime());
            orderList.setAddr(addr);
            if (order.getType() == 1) {
                orderList.setTypeDesc("在线支付");
            } else {
                orderList.setTypeDesc("货到付款");
            }
            //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
            if (order.getStatus() == 0) {
                orderList.setStatusDesc("未付款");
            } else if (order.getStatus() == 1) {
                orderList.setStatusDesc("已付款");
            } else if (order.getStatus() == 2) {
                orderList.setStatusDesc("已发货");
            } else if (order.getStatus() == 3) {
                orderList.setStatusDesc("交易成功");
            } else if (order.getStatus() == 4) {
                orderList.setStatusDesc("交易关闭");
            } else {
                orderList.setStatusDesc("取消");
            }
            return Response.success(orderList);
        }
    }

    @RequestMapping("/order/createsingleproductorder.do")
    @ResponseBody
    public Response createSingleProductOrder(String addrId, String productId, int quantity, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.error(1, "未登录");
        } else {
            int userId = user.getId();
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
            s += userId;
            Long orderNo = Long.parseLong(s);
            double totalPrice = 0;
            Products products = productService.getDetail(Integer.parseInt(productId));
            if (products == null) return Response.error(1, "商品已经下架，不能在线购买！");
            totalPrice = products.getPrice() * quantity;

            Order order = new Order();
            order.setUserId(userId);
            order.setOrderNo(orderNo);
            order.setAmount(totalPrice);
            order.setAddrId(Integer.parseInt(addrId));
            order.setStatus(0);
            order.setType(0);
            order.setFreight(10);
            orderService.addOrder(order);
            int orderId = order.getId();
            Item item = new Item();
            item.setOrderId(orderId);
            item.setProductId(Integer.parseInt(productId));
            item.setQuantity(quantity);
            itemService.addItems(item);


            //getdetail
            int order_id = order.getId();
            Item[] items = itemService.getItemsByOrderId(order_id);
            ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
            for (int i = 0; i < items.length; i++) {
                OrderItem orderItem = new OrderItem();
                Products pro = productService.getDetail(items[i].getProductId());

                orderItem.setOrderId(items[i].getOrderId());
                orderItem.setCommodityId(items[i].getProductId());
                orderItem.setIconUrl(pro.getIconUrl());
                orderItem.setCommodityName(pro.getName());
                orderItem.setQuantity(items[i].getQuantity());
                orderItem.setCurPrice(pro.getPrice());
                orderItem.setTotalPrice(items[i].getQuantity() * pro.getPrice());
                orderItems.add(orderItem);
            }


            Address addr = addrService.findAddressById(order.getAddrId());
            OrderList orderList = new OrderList();
            orderList.setOrderItems(orderItems);
            orderList.setOrderNo(Long.toString(order.getOrderNo()));
            orderList.setUserId(order.getUserId());
            orderList.setAddrId(order.getAddrId());
            orderList.setAmount(order.getAmount());
            orderList.setType(order.getType());
            orderList.setFreight(order.getFreight());
            orderList.setStatus(order.getStatus());
            orderList.setCloseTime(order.getCloseTime());
            orderList.setCreateTime(order.getCreateTime());
            orderList.setDeliveryTime(order.getDeliveryTime());
            orderList.setPaymentTime(order.getPaymentTime());
            orderList.setUpdateTime(order.getUpdateTime());
            orderList.setFinishTime(order.getFinishTime());
            orderList.setAddr(addr);
            if (order.getType() == 1) {
                orderList.setTypeDesc("在线支付");
            } else {
                orderList.setTypeDesc("货到付款");
            }
            //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
            if (order.getStatus() == 0) {
                orderList.setStatusDesc("未付款");
            } else if (order.getStatus() == 1) {
                orderList.setStatusDesc("已付款");
            } else if (order.getStatus() == 2) {
                orderList.setStatusDesc("已发货");
            } else if (order.getStatus() == 3) {
                orderList.setStatusDesc("交易成功");
            } else if (order.getStatus() == 4) {
                orderList.setStatusDesc("交易关闭");
            } else {
                orderList.setStatusDesc("取消");
            }
            return Response.success(orderList);
        }
    }

    @RequestMapping("/mgr/order/findorders.do")
    @ResponseBody
    public Response findOrders(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.error(1, "未登录");
        } else if (user.getRole() == 0) {
            return Response.error(2, "请管理员登录后在进行操作");
        } else {

            Order[] orders = orderService.getAllOrderList();
            if (orders.length == 0) {
                return Response.error(1, "订单为空");
            } else {
                ArrayList<OrderList> ordersList = new ArrayList<OrderList>();
                for (int i = 0; i < orders.length; i++) {
                    Order order = orders[i];
                    int order_id = order.getId();
                    Item[] items = itemService.getItemsByOrderId(order_id);
                    ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
                    for (int j = 0; j < items.length; j++) {
                        OrderItem orderItem = new OrderItem();
                        Products products = productService.getDetail(items[j].getProductId());

                        orderItem.setOrderId(items[j].getOrderId());
                        orderItem.setCommodityId(items[j].getProductId());
                        orderItem.setIconUrl(products.getIconUrl());
                        orderItem.setCommodityName(products.getName());
                        orderItem.setQuantity(items[j].getQuantity());
                        orderItem.setCurPrice(products.getPrice());
                        orderItem.setTotalPrice(items[j].getQuantity() * products.getPrice());
                        orderItems.add(orderItem);
                    }
                    Address addr = addrService.findAddressById(order.getAddrId());
                    OrderList orderList = new OrderList();
                    orderList.setOrderItems(orderItems);
                    orderList.setOrderNo(Long.toString(order.getOrderNo()));
                    orderList.setUserId(order.getUserId());
                    orderList.setAddrId(order.getAddrId());
                    orderList.setAmount(order.getAmount());
                    orderList.setType(order.getType());
                    orderList.setFreight(order.getFreight());
                    orderList.setStatus(order.getStatus());
                    orderList.setCloseTime(order.getCloseTime());
                    orderList.setCreateTime(order.getCreateTime());
                    orderList.setDeliveryTime(order.getDeliveryTime());
                    orderList.setPaymentTime(order.getPaymentTime());
                    orderList.setUpdateTime(order.getUpdateTime());
                    orderList.setFinishTime(order.getFinishTime());
                    orderList.setAddr(addr);
                    if (order.getType() == 1) {
                        orderList.setTypeDesc("在线支付");
                    } else {
                        orderList.setTypeDesc("货到付款");
                    }
                    //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
                    if (order.getStatus() == 0) {
                        orderList.setStatusDesc("未付款");
                    } else if (order.getStatus() == 1) {
                        orderList.setStatusDesc("已付款");
                    } else if (order.getStatus() == 2) {
                        orderList.setStatusDesc("已发货");
                    } else if (order.getStatus() == 3) {
                        orderList.setStatusDesc("交易成功");
                    } else if (order.getStatus() == 4) {
                        orderList.setStatusDesc("交易关闭");
                    } else {
                        orderList.setStatusDesc("取消");
                    }
                    ordersList.add(orderList);
                }
                return Response.success(ordersList);
            }
        }
    }


    @RequestMapping("/mgr/order/search.do")
    @ResponseBody
    public Response search(String orderNo, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            if (user.getRole() == 0) {
                return Response.error(2, "请管理员登录后在进行操作");
            } else {
                Order order = orderService.getOrderDetail(Long.parseLong(orderNo));
                if (order != null) {
                    int order_id = order.getId();
                    Item[] items = itemService.getItemsByOrderId(order_id);
                    ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
                    for (int i = 0; i < items.length; i++) {
                        OrderItem orderItem = new OrderItem();
                        Products products = productService.getDetail(items[i].getProductId());

                        orderItem.setOrderId(items[i].getOrderId());
                        orderItem.setCommodityId(items[i].getProductId());
                        orderItem.setIconUrl(products.getIconUrl());
                        orderItem.setCommodityName(products.getName());
                        orderItem.setQuantity(items[i].getQuantity());
                        orderItem.setCurPrice(products.getPrice());
                        orderItem.setTotalPrice(items[i].getQuantity() * products.getPrice());
                        orderItems.add(orderItem);
                    }


                    Address addr = addrService.findAddressById(order.getAddrId());
                    OrderList orderList = new OrderList();
                    orderList.setOrderItems(orderItems);
                    orderList.setOrderNo(Long.toString(order.getOrderNo()));
                    orderList.setUserId(order.getUserId());
                    orderList.setAddrId(order.getAddrId());
                    orderList.setAmount(order.getAmount());
                    orderList.setType(order.getType());
                    orderList.setFreight(order.getFreight());
                    orderList.setStatus(order.getStatus());
                    orderList.setCloseTime(order.getCloseTime());
                    orderList.setCreateTime(order.getCreateTime());
                    orderList.setDeliveryTime(order.getDeliveryTime());
                    orderList.setPaymentTime(order.getPaymentTime());
                    orderList.setUpdateTime(order.getUpdateTime());
                    orderList.setFinishTime(order.getFinishTime());
                    orderList.setAddr(addr);
                    if (order.getType() == 1) {
                        orderList.setTypeDesc("在线支付");
                    } else {
                        orderList.setTypeDesc("货到付款");
                    }
                    //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
                    if (order.getStatus() == 0) {
                        orderList.setStatusDesc("未付款");
                    } else if (order.getStatus() == 1) {
                        orderList.setStatusDesc("已付款");
                    } else if (order.getStatus() == 2) {
                        orderList.setStatusDesc("已发货");
                    } else if (order.getStatus() == 3) {
                        orderList.setStatusDesc("交易成功");
                    } else if (order.getStatus() == 4) {
                        orderList.setStatusDesc("交易关闭");
                    } else {
                        orderList.setStatusDesc("取消");
                    }
                    return Response.success(orderList);
                } else {
                    return Response.error(1, "订单错误");
                }
            }
        } else {
            return Response.error(1, "未登录");
        }
    }

    @RequestMapping("/mgr/order/getdetail.do")
    @ResponseBody
    public Response getMgrDetail(String orderNo) {
        Order order = orderService.getOrderDetail(Long.parseLong(orderNo));
        if (order != null) {
            int order_id = order.getId();
            Item[] items = itemService.getItemsByOrderId(order_id);
            ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
            for (int i = 0; i < items.length; i++) {
                OrderItem orderItem = new OrderItem();
                Products products = productService.getDetail(items[i].getProductId());

                orderItem.setOrderId(items[i].getOrderId());
                orderItem.setCommodityId(items[i].getProductId());
                orderItem.setIconUrl(products.getIconUrl());
                orderItem.setCommodityName(products.getName());
                orderItem.setQuantity(items[i].getQuantity());
                orderItem.setCurPrice(products.getPrice());
                orderItem.setTotalPrice(items[i].getQuantity() * products.getPrice());
                orderItems.add(orderItem);
            }


            Address addr = addrService.findAddressById(order.getAddrId());
            OrderList orderList = new OrderList();
            orderList.setOrderItems(orderItems);
            orderList.setOrderNo(Long.toString(order.getOrderNo()));
            orderList.setUserId(order.getUserId());
            orderList.setAddrId(order.getAddrId());
            orderList.setAmount(order.getAmount());
            orderList.setType(order.getType());
            orderList.setFreight(order.getFreight());
            orderList.setStatus(order.getStatus());
            orderList.setCloseTime(order.getCloseTime());
            orderList.setCreateTime(order.getCreateTime());
            orderList.setDeliveryTime(order.getDeliveryTime());
            orderList.setPaymentTime(order.getPaymentTime());
            orderList.setUpdateTime(order.getUpdateTime());
            orderList.setFinishTime(order.getFinishTime());
            orderList.setAddr(addr);
            if (order.getType() == 1) {
                orderList.setTypeDesc("在线支付");
            } else {
                orderList.setTypeDesc("货到付款");
            }
            //            1-未付款 2-已付款 3-已发货 4-交易成功 5-交易关闭 6-取消
            if (order.getStatus() == 0) {
                orderList.setStatusDesc("未付款");
            } else if (order.getStatus() == 1) {
                orderList.setStatusDesc("已付款");
            } else if (order.getStatus() == 2) {
                orderList.setStatusDesc("已发货");
            } else if (order.getStatus() == 3) {
                orderList.setStatusDesc("交易成功");
            } else if (order.getStatus() == 4) {
                orderList.setStatusDesc("交易关闭");
            } else {
                orderList.setStatusDesc("取消");
            }
            return Response.success(orderList);
        } else {
            return Response.error(1, "订单错误");
        }
    }
}
