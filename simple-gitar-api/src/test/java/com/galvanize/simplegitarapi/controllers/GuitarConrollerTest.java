package com.galvanize.simplegitarapi.controllers;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GuitarController.class)
public class GuitarConrollerTest {//4

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuitarService guitarService;

    @Test
    public void getGuitarByModel_shouldReturnsGitarDetails() throws Exception{
        given(guitarService.getSelectedGuitarByModel(anyString())).willReturn(new Guitar(3l,"Guild","D45Bld", 7));
        mockMvc.perform(MockMvcRequestBuilders.get("/gitars/c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("model").value("Guild"))
                .andExpect(jsonPath("brand").value("D45Bld"))
                .andExpect(jsonPath("strings").value(7))
                .andExpect(jsonPath("id").value(3l));
    }


    @Test
    public void getGuitar_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarByModel(anyString())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/gitars/Guild"))
                .andExpect(status().isNotFound());
    }

    }
