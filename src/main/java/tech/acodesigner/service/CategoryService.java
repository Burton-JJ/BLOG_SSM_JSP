package tech.acodesigner.service;

import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.dto.OperationResult;
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
public interface CategoryService {

    public List<CategoryDto> getCategories();

    public OperationResult<Category> getCategory(int categoryId);

    public OperationResult saveCategory(String categoryName);

    public OperationResult updateCategory(Category category);

    public OperationResult deleteCategory(int categoryId);

}
