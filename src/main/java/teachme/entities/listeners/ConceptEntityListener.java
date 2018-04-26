package teachme.entities.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import teachme.entities.Concept;
import teachme.repository.ConceptRepository;

import javax.persistence.PrePersist;


public class ConceptEntityListener {

    private static final Logger log = LoggerFactory.getLogger(ConceptEntityListener.class);


    @Autowired
    private ConceptRepository repo;


    @PrePersist
    public void updateConcept(Concept concept) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        log.info("Before persisting concept " + concept);
        /*List<Concept> allForSession = repo.findAllForSession();
        if (!allForSession.isEmpty()) {
            long hits = allForSession.get(0).getHits();
            if (hits > 0) {
                concept.setHits(hits - 1);
            }
        }*/

    }
}
