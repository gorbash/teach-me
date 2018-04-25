package teachme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import teachme.entities.Concept;
import teachme.repository.ConceptRepository;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner loadData(ConceptRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Concept("SOLID", "In object-oriented computer programming, the term SOLID is a mnemonic acronym for five design principles intended to make software designs more understandable, flexible and maintainable. "));
            repository.save(new Concept("S in SOLID", "Single responsibility principle"));
            repository.save(new Concept("O in SOLID", "Open/Closed principle"));

            // fetch all customers
            log.info("Concepts found with findAll():");
            log.info("-------------------------------");
            for (Concept concept : repository.findAll()) {
                log.info(concept.toString());
            }
            log.info("");
        };
    }
}
