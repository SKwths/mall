package bjfu.six.mall.entity.vo;

import bjfu.six.mall.entity.po.Products;

public class ParamAndProduct {
    private Integer param_id;

    private String name;

    private Products[] children;

    public Integer getParam_id() {
        return param_id;
    }

    public void setParam_id(Integer param_id) {
        this.param_id = param_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Products[] getChildren() {
        return children;
    }

    public void setChildren(Products[] children) {
        this.children = children;
    }


}
