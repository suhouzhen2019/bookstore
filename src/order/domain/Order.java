package order.domain;

import user.domain.User;

import java.util.Date;
import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 21:01
 * 订单
 */
public class Order {
    private String oid;
    private Date orderTime; //下单时间
    private double total;   //合计
    private int state;      //订单状态：1未付款 2已付款未发货 3已发货未确认 4已确认
    private User user;      //订单所有者
    private String address; //收货地址
    private List<OrderItem> orderItemList;  //订单下所有条目

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
