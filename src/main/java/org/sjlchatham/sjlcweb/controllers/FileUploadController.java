package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.storage.StorageFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "storage")
public class FileUploadController {

    /*
     *  TODO
     */

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException fileNotFoundException) {
        return ResponseEntity.notFound().build();
    }

}
