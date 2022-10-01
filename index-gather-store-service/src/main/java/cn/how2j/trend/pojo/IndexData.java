package cn.how2j.trend.pojo;

/**
 * @Description : TODO
 * @Author : yzn_f
 * @Date : 2022/10/1 17:03
 * @Version : 1.0
 **/
public class IndexData {
    String date;
    float closePoint;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public float getClosePoint() {
        return closePoint;
    }
    public void setClosePoint(float closePoint) {
        this.closePoint = closePoint;
    }
}
