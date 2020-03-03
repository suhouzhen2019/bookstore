package order.web.servlet;

import cart.domain.Cart;
import cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;
import order.domain.Order;
import order.domain.OrderItem;
import order.exception.OrderException;
import order.service.OrderService;
import user.domain.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 21:02
 */
@WebServlet(name = "orderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    OrderService orderService = new OrderService();

    /**
     * 添加订单
     * @param request 当前请求
     * @param response 当前相应
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //从 session 中获得购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        //封装成 order 对象
        Order order = new Order();
        order.setOid(CommonUtils.uuid());
        order.setOrderTime(new Date());
        order.setState(1);
        order.setUser((User) request.getSession().getAttribute("session_user"));
        order.setTotal(cart.getTotal());

        //创建订单条目集合
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem item = new OrderItem();
            item.setIid(CommonUtils.uuid());
            item.setBook(cartItem.getBook());
            item.setCount(cartItem.getCount());
            item.setSubtotal(cartItem.getTotal());
            item.setOrder(order);

            orderItemList.add(item);
        }
        //把条目集合添加到 order 中
        order.setOrderItemList(orderItemList);

        //生成订单
        orderService.add(order);

        //清空购物车
        cart.clear();

        //保存 order 到 request
        request.setAttribute("order", order);
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * 查询当前用户的所有订单
     * @param request 当前请求
     * @param response 当前相应
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //从 session 中获得当前用户
        User user = (User) request.getSession().getAttribute("session_user");
        //使用 uid 查询全部订单信息
        List<Order> orders = orderService.myOrders(user.getUid());
        //保存 orders 到 request
        request.setAttribute("orders", orders);
        return "f:/jsps/order/list.jsp";
    }

    /**
     * 加载订单
     * @param request 当前请求
     * @param response 当前相应
     */
    public String load(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        Order order = orderService.load(oid);
        request.setAttribute("order", order);
        return "f:/jsps/order/desc.jsp";
    }
    /**
     * 确认收货
     * @param request 当前请求
     * @param response 当前相应
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "交易成功！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }
}
