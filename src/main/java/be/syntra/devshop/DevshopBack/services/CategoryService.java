package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllBy(String name);

    Category findOneByName(String name);

    List<Category> findAll();

    Category findById(Long id);

    Category updateCategory(String newCategoryName, Long categoryToSet);

    void delete(Long id);
}
