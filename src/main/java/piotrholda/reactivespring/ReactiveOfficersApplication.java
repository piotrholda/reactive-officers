package piotrholda.reactivespring;

import piotrholda.reactivespring.dao.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveOfficersApplication {

    @Autowired
    OfficerRepository officerRepository;


    public static void main(String[] args) {
        SpringApplication.run(ReactiveOfficersApplication.class, args);

    }

}
