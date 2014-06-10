package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
//    @ManyToOne
//    @JoinColumn(name = "instructor_id", nullable = false)
//    private Person instructor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Person> students = new HashSet<>();
    
    public Group(){ }
    
    public Group(String name){
        this.name = name;
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
        return String.format("{\"id\": %s, \"name\": \"%s\"}", getId(), getName());
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

//    /**
//     * @return the teacher
//     */
//    public Person getTeacher() {
//        return teacher;
//    }

//    /**
//     * @param teacher the teacher to set
//     */
//    public void setTeacher(Person teacher) {
//        this.teacher = teacher;
//    }
    
    public Set<Person> getStudents() {
        return students;
    }
    
    public void setStudents(Set<Person> students) {
        this.students = students;
    }
}