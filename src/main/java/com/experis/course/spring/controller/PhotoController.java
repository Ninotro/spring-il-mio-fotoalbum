package com.experis.course.spring.controller;

import com.experis.course.spring.exception.PhotoNotFoundException;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping
    public String listIndex(@RequestParam Optional<String> search, Model model) {
        model.addAttribute("listPhoto", photoService.GetPhoto(search));
        return "admin/list";
    }

    @GetMapping("/show/{id}")

    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            model.addAttribute("photo", photo);


            return "admin/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/create")
    public String createGet(Model model) {

        model.addAttribute("photo", new Photo());

        return "admin/create_update";
    }


    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {
        // Controllo se ci sono errori
        if (bindingResult.hasErrors()) {

            // Se ci sono ricarico la pagina mantendendo i dati (grazie al model)
            return "admin/create_update";
        }

        Photo savedPhoto = photoService.createPhoto(formPhoto);
        return "redirect:/photos/show/" + savedPhoto.getId();
    }
}
