package cn.how2j.trend.pojo;

import java.io.Serializable;

/**
 * @Description : TODO
 * @Author : yzn_f
 * @Date : 2022/10/1 18:49
 * @Version : 1.0
 **/
public class Index implements Serializable {
    String code;
    String name;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
