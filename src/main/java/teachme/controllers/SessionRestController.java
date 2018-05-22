package teachme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teachme.entities.Concept;
import teachme.entities.ConceptUser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SessionRestController {

    private static final Logger log = LoggerFactory.getLogger(SessionRestController.class);

    @Autowired
    private ConceptRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Value("${teach-me.sessionSize}")
    private int sessionSize;


    @RequestMapping(path = "/${teach-me.sessionUrl}", method = RequestMethod.POST)
    public ResponseEntity session(HttpServletRequest request) {
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("session: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        long start = System.currentTimeMillis();
        List<Concept> all = repo.findAllForSessionForUserName(user.getId());
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
        return new ResponseEntity(ret, HttpStatus.OK);
    }

    @RequestMapping(path = "/${teach-me.sessionAllUrl}", method = RequestMethod.POST)
    public ResponseEntity sessionAll(HttpServletRequest request) {
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("sessionAll: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        long start = System.currentTimeMillis();
        List<Concept> all = repo.findAllForSessionForUserName(user.getId());
        List<Concept> ret = new ArrayList<>();
        while (!all.isEmpty()) {
            Concept concept = all.remove((int) (Math.random() * all.size()));
            ret.add(concept);
        }
        long end = System.currentTimeMillis();
        log.info(String.format("Request from %s user %s - building session took %dms", request.getRemoteAddr(), request.getUserPrincipal().getName(), end - start));
        return new ResponseEntity(ret, HttpStatus.OK);
    }
}
