package com.experis.course.spring.service;

import com.experis.course.spring.exception.PhotoNotFoundException;
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







    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);

        if (result.isPresent()) {

            return result.get();
        } else {

            throw new PhotoNotFoundException("Photo with ID " + id + ": Not Found");
        }
    }

    public Photo createPhoto(Photo photo) throws RuntimeException {
        return photoRepository.save(photo);
    }

    public Photo editPhoto(Photo photo) throws PhotoNotFoundException {
        Photo photoToEdit = getPhotoById(photo.getId());
        photoToEdit.setTitle(photo.getTitle());
        photoToEdit.setDescription(photo.getDescription());
        photoToEdit.setUrl(photo.getUrl());
        photoToEdit.setVisible(photo.isVisible());
        photoToEdit.setCategories(photo.getCategories());
        return photoRepository.save(photoToEdit);
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }
}
