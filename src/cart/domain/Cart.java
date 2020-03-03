package cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 18:26
 * 购物车
 */
public class Cart {
    private Map<String, CartItem> cartItems = new LinkedHashMap<>();

    /**
     * 合计
     * @return 返回所有条目的小计之和
     */
    public double getTotal() {
        BigDecimal total = new BigDecimal("0");
        for (CartItem value : cartItems.values()) {
            total = total.add(new BigDecimal(value.getTotal() + ""));
        }
        return total.doubleValue();
    }

    /**
     * 添加购物车条目
     * @param item 购物车条目
     */
    public void add(CartItem item) {
        String bid = item.getBook().getBid();
        CartItem _item = cartItems.get(bid);  // 查看当前条目对应图书是否已经存在

        //如果存在，给 item 的 count 添加原有条目的数量
        if(_item != null) item.setCount(_item.getCount() + item.getCount());

        //添加到购物车中
        cartItems.put(bid, item);
    }

    /**
     * 清空购物车
     */
    public void clear() {
        cartItems.clear();
    }

    /**
     * 删除指定购物车条目
     * @param bid 指定购物车条目对应的图书 id
     */
    public void delete(String bid) {
        cartItems.remove(bid);
    }

    /**
     * 获取全部购物车条目信息
     * @return 购物车中的全部条目
     */
    public Collection<CartItem> getCartItems() {
        return cartItems.values();
    }
}
