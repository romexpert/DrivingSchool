import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;

/**
 *
 * @author admin
 */
public class LogonApi extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = null, password = null;
        try {
            JSONObject json = (JSONObject)JSONValue.parse(request.getReader());
            
            email = json.get("email").toString();
            password = json.get("password").toString();
        } catch(Exception ex) {
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
        
        Account account = null;
        try {
            account = Account.login(email, password);
        }
        catch (Exception ex) {
            throw new ServletException(ex);
        }
        
        if(account == null){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Cookie sessionCookie = new Cookie("sessionId", account.getSessionId().toString());
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);
        
        response.setContentType("application/json; charset=utf-8");
        JSONObject data = new JSONObject();
        
        data.put("name", account.getName());
        data.put("role", account.getRole().toString());

        data.writeJSONString(response.getWriter());
    }
    
}