package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapperUtility categoryMapperUtility;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapperUtility categoryMapperUtility
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapperUtility = categoryMapperUtility;
    }

    @Override
    public List<Category> findAllBy(String name) {
        return categoryRepository.findAllByName(name);
    }

    @Override
    public CategoryList findAll() {
        return categoryMapperUtility.convertToCategoryList(categoryRepository.findAll());
    }
}
