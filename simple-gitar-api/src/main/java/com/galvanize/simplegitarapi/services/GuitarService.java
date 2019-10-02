package com.galvanize.simplegitarapi.services;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuitarService {//6

    /*Injecting repository to service*/
    private GuitarRepository guitarRepository;
    public GuitarService(GuitarRepository guitarRepository) {
        this.guitarRepository = guitarRepository;
    }

    /**
     * search by Model
     * @param model
     * @return
     */
    public Guitar getSelectedGuitarByModel(String model) {
        Guitar guitar = guitarRepository.findByModel(model);
        if (guitar == null) throw new GuitarNotFoundException();
        return guitar;
    }

    /**
     * search by id
     * @param id
     * @return
     */
    public Guitar getSelectedGuitarById(Long id) {
        if (guitarRepository.findById(id) == null) throw new GuitarNotFoundException();
        return guitarRepository.findById(id).get();
    }

    /**
     * get all guitar objects from db
     * @return
     */
    public List<Guitar> getAllGuitarGitarsDetails() {
        return guitarRepository.findAll();
    }

    /**
     * add new guitar object to db
     * @param guitar
     * @return
     */
    public Guitar addNewGuitarInstance(Guitar guitar) {
        return guitarRepository.save(guitar);
    }
}
