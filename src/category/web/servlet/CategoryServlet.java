package category.web.servlet;

import category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 16:31
 */
@WebServlet(name = "categoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    private static CategoryService categoryService = new CategoryService();

    /**
     * 查询所有分类
     * @param request 当前请求
     * @param response 当前响应
     * @return 转发到 jsps/left.jsp
     * @throws Exception 异常
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/jsps/left.jsp";
    }
}
