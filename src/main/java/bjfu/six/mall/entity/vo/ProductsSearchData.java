package bjfu.six.mall.entity.vo;

import bjfu.six.mall.entity.po.Products;

public class ProductsSearchData {
    private Products[] lists;
    private Integer count;

    public ProductsSearchData(Products[] lists, Integer count) {
        this.lists = lists;
        this.count = count;
    }

    public Products[] getLists() {
        return lists;
    }

    public void setLists(Products[] lists) {
        this.lists = lists;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
