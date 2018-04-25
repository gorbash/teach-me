package teachme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import teachme.entities.Concept;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "concepts", path = "concepts")
public interface ConceptRepository extends CrudRepository<Concept, Long> {

    List<Concept> findByName(@Param("name") String name);


    @Query("select c from Concept c order by c.hits")
    List<Concept> findAllForSession();

}