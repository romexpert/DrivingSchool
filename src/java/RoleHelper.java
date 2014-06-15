import entities.AccountRole;
import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class RoleHelper {
    
    public static boolean IsInRole(HttpServletRequest request, HttpServletResponse response, AccountRole role) throws IOException{
        Account account = (Account)request.getAttribute("account");
        if(account.getRole() == role){
            return true;
        }
        
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
    
    public static boolean IsInRole(HttpServletRequest request, HttpServletResponse response, EnumSet<AccountRole> role) throws IOException{
        Account account = (Account)request.getAttribute("account");
        if(role.contains(account.getRole())){
            return true;
        }
        
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
    
}
