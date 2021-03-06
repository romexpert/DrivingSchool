package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author admin
 */
@Entity
@Table(name="Lectures")
public class Lecture implements Serializable {
    
    @Id
    @GenericGenerator(name="increment", strategy="increment")
    @GeneratedValue(generator="increment")
    @Column(name="IID")
    private int iid;
    
    private int number;
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lecture")
    private Set<TestQuestion> questions = new HashSet<TestQuestion>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lecture")
    private Set<LectureStatus> lectureStats = new HashSet<>();
    
    public Lecture() {
    }
    
    public Lecture(int number, String name){
        this.number = number;
        this.name = name;
    }
    
    
    public int getNumber(){
        return number;
    }
    
    public String getName(){
        return name;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"number\": %s, \"name\": \"%s\"}", getNumber(), getName());
    }

    /**
     * @return the iid
     */
    public int getIid() {
        return iid;
    }

    /**
     * @param iid the iid to set
     */
    public void setIid(int iid) {
        this.iid = iid;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the questions
     */
    public Set<TestQuestion> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(Set<TestQuestion> questions) {
        this.questions = questions;
    }

    /**
     * @return the lectureStats
     */
    public Set<LectureStatus> getLectureStats() {
        return lectureStats;
    }
}