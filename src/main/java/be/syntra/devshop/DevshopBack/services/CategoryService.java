package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;

import java.util.List;

public interface CategoryService {

    List<Category> findAllBy(String name);

    CategoryList findAll();
}
