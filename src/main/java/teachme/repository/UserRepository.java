package teachme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import teachme.entities.ConceptUser;

public interface UserRepository extends CrudRepository<ConceptUser, Long> {

    public ConceptUser findByUserName(@Param("userName") String userName);
}
