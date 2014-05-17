
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author admin
 */
public class Exercise {
    private int _number;
    private Date _dateTime;
    private String _status;
    
    
    public Exercise(int number, Date dateTime, String status){
        _number = number;
        _dateTime = dateTime;
        _status = status;
    }
    
    
    public int getNumber(){
        return _number;
    }
    
    public Date getDateTime(){
        return _dateTime;
    }
    
    public String getStatus(){
        return _status;
    }
    
    
    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        //SimpleDateFormat.
        return String.format("{\"number\": %1$s, \"datetime\": \"%2$s\", \"status\": \"%3$s\"}", getNumber(), df.format(getDateTime()), getStatus());
    }
}
