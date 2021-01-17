package bjfu.six.mall.service;

import bjfu.six.mall.entity.po.Products;
import bjfu.six.mall.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductsMapper productsMapper;

    public Products getDetail(int product_id){
        return productsMapper.selectProductsById(product_id);
    }

    public Products[] getHot(){
        return productsMapper.selectHotProducts();
    }

    public  Products[] getByParamID(int class_id, int offset, int limit){
        return productsMapper.selectProductsByClassID(class_id, offset, limit);
    }
    public  Products[] getAll(int class_id){
        return productsMapper.selectProductsByClassID(class_id, 0, Integer.MAX_VALUE);
    }
    public  Products[] getAllProducts(Integer offset, Integer limit){
        return productsMapper.selectAllProducts(offset, limit);
    }
    public  Products[] getAllProductsMgr(){
        return productsMapper.selectAllProducts1();
    }
    public  Products[] getProductsByName(String keyword, int offset, int limit){
        return productsMapper.selectProductsByName(keyword, offset, limit);
    }
    public  Products[] getByParamIDAndName(int classId,String keyword, int offset, int limit){
        return productsMapper.selectProductsByParamIDAndName(classId,keyword, offset, limit);
    }

    public int saveProducts(String name,Integer class_id,String icon_url,String sub_images,String detail,String spec_param,Integer price,Integer stock){
        Products products=new Products();
        products.setName(name);
        products.setClassId(class_id);
        products.setIconUrl(icon_url);
        products.setSubImages(sub_images);
        products.setDetail(detail);
        products.setSpecParam(spec_param);
        products.setPrice(price);
        products.setStock(stock);
        if(productsMapper.insertProducts(products)==0){
            return 0;
        }else return products.getId();

    }

    public int updateCommodity(Integer id,String name,Integer class_id,String icon_url,String sub_images,String detail,String spec_param,Integer price,Integer stock){
        return productsMapper.updateProductsById(id,name,class_id,icon_url,sub_images,detail,spec_param,price,stock);
    }

    public int updateCommodityStatus(Integer id,Integer status,Integer hot)
    {
        return productsMapper.updateProductsStatus(id,status,hot);
    }

}
