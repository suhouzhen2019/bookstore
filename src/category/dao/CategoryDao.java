package category.dao;

import category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 16:30
 */
public class CategoryDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 查询所有分类
     * @return 所有分类的集合
     */
    public List<Category> findAll() {
        try {
            String sql = "SELECT * FROM category";
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 查询指定分类
     * @return 指定分类信息
     */
    public Category findByCid(String cid) {
        try {
            String sql = "SELECT * FROM category WHERE cid = ?";
            return qr.query(sql, new BeanHandler<>(Category.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 添加分类
     */
    public void add(Category category) {
        try {
            String sql = "INSERT INTO category VALUES (?, ?)";
            qr.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 修改分类
     */
    public void modify(Category category) {
        try {
            String sql = "UPDATE category SET cname = ? WHERE cid = ?";
            qr.update(sql, category.getCname(), category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 删除分类
     * @param cid 要删除的分类的 id
     */
    public void deleteByCid(String cid) {
        try {
            String sql = "DELETE FROM category WHERE cid = ?";
            qr.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
