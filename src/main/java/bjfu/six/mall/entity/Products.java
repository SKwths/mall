package bjfu.six.mall.entity;

import java.util.Date;

public class Products {
    private String id;
    private String name;
    private String product_id;
    private String parts_id;
    private String icon_url;
    private String sub_images;
    private String detail;
    private String spec_param;
    private Double price;
    private Integer stock;
    private Integer status;
    private Integer is_hot;
    private Date crated;
    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getParts_id() {
        return parts_id;
    }

    public void setParts_id(String parts_id) {
        this.parts_id = parts_id;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getSub_images() {
        return sub_images;
    }

    public void setSub_images(String sub_images) {
        this.sub_images = sub_images;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpec_param() {
        return spec_param;
    }

    public void setSpec_param(String spec_param) {
        this.spec_param = spec_param;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(Integer is_hot) {
        this.is_hot = is_hot;
    }

    public Date getCrated() {
        return crated;
    }

    public void setCrated(Date crated) {
        this.crated = crated;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
