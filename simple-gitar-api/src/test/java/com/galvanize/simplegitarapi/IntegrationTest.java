package com.galvanize.simplegitarapi;

import com.galvanize.simplegitarapi.entity.Guitar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {//3
    private TestRestTemplate testRestTemplate;/*test Rest Client-side application, RestTemplate sends real HTTP requests to the endpoints*/


    @Test
    public void getGitarByModel_returnsGitarDetails() throws Exception{
        //arrange
        //act
        ResponseEntity<Guitar> gitarResponseEntity = testRestTemplate.getForEntity("/gitars/Guild", Guitar.class);
        //assert
        assertThat(gitarResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(gitarResponseEntity.getBody().getModel()).isEqualTo("Guild");
        assertThat(gitarResponseEntity.getBody().getBrand()).isEqualTo("D45Bld");
        assertThat(gitarResponseEntity.getBody().getStrings()).isEqualTo(7);
    }
}
