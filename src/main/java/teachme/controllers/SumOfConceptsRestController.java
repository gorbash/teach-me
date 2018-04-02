package teachme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import teachme.repository.ConceptRepository;

@RestController
public class SumOfConceptsRestController {

    @Autowired
    private ConceptRepository repo;

    @Value("${teach-me.testingProp}")
    private String prop;

    @RequestMapping(path = "/sum")
    @ResponseBody
    public String sum() {

        return "sum of concepts is " + repo.count() + " info from application.yml " + prop;

    }
}
