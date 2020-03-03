package cart.web.servlet;

import book.domain.Book;
import book.service.BookService;
import cart.domain.Cart;
import cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 19:30
 */
@WebServlet(name = "cartServlet", urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {

    /**
     * 添加购物条目
     * @param request 当前请求
     * @param response 当前相应
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //得到购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        //得到图书和数量
        String bid = (String) request.getParameter("bid");
        Book book = new BookService().load(bid);
        int count = Integer.parseInt(request.getParameter("count"));

        //设置条目并添加到购物车
        CartItem cartItem = new CartItem(book, count);
        cart.add(cartItem);
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 清空购物车
     * @param request 当前请求
     * @param response 当前相应
     */
    public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //得到购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        //清空
        cart.clear();
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 删除购物条目
     * @param request 当前请求
     * @param response 当前相应
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        //得到购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        //得到要删除的条目对应的图书 id
        String bid = request.getParameter("bid");

        //删除
        cart.delete(bid);
        return "f:/jsps/cart/list.jsp";
    }
}
