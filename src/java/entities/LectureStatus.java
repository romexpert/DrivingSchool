/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "LectureStatus")
public class LectureStatus implements Serializable {
    @Id
    @Column(name = "IID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int iid;
    
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;
    
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LectureStatusEnum status;
    
    public LectureStatus() {
        status = LectureStatusEnum.NotTaken;
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
     * @return the lecture
     */
    public Lecture getLecture() {
        return lecture;
    }

    /**
     * @param lecture the lecture to set
     */
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the status
     */
    public LectureStatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(LectureStatusEnum status) {
        this.status = status;
    }
}
