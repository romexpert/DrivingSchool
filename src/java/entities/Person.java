package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author admin
 */
@Entity
@Table(name="People")
public class Person implements Serializable {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name="IID")
    private int id;
    private String name;
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "AccountRole")
    private AccountRole accountRole;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
//    private Set<Group> groups = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private Set<Person> students = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<LectureStatus> lectures = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Person instructor;
    
    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private StudentGroup group;
    
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public AccountRole getAccountRole(){
        return accountRole;
    }
    
    public LectureStatus GetStatus(Lecture lect) {
        if(lect == null)
            return null;
        for(LectureStatus ls: lectures) {
            if(ls.getLecture() != null && ls.getLecture().getIid() == lect.getIid())
                return ls;
        }
        return null;
    }
    
    public Person() {
        
    }
    
    public Person(String name, String email, String phone, AccountRole role, String passwordHash, Person instructor){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.accountRole = role;
        this.passwordHash = passwordHash;
        setInstructor(instructor);
    }
    
    @Override
    public String toString(){
        Person instructor = getInstructor();
        Integer instructorId = instructor == null ? null : instructor.id;
        return String.format("{\"id\": %1$s,\"name\": \"%2$s\", \"email\": \"%3$s\", \"phone\": \"%4$s\", \"role\": \"%5$s\", \"instructorId\": %6$s}", getId(), getName(), getEmail(), getPhone(), getAccountRole(), instructorId);
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
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash the passwordHash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @param accountRole the accountRole to set
     */
    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

//    /**
//     * @return the groups
//     */
//    public Set<Group> getGroups() {
//        return groups;
//    }
//
//    /**
//     * @param groups the groups to set
//     */
//    public void setGroups(Set<Group> groups) {
//        this.groups = groups;
//    }
    
    public Person getInstructor() {
        return instructor;
    }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }
    
    public Set<Person> getStudents() {
        return students;
    }
    
    public void setStudents(Set<Person> students) {
        this.students = students;
    }
    
    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    /**
     * @return the lectures
     */
    public Set<LectureStatus> getLectures() {
        return lectures;
    }
}