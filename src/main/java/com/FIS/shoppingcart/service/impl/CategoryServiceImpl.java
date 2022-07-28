package com.FIS.shoppingcart.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.FIS.shoppingcart.dao.CategoryRepository;
import com.FIS.shoppingcart.entities.Category;
import com.FIS.shoppingcart.model.CategoriesDTO;
import com.FIS.shoppingcart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Qualifier("categoryRepository")
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String saveCategory(Category category) {
        // TODO Auto-generated method stub
        category.setActive(true);
        categoryRepository.saveAndFlush(category);
        return "Save Sucessfully";
    }

    @Override
    public List<Category> findAllCategories() {
        // TODO Auto-generated method stub

        List<Category> categories = new ArrayList<>();
        List<Category> categoriesCopy = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        categoriesCopy.addAll(categories);

        return categoriesCopy;

    }

    @Override
    public Category findCategoryById(Integer id) {
        // TODO Auto-generated method stub
        Category category = categoryRepository.findById(id).get();
        if (!category.isActive()) {
            category = null;
        }
        return category;
    }

    @Override
    public String deleteCategory(Integer id) {
        // TODO Auto-generated method stub
        Category c = categoryRepository.findById(id).get();
        c.setActive(false);
        categoryRepository.saveAndFlush(c);
        return "Deleted Successfully";
    }

    @Override
    public String updateCategory(Category category) {
        // TODO Auto-generated method stub
        categoryRepository.saveAndFlush(category);
        return "Updated Successfully";
    }

}
