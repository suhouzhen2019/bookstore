package cart.domain;

import book.domain.Book;

import java.math.BigDecimal;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 18:24
 * 购物车中的条目
 */
public class CartItem {
    private Book book;  //商品
    private int count;  //数量

    public CartItem() {
    }

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }

    /**
     * 小计，获得该条目的总价，但是没有对应的成员
     * @return 该条目的总价格
     */
    public double getTotal() {
        BigDecimal total = new BigDecimal(book.getPrice() + "");
        total = total.multiply(new BigDecimal(count + ""));
        return total.doubleValue();
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
}
