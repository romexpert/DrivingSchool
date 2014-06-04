package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
    /*
    @Column(name = "teacher_id")
    private int teacherId;*/
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Person teacher;
    
    public Group(){ }
    
    public Group(int id, String name, Person teacher){
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }
    
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    /*
    public int getTeacherId(){
        return teacherId;
    }*/
    
    
    @Override
    public String toString(){
        return String.format("{\"id\": %s, \"name\": \"%s\", \"teacherId\": %s}", getId(), getName(), getTeacher().getId());
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
     *//*
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }*/

    /**
     * @return the teacher
     */
    public Person getTeacher() {
        return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }
}