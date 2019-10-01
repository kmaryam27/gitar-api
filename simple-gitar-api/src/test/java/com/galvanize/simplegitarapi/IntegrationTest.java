package com.galvanize.simplegitarapi;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {//3

    // Will contain the random free port number
    @LocalServerPort
    int port;

    // Our application needs an instance of TestRestTemplate otherwise you'll get test errors
    private TestRestTemplate testRestTemplate = new TestRestTemplate();/*test Rest Client-side application, RestTemplate sends real HTTP requests to the endpoints*/

    // Mock the Guitar Repository instead of using the actual repo
    @MockBean
    GuitarRepository guitarRepository;

    @Before
    public void init() {
        // before every test, this will create a guitar
        Guitar guitar = new Guitar("LesPaul", "Gibson", 5);
        // use the mock repository to avoid HTTP 404 error
        when(guitarRepository.findByModel("LesPaul")).thenReturn(guitar);
    }

    // Utility method for creating URLs
    private String getFullUri(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    /*****************************getGitarByModel************************************/
    @Test
    public void getGitarByModel_returnsGitarDetails() throws Exception{
        //arrange
        // getForEntity() requires the full URI path, not a relative path
        String url = getFullUri("/guitars/model/LesPaul");

        //act
        ResponseEntity<String> gitarResponseEntity = testRestTemplate.getForEntity(url, String.class);
        //assert
        assertThat(gitarResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(gitarResponseEntity.getBody()).contains("LesPaul");
        assertThat(gitarResponseEntity.getBody()).contains("Gibson");
        assertThat(gitarResponseEntity.getBody()).contains("5");
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
