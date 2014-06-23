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
@Table(name = "QuestionVariants")
public class TestQuestionVariant implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "IID")
    private int id;
    
    @Column(name = "variant_order")
    private int number;
    
    @Column(name = "variant_text", length = 8000)
    private String text;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private TestQuestion question;
    
    public TestQuestionVariant() {
        
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
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the question
     */
    public TestQuestion getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(TestQuestion question) {
        this.question = question;
    }
    
    @Override
    public String toString(){
        return String.format("{\"id\": %s, \"optionsNumber\": %s, \"optionsText\": \"%s\"}", getId(), getNumber(), getText());
    }
}
