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

    /***************************find a Gitar by uniqu model*********************************/
    @GetMapping("/model/{model}")
    private Guitar getGuitarByModel(@PathVariable String model){
        return guitarService.getSelectedGuitarByModel(model);
    }

    /***************************find a Gitar by uniqu Id*********************************/
    @GetMapping("/{id}")
    private Guitar getGuitarById(@PathVariable Long id){
        return guitarService.getSelectedGuitarById(id);
    }

    /***************************get all Gitars *********************************/
    @GetMapping
    private List<Guitar> getAllGuitars(){
        return guitarService.getAllGuitarGitarsDetails();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void GuitarNotFoundHandler(GuitarNotFoundException e){}
}
