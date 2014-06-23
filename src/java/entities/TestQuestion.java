/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "Questions")
public class TestQuestion implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "IID")
    private int id;
    
    @Column(name = "question", length = 8000)
    private String question;
    
    private String image;
    
    @Column(name = "correct_result")
    private int correctResult;
    
    @Column(name = "comment", length = 8000)
    private String comment;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<TestQuestionVariant> variants = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = true)
    private Lecture lecture;
    
    public TestQuestion() {
        
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
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the correctResult
     */
    public int getCorrectResult() {
        return correctResult;
    }

    /**
     * @param correctResult the correctResult to set
     */
    public void setCorrectResult(int correctResult) {
        this.correctResult = correctResult;
    }

    /**
     * @return the variants
     */
    public Set<TestQuestionVariant> getVariants() {
        return variants;
    }

    /**
     * @param variants the variants to set
     */
    public void setVariants(Set<TestQuestionVariant> variants) {
        this.variants = variants;
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Override
    public String toString(){
        return String.format("{\"id\": %s, \"lectureNumber\": %s, \"question\": \"%s\", \"imageUrl\": \"%s\", \"options\": %s}", getId(), getLecture().getNumber(),
                getQuestion().replace("\n", " "),
                getImage(), getVariants());
    }
}
