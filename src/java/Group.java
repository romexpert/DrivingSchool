/**
 *
 * @author admin
 */
public class Group {
    private int _id;
    private String _name;
    private int _teacherId;
    
    
    public Group(int id, String name, int teacherId){
        _id = id;
        _name = name;
        _teacherId = teacherId;
    }
    
    
    public int getId(){
        return _id;
    }
    
    public String getName(){
        return _name;
    }
    
    public int getTeacherId(){
        return _teacherId;
    }
    
    
    @Override
    public String toString(){
        return String.format("{\"id\": %s, \"name\": \"%s\", \"teacherId\": %s}", getId(), getName(), getTeacherId());
    }
}