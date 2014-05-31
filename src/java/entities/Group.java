package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author admin
 */
@Entity
@Table(name="Groups")
public class Group implements Serializable {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name="IID")
    private int id;
    private String name;
    
    @Column(name = "teacher")
    private int teacherId;
    
    public Group(){ }
    
    public Group(int id, String name, int teacherId){
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }
    
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public int getTeacherId(){
        return teacherId;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"id\": %s, \"name\": \"%s\", \"teacherId\": %s}", getId(), getName(), getTeacherId());
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param teacherId the teacherId to set
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}