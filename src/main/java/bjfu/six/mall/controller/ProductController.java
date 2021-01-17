package bjfu.six.mall.controller;


import bjfu.six.mall.common.Response;
import bjfu.six.mall.entity.po.Products;
import bjfu.six.mall.entity.po.User;
import bjfu.six.mall.entity.vo.ProductsSearchData;
import bjfu.six.mall.mapper.ProductsMapper;
import bjfu.six.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsMapper productsMapper;

    @RequestMapping("/product/getdetail.do")
    @ResponseBody
    public Response getDetail(Integer productId) {
        try {

            Products commodity = productService.getDetail(productId);

            if (commodity != null) {
                return Response.success(commodity);
            } else return Response.error(1, "产品已经下架！");
        } catch (RuntimeException e) {
            return Response.error(1, "商品不存在");
        }
    }

    @RequestMapping("/product/setstatus.do")
    @ResponseBody
    public Response setStatus(String productId, String status, String hot) {
        int productid, statusid, ishot;
        productid = Integer.valueOf(productId).intValue();
        statusid = Integer.valueOf(status).intValue();
        ishot = Integer.valueOf(hot).intValue();
        if ((statusid == 1 || statusid == 2 || statusid == 3) && (ishot == 1 || ishot == 2)) {
            if (productService.updateCommodityStatus(productid, statusid, ishot) != 0) {
                return Response.success("修改产品状态完成");
            } else return Response.error(1, "修改产品参数失败");
        } else return Response.error(1, "非法参数");
    }

    @RequestMapping("/product/findhotproducts.do")
    @ResponseBody
    public Response getHot(String num) {
        try {

            Products[] hot_commodities = productService.getHot();


            int n = Integer.parseInt(num);

            if (n <= hot_commodities.length) {
                ArrayList<Products> ht = new ArrayList<Products>();
                for (int i = 0; i < n; i++) {
                    hot_commodities[i].setSpecParam("");
                    hot_commodities[i].setDetail("");
                    ht.add(hot_commodities[i]);
                }
                return Response.success(ht);
            } else {
                return Response.success(hot_commodities);
            }
        } catch (RuntimeException e) {
            return Response.error(1, "返回热销商品失败！");
        }
    }

    @RequestMapping("/product/findhotproductsapp.do")
    @ResponseBody
    public Response getHotApp(String num) {
        try {

            Products[] hot_commodities = productService.getHot();
            int n = Integer.parseInt(num);
            ArrayList<Products> ht = new ArrayList<Products>();
            for (int i = 0; i < 2; i++) {
                hot_commodities[i].setSpecParam("");
                hot_commodities[i].setDetail("");
                ht.add(hot_commodities[i]);
            }
            return Response.success(ht);
        } catch (RuntimeException e) {
            return Response.error(1, "返回热销商品失败！");
        }
    }

    @RequestMapping("/product/findhotproductsapp1.do")
    @ResponseBody
    public Response getHotApp1(String num) {
        try {

            Products[] hot_products = productService.getHot();
            int n = Integer.parseInt(num);


            ArrayList<Products> ht = new ArrayList<Products>();
            for (int i = 2; i < 4; i++) {
                hot_products[i].setSpecParam("");
                hot_products[i].setDetail("");
                ht.add(hot_products[i]);
            }
            return Response.success(ht);
        } catch (RuntimeException e) {
            return Response.error(1, "返回热销商品失败！");
        }
    }

    @RequestMapping("/product/findhotproductsapp2.do")
    @ResponseBody
    public Response getHotApp2(String num) {
        try {

            Products[] hot_commodities = productService.getHot();
            int n = Integer.parseInt(num);


            ArrayList<Products> ht = new ArrayList<Products>();
            for (int i = 4; i < 6; i++) {
                hot_commodities[i].setSpecParam("");
                hot_commodities[i].setDetail("");
                ht.add(hot_commodities[i]);
            }
            return Response.success(ht);
        } catch (RuntimeException e) {
            return Response.error(1, "返回热销商品失败！");
        }
    }

    @RequestMapping("/mgr/product/saveproduct.do")
    @ResponseBody
    public Response saveProduct(HttpSession session, Integer id, String name, Integer class_id, String icon_url, String sub_images, String detail, String spec_param, Integer price, Integer stock) {
        User mgr = (User) session.getAttribute("user");
        if (mgr == null || (mgr != null && mgr.getRole() != 1)) {  //1-管理员账号
            return Response.error(1, "不是管理员");
        }
        if (id != null) {
            if (productService.updateCommodity(id, name, class_id, icon_url, sub_images, detail, spec_param, price, stock) != 0) {
                return Response.success("产品新更新成功！");
            } else {
                return Response.error(1, "产品更新失败");
            }
        } else {
            int commodityid = productService.saveProducts(name, class_id, icon_url, sub_images, detail, spec_param, price, stock);
            if (commodityid != 0) {
                return Response.success("产品新增成功！", commodityid);
            } else {
                return Response.error(1, "产品新增失败");
            }
        }
    }

    @RequestMapping("/product/searchproducts.do")
    @ResponseBody
    public Response searchProduct(String keyword, String classId, Integer offset, Integer limit) {
        if (keyword == null || classId == null || offset == null || limit == null) {
            return Response.error(1, "错误的参数传递");
        }
        if (keyword.equals("")) {
            if (classId.equals("0")) {
                Products[] products = productService.getAllProducts(offset, limit);
                int count = productsMapper.selectAllProductsCount();
                return Response.success(new ProductsSearchData(products, count));
            } else {
                Products[] products = productService.getByParamID(Integer.parseInt(classId), offset, limit);
                int count = productsMapper.selectProductsByClassIDCount(Integer.parseInt(classId));
                return Response.success(new ProductsSearchData(products, count));
            }
        } else {
            if (classId.equals("0")) {
                Products[] products = productService.getProductsByName(keyword, offset, limit);
                int count = productsMapper.selectProductsByNameCount(keyword);
                if (products.length != 0) {
                    return Response.success(new ProductsSearchData(products, count));
                } else return Response.error(1, "未查找到商品");
            } else {
                Products[] products = productService.getByParamIDAndName(Integer.parseInt(classId), keyword, offset, limit);
                int count = productsMapper.selectProductsByClassIDAndNameCount(Integer.parseInt(classId), keyword);
                if (products.length != 0) {
                    return Response.success(new ProductsSearchData(products, count));
                } else return Response.error(1, "未查找到商品");
            }
        }
    }

    @RequestMapping("/mgr/product/getdetail.do")
    @ResponseBody
    public Response getMgrDetail(HttpSession session, Integer productId) {
        User mgr = (User) session.getAttribute("user");
        if (mgr == null || (mgr != null && mgr.getRole() != 1)) {  //1-管理员账号
            return Response.error(1, "不是管理员");
        }
        try {

            Products commodity = productService.getDetail(productId);

            if (commodity != null) {
                return Response.success(commodity);
            } else return Response.error(1, "产品已经下架！");
        } catch (RuntimeException e) {
            return Response.error(1, "商品不存在");
        }
    }

    @RequestMapping("/mgr/product/productlist.do")
    @ResponseBody
    public Response getProductList(String id, HttpSession session) {
        User mgr = (User) session.getAttribute("user");
        if (mgr == null || (mgr != null && mgr.getRole() != 1)) {  //1-管理员账号
            return Response.error(1, "不是管理员");
        }
        if (id.equals("")) {
            Products[] products = productService.getAllProductsMgr();
            if (products.length != 0) {
                return Response.success(products);
            } else {
                return Response.error(1, "无商品！");
            }

        } else {
            Products product = productService.getDetail(Integer.parseInt(id));
            Products[] products = new Products[1];

            if (product != null) {
                products[0] = product;
                return Response.success(products);
            } else return Response.error(1, "产品已经下架！");
        }
    }


    @RequestMapping("/mgr/product/setstatus.do")
    @ResponseBody
    public Response setStatusMgr(String productId, String status, String hot, HttpSession session) {
        User mgr = (User) session.getAttribute("user");
        if (mgr == null || (mgr != null && mgr.getRole() != 1)) {  //1-管理员账号
            return Response.error(1, "不是管理员");
        }
        int productid, statusid, ishot;
        productid = Integer.valueOf(productId).intValue();
        statusid = Integer.valueOf(status).intValue();
        ishot = Integer.valueOf(hot).intValue();
        if ((statusid == 0 || statusid == 1 || statusid == 2 || statusid == 3) && (ishot == 0 || ishot == 1 || ishot == 2)) {
            if (productService.updateCommodityStatus(productid, statusid, ishot) != 0) {
                return Response.success("修改产品状态完成");
            } else return Response.error(1, "修改产品参数失败");
        } else return Response.error(1, "非法参数");
    }
}
