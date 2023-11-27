package com.experis.course.spring.controller;

import com.experis.course.spring.exception.PhotoNotFoundException;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.repository.UserRepository;
import com.experis.course.spring.security.DatabaseUserDetails;
import com.experis.course.spring.service.CategoryService;
import com.experis.course.spring.service.PhotoService;
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

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;



@GetMapping
    String listIndex(@RequestParam Optional<String> search, Authentication authentication, Model model) {
        DatabaseUserDetails userDetails = (DatabaseUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        // Verifica se l'utente è SUPERADMIN
        if (userDetails.isSuperAdmin()) {
            model.addAttribute("listPhoto", photoService.GetPhoto(search, userId, true));
        } else {
            model.addAttribute("listPhoto", photoService.GetPhoto(search, userId, false));
        }

        return "admin/list";
    }

    @GetMapping("/show/{id}")

    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            model.addAttribute("photo", photo);
            model.addAttribute("categoryList", photo.getCategories());


            return "admin/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/create")
    public String createGet(Model model, Authentication authentication) {
        DatabaseUserDetails userDetails = (DatabaseUserDetails) authentication.getPrincipal();

        // Verifica se l'utente è ADMIN
        if (userDetails.isAdmin()) {
            model.addAttribute("photo", new Photo());
            model.addAttribute("categoryList", categoryService.getAll());
            return "admin/create_update";
        } else {
            // Messaggio di accesso negato
            model.addAttribute("message", "Accesso negato. Solo gli ADMIN possono creare foto.");
            return "error";
        }
    }



    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {
        // Controllo se ci sono errori
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());

            // Se ci sono ricarico la pagina mantendendo i dati (grazie al model)
            return "admin/create_update";
        }

        Photo savedPhoto = photoService.createPhoto(formPhoto);
        return "redirect:/photos/show/" + savedPhoto.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable Integer id, Model model) {
        try {
            // Passo la photo con il model
            model.addAttribute("photo", photoService.getPhotoById(id));
            model.addAttribute("categoryList", categoryService.getAll());
            return "admin/create_update";
        } catch (PhotoNotFoundException e) {
            // Altrimenti lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping("/edit/{id}")
    public String editPost(
            @PathVariable Integer id,
            @Valid @ModelAttribute("photo") Photo formPhoto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());
            return "/admin/create_update";
        }
        try {
            Photo savedPhoto = photoService.editPhoto(id, formPhoto);
            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Provo a prendere photo in base a id
            Photo photoToDelete = photoService.getPhotoById(id);
            photoService.deletePhoto(id);
            redirectAttributes.addFlashAttribute("message", "Foto '" + photoToDelete.getTitle() + "' eliminata correttamente!");
            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
