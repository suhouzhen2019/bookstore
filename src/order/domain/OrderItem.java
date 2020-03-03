package order.domain;

import book.domain.Book;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 21:01
 * 订单条目
 */
public class OrderItem {
    private String iid;
    private Book book;      //对应图书
    private int count;      //数量
    private double subtotal;//小计
    private Order order;    //所属订单

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
