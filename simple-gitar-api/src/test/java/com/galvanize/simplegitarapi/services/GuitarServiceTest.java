package com.galvanize.simplegitarapi.services;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GuitarServiceTest {//7

    @Mock
    GuitarRepository guitarRepository;

    private GuitarService guitarService;



    @Before
    public void setUp() throws Exception {
        guitarService = new GuitarService(guitarRepository);
    }

    @Test
    public void getSelectedGuitarByModel() {
        given(guitarRepository.findByModel(anyString())).willReturn(new Guitar(3l,"Guild","D45Bld", 7));
        Guitar guitar = guitarService.getSelectedGuitarByModel("Guild");
        assertThat(guitar.getId()).isNotNull();
        assertThat(guitar.getStrings()).isEqualTo(7);
        assertThat(guitar.getBrand()).isEqualTo("D45Bld");
        assertThat(guitar.getModel()).isEqualTo("Guild");
    }

    @Test(expected = GuitarNotFoundException.class)
    public void getSelectedGuitarByModel_whenGitarNotFound()throws Exception{
        given(guitarRepository.findByModel(anyString())).willReturn(null);
        guitarService.getSelectedGuitarByModel("Guild");
    }
}