package category.admin.web.servlet;

import category.domain.Category;
import category.exception.CategoryException;
import category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-03 - 13:44
 * 管理员专用分类过滤器
 */
@WebServlet(name = "adminCategoryServlet", urlPatterns = "/Admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * 查询所有分类
     * @param request 当前请求
     * @param response 当前响应
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }

    /**
     * 添加分类
     * @param request 当前请求
     * @param response 当前响应
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //封装分类，并补全 id
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());

        categoryService.add(category);

        return findAll(request, response);
    }

    /**
     * 删除分类
     * @param request 当前请求
     * @param response 当前响应
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取 cid
        String cid = request.getParameter("cid");

        //删除 cid
        try {
            categoryService.delete(cid);
            return findAll(request, response);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/admin/msg.jsp";
        }
    }

    /**
     * 修改分类准备
     * @param request 当前请求
     * @param response 当前响应
     */
    public String editPre(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取 cid
        String cid = request.getParameter("cid");

        //获取分类信息
        request.setAttribute("category", categoryService.load(cid));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    /**
     * 修改分类
     * @param request 当前请求
     * @param response 当前响应
     * @return 转发到 jsps/left.jsp
     * @throws Exception 异常
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //封装表单数据
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        //调用修改方法
        categoryService.modify(category);
        return findAll(request, response);
    }

}
