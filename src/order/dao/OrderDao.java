package order.dao;

import book.domain.Book;
import category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;
import order.domain.Order;
import order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.taglibs.standard.tag.common.fmt.ParamSupport;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 21:01
 */
public class OrderDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 插入订单
     * @param order 订单对象
     */
    public void addOrder(Order order) {
        try {
            String sql = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?)";
            //处理 util.data 转换为 sql.time
            Timestamp time = new Timestamp(order.getOrderTime().getTime());
            Object[] params = {order.getOid(), time, order.getTotal(), order.getState(), order.getUser().getUid(), order.getAddress()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 插入订单条目
     * @param orderItemList 订单条目集合
     */
    public void addOrderItemList(List<OrderItem> orderItemList) {
        try {
            String sql = "INSERT INTO orderitem VALUES (?, ?, ?, ?, ?)";
            Object[][] params = new Object[orderItemList.size()][];
            int i = 0;
            for (OrderItem orderItem : orderItemList) {
                //处理 util.data 转换为 sql.time
                params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(), orderItem.getSubtotal(), orderItem.getOrder().getOid(), orderItem.getBook().getBid()};
                i++;
            }
            qr.batch(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 设置订单的状态
     * @param state 修改成的状态
     * @param oid 要修改的订单
     */
    public void updateState(String oid, int state) {
        try {
            String sql = "UPDATE orders SET state = ? WHERE oid = ?";
            qr.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置订单的状态
     * @param oid 要查询的订单
     */
    public int getStateByOid(String oid) {
        try {
            String sql = "SELECT state FROM orders WHERE oid = ?";
            return (Integer) qr.query(sql, new ScalarHandler(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据用户 id 查询全部订单信息
     * @param uid 用户的 id
     * @return 全部订单信息
     */
    public List<Order> findByUid(String uid) {
        try {
            //通过 uid 查询所有 list
            String sql = "SELECT * FROM orders WHERE uid = ? ORDER BY state DESC, ordertime DESC";
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class), uid);

            //遍历每个 order 获取所有条目信息
            for (Order order : orderList) {
                loadOrderItems(order);
            }

            //返回订单列表
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据用订单 id 查询订单信息
     * @param oid 订单 id
     * @return 订单信息
     */
    public Order findByOid(String oid) {
        try {
            //通过 oid 查询订单
            String sql = "SELECT * FROM orders WHERE oid = ?";
            Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);

            //给订单加载全部条目信息然后返回
            loadOrderItems(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为单个订单加载其全部条目信息
     * @param order 订单
     */
    private void loadOrderItems(Order order) {

        try {
            //查询两张表获取订单条目信息
            String sql = "SELECT * FROM orderitem i, book b WHERE oid = ? AND i.bid = b.bid";
            //使用 mapListHandler 处理两张表的条目信息
            //每个 map 对应一行结果集
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());

            List<OrderItem> orderItemList = new LinkedList<>();

            //每个 map 生成两个对象，item 和 book，并建立关系
            //然后存储在 orderItemList 中
            for (Map<String, Object> map : mapList) {
                OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
                Book book = CommonUtils.toBean(map, Book.class);
                orderItem.setBook(book);
                orderItemList.add(orderItem);
            }
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
