package teachme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import teachme.entities.Concept;
import teachme.repository.ConceptRepository;

import java.util.List;

@RestController
public class SumOfConceptsRestController {

    @Autowired
    private ConceptRepository repo;

    @Value("${teach-me.testingProp}")
    private String prop;


    @RequestMapping(path="/session")
    public List<Concept> session() {
        return repo.findAllForSession();
    }
}
