package tech.acodesigner.dao;

import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.entity.Category;

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
public interface CategoryDao {

    public List<CategoryDto> getCategories();

    public Category getCategoryByCategoryId(int categoryId);

    public int saveCategory(String categoryName);

    public int updateCategory(Category category);

    public int deleteCategory(int categoryId);


}
