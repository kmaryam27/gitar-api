package com.galvanize.simplegitarapi.repositories;

import com.galvanize.simplegitarapi.entity.Guitar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class GuitarRepositoryTest {

    @Autowired
    private GuitarRepository guitarRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByModel_returnGitarDetails() throws Exception{
//        Guitar savedGuitar = testEntityManager.persistFlushFind(new Guitar(3l,"Guild","D45Bld", 7));
        Guitar savedGuitar = new Guitar(3l,"Guild","D45Bld", 7);
        savedGuitar = guitarRepository.save(savedGuitar);

        Guitar guitar = guitarRepository.findByModel("Guild");
        assertThat(guitar.getModel()).isEqualTo(savedGuitar.getModel());
    }
}