import java.util.UUID;

/**
 *
 * @author admin
 */
public class Account {
    
    //TODO:remove
    public static UUID _adminGuid = UUID.fromString("8BA6B85D-21B5-4AFC-B8B3-DF9988F42E01");
    public static UUID _teacherGuid = UUID.fromString("2D690119-FA6E-4FEC-91C3-D0213DA38646");
    public static UUID _studentGuid = UUID.fromString("30505A4C-7742-4E1B-AC77-920E7BEAB488");
    //TODO:end remove
    
    private UUID _sessionId;
    private String _email;
    private String _name;
    private AccountRole _role;
    
    
    public UUID getSessionId(){
        return _sessionId;
    }
    
    public String getName(){
        return _name;
    }
    
    public AccountRole getRole(){
        return _role;
    }
    
    
    private Account(){ }
    
    
    public static Account login(String email, String password){
        
        UUID sessionId = null;
        AccountRole role = null;
        String name = null;
        
        //TODO:remove fake
        if("student@driftman.ru".equals(email) && "student123".equals(password)){
            sessionId = _studentGuid;
            role = AccountRole.Student;
            name = "Андрюша";
        }
        else if("teacher@driftman.ru".equals(email) && "teacher123".equals(password)){
            sessionId = _teacherGuid;
            role = AccountRole.Teacher;
            name = "Учитель Андрюша";
        }
        else if("admin@driftman.ru".equals(email) && "admin123".equals(password)){
            sessionId = _adminGuid;
            role = AccountRole.Admin;
            name = "Админ Андрюша";
        }
        //TODO:end:remove fake
        
        if(sessionId == null)
            return null;
        
        Account account = new Account();
        account._sessionId = sessionId;
        account._email = email;
        account._name = name;
        account._role = role;
        
        return account;
    }
    
}
