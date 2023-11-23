package com.experis.course.spring.service;

import com.experis.course.spring.model.Photo;
import com.experis.course.spring.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    public List <Photo> GetPhoto (Optional <String> search) {
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        }

        else
            return photoRepository.findAll();
    }
}
