package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository)
    {
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
    public Category updateCategory(CategoryChangeDto categoryChangeDto) {
        Category category = findById(categoryChangeDto.getCategoryToSet());
        category.setName(categoryChangeDto.getNewCategoryName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        Category categoryToDelete = findById(id);
        categoryRepository.delete(categoryToDelete);
    }
}
