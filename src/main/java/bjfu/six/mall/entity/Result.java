package bjfu.six.mall.entity;

import java.util.List;
//用户模块用户返回的Result结构体
public class Result {
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg;


    public Users getData() {
        return data;
    }

    public void setData(Users data) {
        this.data = data;
    }

    Users data;

    public List<Users> getDatas() {
        return datas;
    }

    public void setDatas(List<Users> datas) {
        this.datas = datas;
    }

    List<Users> datas;
}
