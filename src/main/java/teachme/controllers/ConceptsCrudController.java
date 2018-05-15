package teachme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teachme.entities.Concept;
import teachme.entities.ConceptUser;
import teachme.repository.ConceptRepository;
import teachme.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ConceptsCrudController {

    private static final Logger log = LoggerFactory.getLogger(ConceptsCrudController.class);

    @Autowired
    private ConceptRepository conceptRepo;

    @Autowired
    private UserRepository userRepo;

    @RequestMapping(path = "/${teach-me.conceptsUrl}", method = RequestMethod.GET)
    public ResponseEntity concepts(HttpServletRequest request) {
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("concepts: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        List<Concept> ret = conceptRepo.findAllOrderByNameForUserName(user.getId());
        return new ResponseEntity(ret, HttpStatus.OK);
    }

    @RequestMapping(path = "/${teach-me.conceptsUrl}/{id}", method = RequestMethod.GET)
    public ResponseEntity concept(HttpServletRequest request, @PathVariable Long id) {
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("concept: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        Concept byId = conceptRepo.findByIDAndUserName(id, user.getId());
        if (byId == null) {
            return new ResponseEntity("No such id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(byId, HttpStatus.OK);
    }

    @RequestMapping(value = "/${teach-me.conceptsUrl}", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletRequest request, @RequestBody Concept concept) {
        log.info("Creating " + concept);
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("create: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        concept.setUser(user);
        ResponseEntity ret = null;
        try {
            conceptRepo.save(concept);
            ret = new ResponseEntity(concept, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ret = ResponseEntity.badRequest().build();
        }
        return ret;
    }

    @RequestMapping(value = "/${teach-me.conceptsUrl}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(HttpServletRequest request, @PathVariable Long id) {
        log.info("Deleting " + id);
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("delete: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        Concept byId = conceptRepo.findByIDAndUserName(id, user.getId());
        if (byId == null) {
            log.error("No such concept " + id);
            return new ResponseEntity(id, HttpStatus.NOT_FOUND);
        }
        conceptRepo.delete(byId);
        return new ResponseEntity(id, HttpStatus.OK);

    }

    @RequestMapping(value = "/${teach-me.conceptsUrl}/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(HttpServletRequest request, @PathVariable Long id, @RequestBody Concept concept) {
        log.info("Updating " + id + " concept: " + concept);
        ConceptUser user = userRepo.findByUserName(request.getUserPrincipal().getName());
        log.info("update: got user " + user);
        if (user == null) {
            return new ResponseEntity("No user in database", HttpStatus.NOT_FOUND);
        }
        Concept byId = conceptRepo.findByIDAndUserName(id, user.getId());
        if (byId == null) {
            log.error("No such concept " + id);
            return new ResponseEntity(id, HttpStatus.NOT_FOUND);
        }
        ResponseEntity ret = null;
        byId.setName(concept.getName());
        byId.setDefinition(concept.getDefinition());
        try {
            conceptRepo.save(byId);
            ret = new ResponseEntity(byId, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ret = ResponseEntity.badRequest().build();
        }
        return ret;
    }

}
