package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;
import be.syntra.devshop.DevshopBack.models.CategoryList;

import java.util.List;

public interface CategoryService {

    List<Category> findAllBy(String name);

    Category findOneByName(String name);

    CategoryList findAll();

    Category findById(Long id);

    Category updateCategory(CategoryChangeDto categoryChangeDto);

    void delete(Long id);
}
