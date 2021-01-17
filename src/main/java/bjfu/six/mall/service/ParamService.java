package bjfu.six.mall.service;

import bjfu.six.mall.entity.po.Params;
import bjfu.six.mall.mapper.ParamsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamService {

    @Autowired
    private ParamsMapper paramsMapper;

    /**
     * 添加一个商品类别
     * @param parentId  父级别的id，为0时表示没有父类别
     * @param name      要添加的商品类别名称
     */
    public void addParam(int parentId, String name) throws RuntimeException{
        if(name == null || name.equals("")) {
            throw new RuntimeException("不合法的类型名字");
        }
        Params params = new Params();
        params.setParentId(0);
        params.setName(name);
        Params parent = paramsMapper.selectParamsById(parentId);
        if(parentId == 0 || parent != null){
            if(paramsMapper.insertParams(params) == 0){
                throw new RuntimeException("添加失败");
            }
            return;
        }
        throw new RuntimeException("没有此父级id");
    }

    public Params[] getAllParams(){
        return paramsMapper.selectAllParams();
    }

    public Params[] getAllParams2(){
        return paramsMapper.selectAllParams2();
    }

    public void updateParam(String name, int id)
    {
        if(paramsMapper.updateParam(name,id) ==0)
        {
            throw new RuntimeException("参数错误");
        }
        return;
    }

    public int findClsCmdt(int id)
    {
        if(paramsMapper.cmdt(id)==0)
            return 0;
        else return 1;
    }

    public void delParamByid(int id)
    {
        paramsMapper.deleteById(id);
    }
}
