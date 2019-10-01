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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GuitarController.class)
public class GuitarConrollerTest {//4

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuitarService guitarService;

    /*****************************getGuitarByModel************************************/
    @Test
    public void getGuitarByModel_shouldReturnsGitarDetails() throws Exception{
        Guitar guitar = new Guitar("Guild","D45Bld", 7);
        guitar.setId(3l);
        given(guitarService.getSelectedGuitarByModel(anyString())).willReturn(guitar);

        mockMvc.perform(MockMvcRequestBuilders.get("/guitars/model/c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("model").value("Guild"))
                .andExpect(jsonPath("brand").value("D45Bld"))
                .andExpect(jsonPath("strings").value(7))
                .andExpect(jsonPath("id").value(3l));
    }


    @Test
    public void getGuitar_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarByModel(anyString())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/guitars/model/Guild"))
                .andExpect(status().isNotFound());
    }

    /*****************************getGuitarById************************************/

    @Test
    public void getGuitarById_shouldReturnsGitarDetails() throws Exception{
        Guitar guitar = new Guitar("Guild","D45Bld", 7);
        guitar.setId(3l);
        given(guitarService.getSelectedGuitarById(anyLong())).willReturn(guitar);
        mockMvc.perform(MockMvcRequestBuilders.get("/guitars/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("model").value("Guild"))
                .andExpect(jsonPath("brand").value("D45Bld"))
                .andExpect(jsonPath("strings").value(7))
                .andExpect(jsonPath("id").value(3l));
    }

    @Test
    public void getGuitarById_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarById(anyLong())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/guitars/3"))
                .andExpect(status().isNotFound());
    }

    /*****************************getAllGitars************************************/

   @Test
    public void getAllGitars_shouldReturnAllGitarsDetailsFromDB() throws Exception{
       List<Guitar> guitarList = new ArrayList<>();
       Guitar guitar = new Guitar("Guild", "D45Bld",7);
       Guitar guitar1 = new Guitar("Guild2", "D45Bld2",14);
       guitarList.add(guitar);
       guitarList.add(guitar1);
        given(guitarService.getAllGuitarGitarsDetails()).willReturn(guitarList);
       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/guitars"))
                .andExpect(status().isOk())
               .andDo(print())
               .andReturn();
       String content = result.getResponse().getContentAsString();
       assertEquals(content,"[{\"id\":null,\"model\":\"Guild\",\"brand\":\"D45Bld\",\"strings\":7},{\"id\":null,\"model\":\"Guild2\",\"brand\":\"D45Bld2\",\"strings\":14}]");
   }



}
