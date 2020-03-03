package book.admin.web.servlet;

import book.domain.Book;
import book.service.BookService;
import category.domain.Category;
import category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-03 - 15:44
 */
@WebServlet(name = "adminBookServlet", urlPatterns = "/Admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    BookService bookService = new BookService();
    CategoryService categoryService = new CategoryService();

    /**
     * 查询所有图书
     * @param request 当前请求
     * @param response 当前响应
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("bookList", bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }

    /**
     * 加载图书
     * @param request 当前请求
     * @param response 当前响应
     */
    public String load(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取当前图书，保存到 request
        String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.load(bid));

        //获取所有分类，保存到 request
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    /**
     * 删除图书
     * @param request 当前请求
     * @param response 当前响应
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取当前图书，保存到 request
        String bid = request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request,response);
    }

    /**
     * 修改图书
     * @param request 当前请求
     * @param response 当前响应
     */
    public String modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        book.setCategory(category);

        bookService.modify(book);
        return findAll(request,response);
    }

    /**
     * 添加图书预处理
     * @param request 当前请求
     * @param response 当前响应
     */
    public String addPre(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取所有分类，保存到 request
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }

}
