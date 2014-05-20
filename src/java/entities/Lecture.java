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
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}