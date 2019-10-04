package com.galvanize.simplegitarapi.controllers;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/guitars")
public class GuitarController {//5

    @Autowired
    private GuitarService guitarService;

    /**
     * find a Gitar by uniqu model
     * @param model
     * @return
     */
    @GetMapping("/model/{model}")
    private Guitar getGuitarByModel(@PathVariable String model){
        return guitarService.getSelectedGuitarByModel(model);
    }

    /**
     * find a Gitar by uniqu Id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    private Guitar getGuitarById(@PathVariable Long id){
        return guitarService.getSelectedGuitarById(id);
    }

    /**
     * get all Gitars
     * @return
     */
    @GetMapping
    private List<Guitar> getAllGuitars(){
        return guitarService.getAllGuitarGitarsDetails();
    }

    /* because for test we have to use variable of service only autowiring does not work*/
    public GuitarController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    /**
     * Add new Guitar
     * @param guitar
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guitar createGuitar(@RequestBody @Valid Guitar guitar) {
            return guitarService.addNewGuitarInstance(guitar);
    }

    /**
     * Update a guitar entity
     * @param id
     * @param guitar
     * @return
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Guitar updateGuitar(@PathVariable Long id, @RequestBody @Valid Guitar guitar) {
        return guitarService.updateSelectedGuitarById(id, guitar);
    }

    /**
     * Exception Handler
     * @param e
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void GuitarNotFoundHandler(GuitarNotFoundException e){}
}
