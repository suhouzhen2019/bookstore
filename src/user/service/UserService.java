package user.service;

import user.exception.UserException;

import user.dao.UserDao;
import user.domain.User;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-01 - 11:53
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户注册
     * @param form 用户表单信息
     * @throws UserException 注册失败
     */
    public void regist(User form) throws UserException {
        //校验用户名
        User user = userDao.findByUserName(form.getUsername());
        if(user != null) throw new UserException("用户名已被注册！");

        //校验用户名
        user = userDao.findByEmail(form.getEmail());
        if(user != null) throw new UserException("邮箱已被注册！");

        //校验成功
        userDao.add(form);
    }

    /**
     * 激活用户
     * @param code 用户的激活码
     * @throws UserException 异常
     */
    public void active(String code) throws UserException {
        //使用 code 查询数据库，得到 User
        User user = userDao.findByCode(code);

        //如果用户不存在或者已经激活
        if(user == null) throw new UserException("激活码无效！");
        if(user.isState()) throw new UserException("您已经激活过了！");

        //否则进行激活
        userDao.updateState(user.getUid(), true);
    }

    public User login(User form) throws UserException {
        User user = userDao.findByUserName(form.getUsername());
        if(user == null) throw new UserException("用户名不存在！");
        if(!form.getPassword().equals(user.getPassword())) throw new UserException("密码错误！");
        if(!user.isState()) throw new UserException("您还未激活！");

        return user;
    }

}
