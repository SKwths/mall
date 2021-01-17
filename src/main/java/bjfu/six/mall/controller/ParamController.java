package bjfu.six.mall.controller;

import bjfu.six.mall.common.Response;
import bjfu.six.mall.entity.po.Params;
import bjfu.six.mall.entity.po.Products;
import bjfu.six.mall.entity.po.User;
import bjfu.six.mall.entity.vo.ParamAndProduct;
import bjfu.six.mall.service.ParamService;
import bjfu.six.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ParamController {

    @Autowired
    private ParamService paramService;

    @Autowired
    private ProductService productService;


    @RequestMapping("/mgr/param/saveparam.do")
    @ResponseBody
    public Response addAClass(Integer parent_id, String name, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后在进行操作！");
        }
        if(user.getRole() == 0){
            return Response.error(1, "你没有权限进行此操作！");
        }
        try {
            paramService.addParam(parent_id, name);
            return Response.success("产品参数新增成功！");
        }catch (RuntimeException e){
            return Response.error(1, "产品参数新增失败！");
        }
    }
    @RequestMapping("product/findfloors.do")
    @ResponseBody
    public Response getFloors(){
        try {
            ArrayList<ParamAndProduct> cams = new ArrayList<ParamAndProduct>();
            Params[] params=paramService.getAllParams();

            if(params.length<=4){
                for(Params c : params){
                    ParamAndProduct cam = new ParamAndProduct();
                    cam.setParam_id(c.getId());
                    cam.setName(c.getName());
                    Products[] products = productService.getByParamID(c.getId(), 0, Integer.MAX_VALUE);
                    if(products.length<=8){
                        for(Products po:products){
                            po.setDetail(null);
                            po.setSubImages(null);
                            po.setSpecParam(null);
                        }
                        cam.setChildren(products);
                        cams.add(cam);
                    }
                    else{
                        Products[] products1=new Products[8];
                        for(int j=0;j<8;j++){
                            products[j].setDetail(null);
                            products[j].setSpecParam(null);
                            products[j].setSubImages(null);
                            products1[j]=products[j];
                        }
                        cam.setChildren(products1);
                        cams.add(cam);

                    }

                }
                Response rs=new Response();
                rs.setData(cams);
                rs.setMsg("返回分类和商品成功");
                return rs;
            }
            else{
                for(int i=0;i<4;i++){
                    ParamAndProduct cam = new ParamAndProduct();
                    cam.setParam_id(params[i].getId());
                    cam.setName(params[i].getName());
                    Products[] products = productService.getByParamID(params[i].getId(), 0, Integer.MAX_VALUE);
                    if(products.length<=8){
                        for(Products pro:products){
                            pro.setDetail(null);
                            pro.setSubImages(null);
                            pro.setSpecParam(null);
                        }
                        cam.setChildren(products);
                        cams.add(cam);
                    }
                    else{
                        Products[] products1=new Products[8];
                        for(int j=0;j<8;j++){
                            products[j].setDetail(null);
                            products[j].setSpecParam(null);
                            products[j].setSubImages(null);
                            products1[j]=products[j];
                        }
                        cam.setChildren(products1);
                        cams.add(cam);

                    }
                }

                Response rs=new Response();
                rs.setData(cams);
                rs.setMsg("返回分类和商品成功");
                return rs;
            }
        }catch (RuntimeException e){
            return Response.error(1, "返回分类和商品失败！");
        }
    }

    @RequestMapping("/param/findallparams.do")
    @ResponseBody
    public Response getAllClassAndCommodity(){
        try {
            ArrayList<ParamAndProduct> cams = new ArrayList<ParamAndProduct>();
            Params[] params=paramService.getAllParams2();
            for(Params p : params){
                ParamAndProduct cam = new ParamAndProduct();
                cam.setParam_id(p.getId());
                cam.setName(p.getName());
                Products[] products = productService.getByParamID(p.getId(), 0, Integer.MAX_VALUE);
                cam.setChildren(products);
                cams.add(cam);
            }
            Response rs=new Response();
            rs.setData(cams);
            rs.setMsg("返回分类和商品成功");
            return rs;
        }catch (RuntimeException e){
            return Response.error(1, "返回分类和商品失败！");
        }
    }

    @RequestMapping("/mgr/param/updateparam.do")
    @ResponseBody
    public Response updateClass(String name,String id ,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后在进行操作！");
        }
        if(user.getRole() == 0){
            return Response.error(1, "你没有权限进行此操作！");
        }
        try
        {
            int updateid = Integer.valueOf(id).intValue();
            paramService.updateParam(name,updateid);
            return Response.success("产品参数修改成功");

        }catch (Exception e)
        {
            return Response.error(1,"产品参数修改失败");
        }
    }

    @RequestMapping("/mgr/param/findptype.do")
    @ResponseBody
    public Response findAllType(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后在进行操作！");
        }
        if(user.getRole() == 0){
            return Response.error(1, "你没有权限进行此操作！");
        }
        Params params[] = paramService.getAllParams();
        Response rs = new Response();
        rs.setData(params);
        return rs;
    }

    @RequestMapping("/mgr/param/delparam.do")
    @ResponseBody
    public Response delAParam(String id,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Response.error(1, "请登录后在进行操作！");
        }
        if(user.getRole() == 0){
            return Response.error(1, "你没有权限进行此操作！");
        }
        int delid = Integer.valueOf(id).intValue();
        if(paramService.findClsCmdt(delid)==1)
        {
            return Response.error(1,"不能删除有商品的类型");
        }
        else
        {
            paramService.delParamByid(delid);
            return Response.success();
        }
    }
}
