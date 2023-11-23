package com.experis.course.spring.controller;

import com.experis.course.spring.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping
    public String listIndex (@RequestParam Optional<String> search, Model model) {
        model.addAttribute("listPhoto", photoService.GetPhoto(search));
        return "admin/list";
    }


}
