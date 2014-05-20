import java.util.UUID;

/**
 *
 * @author admin
 */
public class Account {
    
    //TODO:remove
    public static UUID _adminGuid = UUID.fromString("8BA6B85D-21B5-4AFC-B8B3-DF9988F42E01");
    private static String _adminMail = "admin@driftman.ru";
    public static UUID _teacherGuid = UUID.fromString("2D690119-FA6E-4FEC-91C3-D0213DA38646");
    private static String _teacherMail = "teacher@driftman.ru";
    public static UUID _studentGuid = UUID.fromString("30505A4C-7742-4E1B-AC77-920E7BEAB488");
    private static String _studentMail = "student@driftman.ru";
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
    
    
    public static Account login(String email, String password) throws Exception{
        
        UUID sessionId = null;
        
        //TODO:remove fake
        if(_studentMail.equals(email) && "student123".equals(password)){
            sessionId = _studentGuid;
        }
        else if(_teacherMail.equals(email) && "teacher123".equals(password)){
            sessionId = _teacherGuid;
        }
        else if(_adminMail.equals(email) && "admin123".equals(password)){
            sessionId = _adminGuid;
        }
        //TODO:end:remove fake
        
        if(sessionId == null)
            return null;
        
        Account account = getByEmail(email);
        account._sessionId = sessionId;
        
        return account;
    }
    
    public static Account get(UUID sessionId) throws Exception{
        String email = null;
        
        if(_studentGuid.equals(sessionId))
            email = _studentMail;
        else if (_teacherGuid.equals(sessionId))
            email = _teacherMail;
        else if(_adminGuid.equals(sessionId))
            email = _adminMail;
        else
            throw new Exception("SessionNotExists");
        
        Account account = getByEmail(email);
        account._sessionId = sessionId;
        return account;
    }
    
    private static Account getByEmail(String email) throws Exception{
        Account account = new Account();
        account._email = email;
        
        //TODO:remove fake
        if(_studentMail.equals(email)){
            account._role = AccountRole.Student;
            account._name = "Андрюша";
        }
        else if(_teacherMail.equals(email)){
            account._role = AccountRole.Teacher;
            account._name = "Учитель Андрюша";
        }
        else if(_adminMail.equals(email)){
            account._role = AccountRole.Admin;
            account._name = "Админ Андрюша";
        }
        else
            throw new Exception("Email was not found.");
        //TODO:end:remove fake
        
        return account;
    }
    
}