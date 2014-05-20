/**
 *
 * @author admin
 */
public class Group {
    private int _number;
    private int _teacherId;
    
    
    public Group(int number, int teacherId){
        _number = number;
        _teacherId = teacherId;
    }
    
    
    public int getNumber(){
        return _number;
    }
    
    public int getTeacherId(){
        return _teacherId;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"number\": %s, \"teacherId\": %s}", getNumber(), getTeacherId());
    }
}