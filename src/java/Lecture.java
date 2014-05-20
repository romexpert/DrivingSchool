/**
 *
 * @author admin
 */
public class Lecture {
    private int _number;
    private String _name;
    private String _status;
    
    
    public Lecture(int number, String name, String status){
        _number = number;
        _name = name;
        _status = status;
    }
    
    
    public int getNumber(){
        return _number;
    }
    
    public String getName(){
        return _name;
    }
    
    public String getStatus(){
        return _status;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"number\": %s, \"name\": \"%s\", \"status\": \"%s\"}", getNumber(), getName(), getStatus());
    }
}