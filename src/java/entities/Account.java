package entities;

import entities.util.AccessFactory;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author admin
 */
@Entity
@Table(name="Sessions")
public class Account implements Serializable {
    
    @Id
    @Column
    private UUID sessionId;
    @Column
    private String email;
    @Column
    private Date createdDate;
    @Transient
    private int _id;
    @Transient
    private String _name;
    @Transient
    private AccountRole _role;
    
    
    public UUID getSessionId(){
        return sessionId;
    }
    
    public String getName(){
        return _name;
    }
    
    public AccountRole getRole(){
        return _role;
    }
    
    public int getId(){
        return _id;
    }
    
    
    private Account(){ }
    
    
    public static Account login(String email, String password) throws Exception{
        
        Account account = null;
        Person person = AccessFactory.PeopleAccess().getByEmail(email);
        if(person != null && password.equals(person.getPasswordHash())){
            account = getByEmail(email);
            account.sessionId = UUID.randomUUID();
            account.createdDate = new Date();
            AccessFactory.SessionAccess().addOrUpdateItem(account);
        }
        
        return account;
    }
    
    public static Account get(UUID sessionId) throws Exception{
        String email = null;
        
        Account session = AccessFactory.SessionAccess().getItem(sessionId);
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        
        if(session == null || session.createdDate.compareTo(c.getTime()) < 0)
            throw new Exception("SessionNotExists");
        
        Account account = getByEmail(session.email);
        account.sessionId = session.getSessionId();
        return account;
    }
    
    private static Account getByEmail(String email) throws Exception{
        Account account = new Account();
        account.email = email;
        
        Person person = AccessFactory.PeopleAccess().getByEmail(email);
        account._id = person.getId();
        account._name = person.getName();
        account._role = person.getAccountRole();
        
        return account;
    }
    
    
    public static Account getTest(){
        Account account = new Account();
        
        account._role = AccountRole.Admin;
        
        return account;
    }
}