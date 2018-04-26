package teachme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${teach-me.numberOfGeneratedRecords}")
    private int numberOfGeneratedRecords;

    @Bean
    public CommandLineRunner loadData(ConceptRepository repository) {
        return (args) -> {
            if (numberOfGeneratedRecords > 0) {

                for (int i = 0; i < numberOfGeneratedRecords; i++) {
                    repository.save(generateConcept());
                }

                log.info("Concepts found with findAll():");
                log.info("-------------------------------");
                for (Concept concept : repository.findAll()) {
                    log.info(concept.toString());
                }
                log.info("");
            }
        };
    }

    private Concept generateConcept() {
        int qLength = (int) (Math.random() * 200);
        int aLength = (int) (Math.random() * 1000);
        return new Concept(genString(qLength), genString(aLength));
    }

    private String genString(int length) {
        StringBuffer buffer = new StringBuffer("");

        while (buffer.length() < length) {
            int sentenceLength = (int) (Math.random() * 30) + 1;
            for (int k = 0; k < sentenceLength; k++) {
                int wordLength = (int) (Math.random() * 12) + 1;
                for (int i = 0; i < wordLength; i++) {
                    buffer.append((char) (Math.random() * 26 + 'a'));
                }
                if (k != sentenceLength - 1)
                    buffer.append(' ');
            }
            buffer.append('.');
            buffer.append('\n');
        }
        String ret = buffer.toString();
        ret = ret.substring(0, length);
        return ret;
    }
}
