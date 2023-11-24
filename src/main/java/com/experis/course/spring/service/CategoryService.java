package com.experis.course.spring.service;

import com.experis.course.spring.exception.CategoryNameUniqueException;
import com.experis.course.spring.exception.CategoryNotFoundException;
import com.experis.course.spring.model.Category;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.repository.CategoryRepository;
import com.experis.course.spring.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, PhotoRepository photoRepository) {
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) throws CategoryNameUniqueException {
        // Verifico che la categoria non sia duplicata
        if (categoryRepository.existsByName(category.getName())) {
            // Se lo è, lancio un'eccezione passando il nome della categoria
            throw new CategoryNameUniqueException(category.getName());
        }
        // Trasformo il nome in lowercase
        category.setName(category.getName().toLowerCase());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        // Salvo in result in modo Optional perché potrebbe non ritornare nulla
        Optional<Category> result = categoryRepository.findById(id);
        // Se il risultato è presente
        return result.orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + ": Not Found"));
    }

    @Transactional
    public void deleteCategoryWithAssociations(Integer id) {
        // Recupero la categoria tramite ID
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + ": Not Found"));
        // Rimuovo l'associazione tra categoria e foto
        // Ciclo su ogni oggetto foto e rimuovo la categoria
        for (Photo photo : photoRepository.findAll()) {
            photo.getCategories().remove(categoryToDelete);
        }
        // Elimino la categoria
        categoryRepository.delete(categoryToDelete);
    }
}

