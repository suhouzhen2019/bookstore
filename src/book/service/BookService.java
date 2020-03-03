package book.service;

import book.dao.BookDao;
import book.domain.Book;

import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 17:24
 * 图书信息的业务逻辑层
 */
public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     * @return 所有图书的 list 集合
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * 按照分类查询图书
     * @param cid 要查询的分类的 id
     * @return 该分类对应图书的 list 集合
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    /**
     * 按照 id 查询图书
     * @param bid 要查询的图书的 id
     * @return 对应的图书对象
     */
    public Book load(String bid) {
        return bookDao.findById(bid);
    }

    /**
     * 添加图书
     * @param book 要添加的图书
     */
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * 删除图书
     * @param bid 要删除的图书编号
     */
    public void delete(String bid) {
        bookDao.delete(bid);
    }

    /**
     * 删除图书
     * @param book 修改后的图书对象
     */
    public void modify(Book book) {
        bookDao.modify(book);
    }
}
