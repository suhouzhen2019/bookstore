package user.web.filter;

import user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-03 - 19:21
 */
@WebFilter(filterName = "userFilter", servletNames = {"cartServlet", "orderServlet"}, urlPatterns = {"/jsps/cart/*", "/jsps/order/*"})
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //从 session 中获取用户信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        User user = (User) request.getSession().getAttribute("session_user");

        if(user != null) filterChain.doFilter(servletRequest, servletResponse);
        else {
            request.setAttribute("msg","您还没有登陆！");
            request.getRequestDispatcher("/jsps/msg.jsp").forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
