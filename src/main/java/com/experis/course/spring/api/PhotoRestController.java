package com.experis.course.spring.api;

import com.experis.course.spring.exception.PhotoNotFoundException;
import com.experis.course.spring.model.Photo;
import com.experis.course.spring.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/photos")
@CrossOrigin
public class PhotoRestController {

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List  <Photo> index() {

        return photoService.GetPhoto(Optional.of(""));

    }

//    endpoint per i dettagli del libro preso per id

    @GetMapping ("/{id}")
    public Photo details (@PathVariable Integer id) {

        try {
            return photoService.getPhotoById(id);
        }
        catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping
    public Photo create(@Valid @RequestBody Photo photo) {

        return photoService.createPhoto(photo);


    }
}




