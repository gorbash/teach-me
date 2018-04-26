package teachme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachme.entities.Concept;
import teachme.repository.ConceptRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SumOfConceptsRestController {

    private static final Logger log = LoggerFactory.getLogger(SumOfConceptsRestController.class);

    @Autowired
    private ConceptRepository repo;

    @Value("${teach-me.sessionSize}")
    private int sessionSize;


    @RequestMapping(path = "/session")
    public List<Concept> session() {
        List<Concept> all = repo.findAllForSession();
        List<Concept> mid = new ArrayList<>();
        List<Concept> ret = new ArrayList<>();
        Iterator<Concept> iterator = all.iterator();
        while (mid.size() < 2 * sessionSize && iterator.hasNext()) {
            mid.add(iterator.next());
        }
        log.info("--------------Building session---------------");
        while (ret.size() < sessionSize && !mid.isEmpty()) {
            Concept concept = mid.remove((int) (Math.random() * mid.size()));
            concept.setHits(concept.getHits() + 1);
            repo.save(concept);
            ret.add(concept);
            log.info(concept.toString());
        }
        log.info("-----------------Session end-----------------");
        return ret;
    }
}
