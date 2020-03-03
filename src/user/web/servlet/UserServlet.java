package user.web.servlet;

import cart.domain.Cart;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import user.domain.User;
import user.service.UserService;
import user.exception.UserException;

import javax.mail.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-01 - 11:55
 * User 表述层
 */
@WebServlet(name="UserServlet", urlPatterns="/UserServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    /**
     * 用户注册方法
     * @param request 当前的请求
     * @param response 服务器的响应
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //封装表单数据
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);

        //补全数据
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        //输入校验
        Map<String, String> errors = new HashMap<>(); //存储错误信息
        //校验用户名
        String username = form.getUsername();
        if(username == null || username.trim().isEmpty()) errors.put("username", "用户名不能为空！");
        else if(username.length() < 3 || username.length() > 10) errors.put("username", "用户名长度必须在3-10位之间！");
        //校验密码
        String password = form.getPassword();
        if(password == null || password.trim().isEmpty()) errors.put("password", "密码不能为空！");
        else if(password.length() < 8 || password.length() > 16) errors.put("password", "密码长度必须在8-16位之间！");
        //校验邮箱
        String email = form.getEmail();
        if(email == null || email.trim().isEmpty()) errors.put("email", "邮箱不能为空！");
        else if(!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("email", "邮箱格式错误！");
        }

        //如果出错
        //保存错误信息，转发到指定页面
        if(errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        try {
            userService.regist(form);

            //如果注册成功，先给用户发送邮件，从配置文件获取信息
            Properties prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));

            //得到 session
            Session session = MailUtils.createSession(
                    prop.getProperty("host"),
                    prop.getProperty("name"),
                    prop.getProperty("pwd")
            );
            Mail mail = new Mail(
                    prop.getProperty("from"),
                    form.getEmail(),
                    prop.getProperty("subject"),
                    MessageFormat.format(prop.getProperty("content"), form.getCode())//替换占位符{0}为code
            );//创建邮件对象

            try {
                MailUtils.send(session, mail);//发邮件
            } catch(Exception e) {
                e.printStackTrace();
            }

            //保存成功信息，转发到指定页面
            request.setAttribute("msg", "注册成功！请到邮箱激活。");
            return "f:jsps/msg.jsp";
        } catch(UserException e) {
            //如果插入时抛出异常
            //保存错误信息，转发到指定页面
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

    }

    /**
     * 激活用户
     * @param request 当前的请求
     * @param response 服务器的响应
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        try {
            userService.active(code);
            request.setAttribute("msg", "恭喜，激活成功！");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    /**
     * 用户登录
     * @param request 当前的请求
     * @param response 服务器的响应
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);

        //输入校验
        Map<String, String> errors = new HashMap<>(); //存储错误信息
        //校验用户名
        String username = form.getUsername();
        if(username == null || username.trim().isEmpty()) errors.put("username", "用户名不能为空！");
        //校验密码
        String password = form.getPassword();
        if(password == null || password.trim().isEmpty()) errors.put("password", "密码不能为空！");

        if(!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return "f:/jsps/user/login.jsp";
        }

        try {
            User user = userService.login(form);
            request.getSession().invalidate();
            request.getSession().setAttribute("session_user", user);
            request.getSession().setAttribute("cart", new Cart());
            return  "r:/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }

    }

    /**
     * 用户退出
     * @param request 当前的请求
     * @param response 服务器的响应
     */
    public String quit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //把当前的 session 销毁即可
        request.getSession().invalidate();
        return "r:/index.jsp";
    }
}
