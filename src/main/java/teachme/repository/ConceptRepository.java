package teachme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import teachme.entities.Concept;

import java.util.List;


public interface ConceptRepository extends CrudRepository<Concept, Long> {

    List<Concept> findByName(@Param("name") String name);

 /*   @Query("select c from Concept c order by c.hits asc")
    List<Concept> findAllForSession();*/

    @Query("select c from Concept c, ConceptUser cu where c.user = cu and cu.id = :user_id order by c.hits asc")
    List<Concept> findAllForSessionForUserName(@Param("user_id") Long userid);

    @Query("select c from Concept c, ConceptUser cu where c.user = cu and cu.id = :user_id order by c.name asc")
    List<Concept> findAllOrderByNameForUserName(@Param("user_id") Long userid);

    @Query("select c from Concept c, ConceptUser cu where c.user = cu and cu.id = :user_id and c.id = :id")
    Concept findByIDAndUserName(@Param("id") Long conceptId, @Param("user_id") Long userid);
}