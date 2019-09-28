package com.galvanize.simplegitarapi.controllers;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guitars")
public class GuitarController {//5

    @Autowired
    private GuitarService guitarService;

    @GetMapping("/model/{model}")
    private Guitar getGuitarByModel(@PathVariable String model){
        return guitarService.getSelectedGuitarByModel(model);
    }

    @GetMapping("/{id}")
    private Guitar getGuitarById(@PathVariable Long id){
        return guitarService.getSelectedGuitarById(id);
    }

//    @GetMapping("/{id}/{model}")
//    private Guitar getGuitarById(@RequestParam String id, )

    @GetMapping
    private List<Guitar> getAllGuitars(){
        return guitarService.getAllGuitarGitarsDetails();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void GuitarNotFoundHandler(GuitarNotFoundException e){}
}
