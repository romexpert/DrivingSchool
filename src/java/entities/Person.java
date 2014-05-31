package entities;
/**
 *
 * @author admin
 */
public class Person {
    
    private int _id;
    private String _name;
    private String _email;
    private String _passwordHash;
    private String _phone;
    private AccountRole _role;
    
    public int getId(){
        return _id;
    }
    public String getName(){
        return _name;
    }
    public String getEmail(){
        return _email;
    }
    public String getPhone(){
        return _phone;
    }
    public AccountRole getRole(){
        return _role;
    }
    
    public Person(int id, String name, String email, String phone, AccountRole role){
        _id = id;
        _name = name;
        _email = email;
        _phone = phone;
        _role = role;
    }
    
    @Override
    public String toString(){
        return String.format("{\"id\": %1$s,\"name\": \"%2$s\", \"email\": \"%3$s\", \"phone\": \"%4$s\", \"role\": \"%5$s\"}", getId(), getName(), getEmail(), getPhone(), getRole());
    }
}