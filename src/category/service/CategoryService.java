package category.service;

import book.dao.BookDao;
import book.domain.Book;
import category.dao.CategoryDao;
import category.domain.Category;
import category.exception.CategoryException;

import java.sql.SQLException;
import java.util.List;

/**
 * @author su houzhen
 * @version 1.0
 * 2020-03-02 - 16:31
 */
public class CategoryService {
    private static CategoryDao categoryDao = new CategoryDao();
    private static BookDao bookDao = new BookDao();

    /**
     * 找到所有分类
     * @return 全部分类的 list 集合
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 添加分类
     * @param category 要添加的分类的对象
     */
    public void add(Category category) {
        categoryDao.add(category);
    }

    /**
     * 修改分类
     * @param category 要添加的分类的对象
     */
    public void modify(Category category) {
        categoryDao.modify(category);
    }

    /**
     * 删除分类
     * @param cid 分类的 id
     */
    public void delete(String cid) throws CategoryException {
        List<Book> bookList = bookDao.findByCategory(cid);
        if(!bookList.isEmpty()) throw new CategoryException("该分类下还有图书，不能删除！");

        categoryDao.deleteByCid(cid);
    }

    /**
     * 加载
     * @param cid 分类的 id
     * @return 加载成功的分类
     */
    public Category load(String cid) throws CategoryException {
        return  categoryDao.findByCid(cid);
    }
}
