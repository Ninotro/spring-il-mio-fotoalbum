package com.experis.course.spring.controller;

import com.experis.course.spring.exception.CategoryNameUniqueException;
import com.experis.course.spring.exception.CategoryNotFoundException;
import com.experis.course.spring.model.Category;
import com.experis.course.spring.repository.UserRepository;
import com.experis.course.spring.security.DatabaseUserDetails;
import com.experis.course.spring.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
@Autowired
CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;


@GetMapping
    public String index(Model model, Authentication authentication) {
        DatabaseUserDetails userDetails = (DatabaseUserDetails) authentication.getPrincipal();

        // Verifica se l'utente è SUPERADMIN
        if (userDetails.isSuperAdmin()) {
            model.addAttribute("categoryList", categoryService.getAll());
            model.addAttribute("categoryObj", new Category());
            return "categories/list";
        } else {
            // Messaggio di accesso negato
            model.addAttribute("message", "Accesso negato. Solo i SUPERADMIN possono creare categorie.");
            return "error/error";
        }
    }


    @PostMapping
    public String create(@Valid @ModelAttribute("categoryObj") Category formCategory, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
               if (bindingResult.hasErrors()) {
                   model.addAttribute("categoryList", categoryService.getAll());
            return "categories/list";
        }
        try {
            categoryService.save(formCategory);
            redirectAttributes.addFlashAttribute("message", "Categoria '" + formCategory.getName() + "' creata correttamente!");
            return "redirect:/categories";
        } catch (CategoryNameUniqueException e) {
            redirectAttributes.addFlashAttribute("messageFailed", "La categoria '" + formCategory.getName() + "' esiste già");
            return "redirect:/categories";
        }
    }


    @PostMapping("/delete/{id}")

    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Category categoryToDelete = categoryService.getCategoryById(id);
            categoryService.deleteCategoryWithAssociations(id);
            redirectAttributes.addFlashAttribute("message", "Categoria '" + categoryToDelete.getName() + "' eliminata!");
            return "redirect:/categories";
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}