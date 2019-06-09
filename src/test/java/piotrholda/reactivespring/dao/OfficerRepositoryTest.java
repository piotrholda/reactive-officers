package piotrholda.reactivespring.dao;

import piotrholda.reactivespring.dao.OfficerRepository;
import piotrholda.reactivespring.entities.Officer;
import piotrholda.reactivespring.entities.Rank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficerRepositoryTest {

    @Autowired
    OfficerRepository officerRepository;

    private final List<Officer> officers = Arrays.asList(
            new Officer(Rank.ADMIRAL, "Clar", "Kirk"),
            new Officer(Rank.CAPTAIN, "Tom", "Hanks"),
            new Officer(Rank.ENSIGN, "Tony", "Halik")
    );

    @Before
    public void setUp() {
        officerRepository
                .deleteAll()
                .thenMany(Flux.fromIterable(officers))
                .flatMap(officerRepository::save)
                .then()
                .block();
    }

    @Test
    public void shouldCreateOfficer() {
        StepVerifier.create(officerRepository.save(new Officer(Rank.ADMIRAL, "Robinson", "Crusoe")))
                .expectNextMatches(officer -> !officer.getId().equals(""))
                .verifyComplete();
    }

    @Test
    public void shouldSelectById() {
        officers.stream()
                .map(Officer::getId)
                .forEach(id ->
                        StepVerifier.create(officerRepository.findById(id))
                                .expectNextMatches(officer -> officer.getId().equals(id))
                                .verifyComplete()
                );
    }

    @Test
    public void shouldFIndByLast() {
        officers.stream()
                .map(Officer::getLast)
                .forEach(last ->
                        StepVerifier.create(officerRepository.findByLast(last))
                                .expectNextMatches(officer -> officer.getLast().equals(last))
                                .verifyComplete()
                );
    }

    @Test
    public void shouldFindByRank() {
        officers.stream()
                .map(Officer::getRank)
                .forEach(rank ->
                        StepVerifier.create(officerRepository.findByRank(rank)
                                .map(Officer::getRank)
                                .distinct())
                                .expectNextMatches(r -> r.equals(rank))
                                .verifyComplete()
                );
    }

}