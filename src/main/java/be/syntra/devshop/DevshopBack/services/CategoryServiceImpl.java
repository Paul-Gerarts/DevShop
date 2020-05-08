package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAllBy(String name) {
        return categoryRepository.findAllByName(name);
    }

    @Override
    public CategoryList findAll() {
        return categoryMapper.convertToCategoryList(categoryRepository.findAllByOrderByNameAsc());
    }

    @Override
    public Category findOneByName(String name) {
        return categoryRepository.findOneByName(name);
    }
}
