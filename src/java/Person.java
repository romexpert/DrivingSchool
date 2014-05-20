/**
 *
 * @author admin
 */
public class Person {
    
    private int _id;
    private String _name;
    private String _phone;
    private AccountRole _role;
    
    public int getId(){
        return _id;
    }
    public String getName(){
        return _name;
    }
    public String getPhone(){
        return _phone;
    }
    public AccountRole getRole(){
        return _role;
    }
    
    public Person(int id, String name, String phone, AccountRole role){
        _id = id;
        _name = name;
        _phone = phone;
        _role = role;
    }
    
    @Override
    public String toString(){
        return String.format("{\"id\": %1$s, \"name\": \"%2$s\", \"phone\": \"%3$s\", \"role\": \"%4$s\"}", getId(), getName(), getPhone(), getRole());
    }
}
