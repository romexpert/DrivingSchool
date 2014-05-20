package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name="Lectures")
public class Lecture implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int iid;
    private int number;
    private String name;
    private String status;
    
    public Lecture() {
    }
    
    public Lecture(int number, String name, String status){
        this.number = number;
        this.name = name;
        this.status = status;
    }
    
    
    public int getNumber(){
        return number;
    }
    
    public String getName(){
        return name;
    }
    
    public String getStatus(){
        return status;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"number\": %s, \"name\": \"%s\", \"status\": \"%s\"}", getNumber(), getName(), getStatus());
    }
}