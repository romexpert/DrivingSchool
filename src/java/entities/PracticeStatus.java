/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "PracticeStatuses")
public class PracticeStatus implements Serializable{
    @Id
    @Column(name = "iid")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    
    @Column(name="practice", nullable = false)
    private int practiceNumber;
    
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person student;
    
    @Column(name = "is_completed", nullable = false)
    private boolean completed;
    
    public PracticeStatus() {
        this.completed = false;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the practiceNumber
     */
    public int getPracticeNumber() {
        return practiceNumber;
    }

    /**
     * @param practiceNumber the practiceNumber to set
     */
    public void setPracticeNumber(int practiceNumber) {
        this.practiceNumber = practiceNumber;
    }

    /**
     * @return the student
     */
    public Person getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Person student) {
        this.student = student;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
