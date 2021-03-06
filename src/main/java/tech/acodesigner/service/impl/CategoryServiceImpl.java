package tech.acodesigner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.acodesigner.dao.CategoryDao;
import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Category;
import tech.acodesigner.service.CategoryService;

import java.util.List;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryDao.getCategories();
    }

    @Override
    public OperationResult<Category> getCategory(int categoryId) {
        OperationResult<Category> or = new OperationResult<Category>();
        Category category = categoryDao.getCategoryByCategoryId(categoryId);
        if (category == null) {
            or.setSuccess(false);
            or.setInfo("该分类不存在");
        } else {
            or.setSuccess(true);
            or.setData(category);
        }
        return or;
    }

    @Override
    public OperationResult saveCategory(String categoryName) {
        OperationResult or = new OperationResult();
        int result = categoryDao.saveCategory(categoryName);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("添加失败");
        } else {
            or.setSuccess(true);
            or.setInfo("添加成功");
        }
        return or;
    }

    @Override
    public OperationResult updateCategory(Category category) {
        OperationResult or = new OperationResult();
        int result = categoryDao.updateCategory(category);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("修改失败");
        } else {
            or.setSuccess(true);
            or.setInfo("修改成功");
        }
        return or;
    }

    @Override
    public OperationResult deleteCategory(int categoryId) {
        OperationResult or = new OperationResult();
        int result = categoryDao.deleteCategory(categoryId);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("删除失败");
        } else {
            or.setSuccess(true);
            or.setInfo("删除成功");
        }
        return or;
    }
}
