package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.exceptions.DeleteException;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllBy(String name) {
        return categoryRepository.findAllByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Category findOneByName(String name) {
        return categoryRepository.findOneByName(name);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseGet(Category::new);
    }

    @Override
    public Category updateCategory(String newCategoryName, Long categoryToSet) {
        Category category = findById(categoryToSet);
        category.setName(newCategoryName);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        Category categoryToDelete = findById(id);
        try {
            categoryRepository.delete(categoryToDelete);
        } catch (DataIntegrityViolationException sql) {
            throw new DeleteException("Could not delete category");
        }
    }
}
