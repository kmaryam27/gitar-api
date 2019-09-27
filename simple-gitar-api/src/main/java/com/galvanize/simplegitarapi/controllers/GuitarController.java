package com.galvanize.simplegitarapi.controllers;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gitars")
public class GuitarController {//5

    @Autowired
    private GuitarService guitarService;

    @GetMapping("/{model}")
    private Guitar getGuitarByModel(@PathVariable String model){
        return guitarService.getSelectedGuitarByModel(model);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void GuitarNotFoundHandler(GuitarNotFoundException e){}

}
