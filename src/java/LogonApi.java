import java.io.IOException;
import javax.servlet.ServletException;
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
            // crash and burn
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
        
        if(logonUser(email, password)){
            response.setContentType("application/json; charset=utf-8");
            JSONObject data = new JSONObject();
            //TODO
            data.put("name", "Андрюша");
            data.put("role", "student");
            
            data.writeJSONString(response.getWriter());
            return;
        }
        
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
    
    boolean logonUser(String email, String password)
    {
        //if(email == null || password == null)
        //    return false;
        if("ex@ex.ru".equals(email) && "ex@ex.ru".equals(password))
            return true;
        
        return false;
    }
    
}