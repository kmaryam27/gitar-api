package com.galvanize.simplegitarapi.services;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuitarService {//6

//    @Autowired
    private GuitarRepository guitarRepository;

    public GuitarService(GuitarRepository guitarRepository) {//injecting repository to service
        this.guitarRepository = guitarRepository;
    }

    public Guitar getSelectedGuitarByModel(String model) {
        Guitar guitar = guitarRepository.findByModel(model);
        if (guitar == null) throw new GuitarNotFoundException();
        return guitar;
    }
}
