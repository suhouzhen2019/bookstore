package book.web.servlet;

import book.service.BookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 17:23
 * 管理图书信息的服务器
 */
@WebServlet(name = "bookServlet", urlPatterns = "/BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * 查询所有分类
     * @param request 当前请求
     * @param response 当前响应
     * @return 转发到 jsps/left.jsp
     * @throws Exception 异常
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("bookList", bookService.findAll());
        return "f:jsps/book/list.jsp";
    }

    /**
     * 按照分类查询图书
     * @param request 当前请求
     * @param response 当前响应
     * @return 转发到 jsps/left.jsp
     * @throws Exception 异常
     */
    public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("bookList", bookService.findByCategory(request.getParameter("cid")));
        return "f:jsps/book/list.jsp";
    }

    /**
     * 根据图书编号加载详细信息
     * @param request 当前请求
     * @param response 当前响应
     * @return 转发到 jsps/left.jsp
     * @throws Exception 异常
     */
    public String load(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("book", bookService.load(request.getParameter("bid")));
        return "f:jsps/book/desc.jsp";
    }
}
