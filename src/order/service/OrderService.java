package order.service;

import cn.itcast.jdbc.JdbcUtils;
import order.dao.OrderDao;
import order.domain.Order;
import order.exception.OrderException;

import java.sql.SQLException;
import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 21:02
 */
public class OrderService {
    OrderDao orderDao = new OrderDao();

    /**
     * 添加订单，需要处理事务
     * @param order 添加的订单
     */
    public void add(Order order) {
        try {
            //开启事务
            JdbcUtils.beginTransaction();
            orderDao.addOrder(order);
            orderDao.addOrderItemList(order.getOrderItemList());

            //提交事务
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            //回滚事务
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得当前用户的所有订单
     * @param uid 用户的 id
     */
    public List<Order> myOrders(String uid) {
        return orderDao.findByUid(uid);
    }

    /**
     * 根据订单 id 加载订单信息
     * @param oid 订单 id
     * @return 订单信息
     */
    public Order load(String oid) {
        return orderDao.findByOid(oid);
    }

    /**
     * 根据订单 id 确认收货
     * @param oid 订单 id
     * @return 订单信息
     */
    public void confirm(String oid) throws OrderException {
        //校验订单状态，如果不是 3，抛出异常
        int state = orderDao.getStateByOid(oid);
        if(state != 3) throw new OrderException("该订单未发货!");

        //如果发货则确认收货
        orderDao.updateState(oid, 4);
    }
}
