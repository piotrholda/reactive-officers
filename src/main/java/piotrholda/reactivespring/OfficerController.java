package piotrholda.reactivespring;

import piotrholda.reactivespring.dao.OfficerRepository;
import piotrholda.reactivespring.entities.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/officer")
public class OfficerController {

    private final OfficerRepository officerRepository;

    @Autowired
    public OfficerController(OfficerRepository officerRepository) {
        this.officerRepository = officerRepository;
    }

    @GetMapping
    public Flux<Officer> getAll() {
        return officerRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Officer> create(@RequestBody Officer officer) {
        return officerRepository.save(officer);
    }

}
