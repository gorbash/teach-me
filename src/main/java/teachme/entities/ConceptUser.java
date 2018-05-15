package teachme.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConceptUser {


    @Id
    @GeneratedValue
    private long id;

    private String userName;

    private String password;


    public ConceptUser() {
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "ConceptUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}
