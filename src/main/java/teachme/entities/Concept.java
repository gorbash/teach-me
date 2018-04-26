package teachme.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Concept {

    @Id
    @GeneratedValue
    private long id;

    @Type(type="text")
    private String name;

    @Type(type="text")
    private String definition;

    private Long hits;

    public Concept(String name, String definition) {
        this.name = name;
        this.definition = definition;
        this.hits = 0l;
    }

    public Concept() {

    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", definition='" + definition + '\'' +
                ", hits=" + hits +
                '}';
    }
}
