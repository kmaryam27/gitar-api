package com.galvanize.simplegitarapi;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {//3

    private TestRestTemplate testRestTemplate;/*test Rest Client-side application, RestTemplate sends real HTTP requests to the endpoints*/

    @Autowired
    GuitarRepository guitarRepository;

    /*****************************getGitarByModel************************************/
    @Test
    public void getGitarByModel_returnsGitarDetails() throws Exception{
        //arrange
        Guitar guitar = new Guitar(3l, "Guild", "tst1",5);
        guitarRepository.save(guitar);

        //act
        ResponseEntity<Guitar> gitarResponseEntity = testRestTemplate.getForEntity("/guitars/model/Guild", Guitar.class);
        //assert
        assertThat(gitarResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(gitarResponseEntity.getBody().getModel()).isEqualTo("Guild");
        assertThat(gitarResponseEntity.getBody().getBrand()).isEqualTo("D45Bld");
        assertThat(gitarResponseEntity.getBody().getStrings()).isEqualTo(7);
    }

    /*****************************getGitarById************************************/
    @Test
    public void getGitarById_returnsGitarDetails() throws Exception{
        //arrange
//        Guitar guitar = new Guitar("Guild", "D45Bld",7);
//        guitarRepository.save(guitar);
        given(guitarRepository.findById(anyLong())).willReturn(Optional.of(new Guitar("Guild","D45Bld", 7)));
        //act
        ResponseEntity<Guitar> gitarResponseEntity = testRestTemplate.getForEntity("/guitars/2", Guitar.class);
        //assert
        assertThat(gitarResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(gitarResponseEntity.getBody().getModel()).isEqualTo("Guild");
        assertThat(gitarResponseEntity.getBody().getBrand()).isEqualTo("D45Bld");
        assertThat(gitarResponseEntity.getBody().getStrings()).isEqualTo(7);
    }

    /*****************************getAllGitar************************************/
    @Test
    public void getAllGitars_returnsGitarsDetails() throws Exception{
        //arrange
        List<Guitar> guitarList = new ArrayList<>();
        Guitar guitar = new Guitar("Guild", "D45Bld",7);
        guitar = guitarRepository.save(guitar);
        Guitar guitar1 = new Guitar("Guild", "D45Bld",7);
        guitar1 = guitarRepository.save(guitar1);
        guitarList.add(guitar);
        guitarList.add(guitar1);

        given(guitarRepository.findAll()).willReturn(guitarList);
        //act
        ResponseEntity<Guitar> gitarResponseEntity = testRestTemplate.getForEntity("/guitars", Guitar.class);
        //assert
        assertThat(gitarResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(gitarResponseEntity.hasBody()).isEqualTo(true);
    }
}
