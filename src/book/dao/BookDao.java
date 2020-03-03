package book.dao;

import book.domain.Book;
import category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 17:24
 * 图书信息的数据访问层
 */
public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加图书
     * @param book 要添加的图书
     */
    public void add(Book book) {
        try {
            String sql = "INSERT INTO book VALUES(?, ?, ?, ?, ?, ?)";
            Object[] params = {book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 修改图书
     * @param book 修改后的图书对象
     */
    public void modify(Book book) {
        try {
            String sql = "UPDATE book SET bname = ?, price = ?, author = ?, image = ?, cid = ? WHERE bid = ?";
            Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid(), book.getBid()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 删除图书
     * @param bid 要删除的图书 id
     */
    public void delete(String bid) {
        try {
            String sql = "UPDATE book SET del = TRUE WHERE bid = ?";
            qr.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 查询所有图书
     * @return 所有图书的 list 集合
     */
    public List<Book> findAll() {
        try {
            String sql = "SELECT * FROM book WHERE del = FALSE";
            return qr.query(sql, new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 按照分类查询图书
     * @param cid 要查询的分类的 id
     * @return 该分类对应图书的 list 集合
     */
    public List<Book> findByCategory(String cid) {
        try {
            String sql = "SELECT * FROM book where cid = ? AND del = FALSE";
            return qr.query(sql, new BeanListHandler<>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 按照 id 查询图书
     * @param bid 要查询的图书的 id
     * @return 对应的图书对象
     */
    public Book findById(String bid) {
        try {
            String sql = "SELECT * FROM book where bid = ?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);

            return book;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
