package user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import user.domain.User;

import java.sql.SQLException;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-01 - 11:51
 * user 持久层
 */
public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 按用户名查询
     * @param username 要查询的用户名
     * @return 如果找到，返回 User 对象
     */
    public User findByUserName(String username) {
        try {
            String sql = "SELECT * FROM tb_user where username = ?";

            //此处调用了自己编写的 TxQueryRunner 的查询方法
            //TxQueryRunner 继承了 DBUtils.QueryRunner
            //无需调用者提供数据库连接，TxQueryRunner 会使用 JdbcUtils 的 getConnection() 方法
            //然后再调用 DBUtils.QueryRunner 的对应方法
            //JbcUtils 的 getConnection() 方法会从 C3P0 连接池中拿到连接，该类还支持为不同线程提供连接，和事务处理
            //C3P0 的配置文件已在 src 下给出，可以自行修改
            return qr.query(sql, new BeanHandler<>(User.class), username);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按邮箱查询
     * @param email 要查询的邮箱
     * @return 如果找到，返回 User 对象
     */
    public User findByEmail(String email) {
        try {
            String sql = "SELECT * FROM tb_user where email = ?";
            return qr.query(sql, new BeanHandler<>(User.class), email);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按激活码查询
     * @param code 要查询的激活码
     * @return 如果找到，返回 User 对象
     */
    public User findByCode(String code) {
        try {
            String sql = "SELECT * FROM tb_user where code = ?";
            return qr.query(sql, new BeanHandler<>(User.class), code);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加 User 信息到 tb_user 表中
     * @param user 要添加的 User 对象
     */
    public void add(User user) {
        try {
            String sql = "INSERT INTO tb_user VALUES (?, ?, ?, ?, ?, ?)";
            Object[] params = {user.getUid(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCode(), user.isState()};
            qr.update(sql, params);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改指定用户的状态
     * @param uid 要操作的 User 对象的 id
     * @param state 要修改的状态
     */
    public void updateState(String uid, boolean state) {
        try {
            String sql = "UPDATE tb_user SET state = ? WHERE uid = ?";
            qr.update(sql, state, uid);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

