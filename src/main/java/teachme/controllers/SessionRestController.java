package teachme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teachme.entities.Concept;
import teachme.repository.ConceptRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SessionRestController {

    private static final Logger log = LoggerFactory.getLogger(SessionRestController.class);

    @Autowired
    private ConceptRepository repo;

    @Value("${teach-me.sessionSize}")
    private int sessionSize;


    @RequestMapping(path = "/${teach-me.sessionUrl}", method = RequestMethod.POST)
    public List<Concept> session(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        List<Concept> all = repo.findAllForSession();
        List<Concept> mid = new ArrayList<>();
        List<Concept> ret = new ArrayList<>();
        Iterator<Concept> iterator = all.iterator();
        while (mid.size() < 2 * sessionSize && iterator.hasNext()) {
            mid.add(iterator.next());
        }
        while (ret.size() < sessionSize && !mid.isEmpty()) {
            Concept concept = mid.remove((int) (Math.random() * mid.size()));
            concept.setHits(concept.getHits() + 1);
            repo.save(concept);
            ret.add(concept);
        }
        long end = System.currentTimeMillis();
        log.info(String.format("Request from %s user %s - building session took %dms", request.getRemoteAddr(), request.getUserPrincipal().getName(), end - start));
        return ret;
    }
}
