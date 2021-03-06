import entities.AccountRole;
import entities.StudentGroup;
import entities.Person;
import entities.access.IItemsAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author admin
 */
public class GroupApi extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!RoleHelper.IsInRole(request, response, AccountRole.Admin)){
            return;
        }
        
        try{
            List<StudentGroup> groups = AccessFactory.GroupsAccess().getAllItems();

            JSONArray data = new JSONArray();
            data.addAll(groups);

            data.writeJSONString(response.getWriter());
        } catch(SQLException ex){
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject json = (JSONObject)JSONValue.parse(request.getReader());            
            String name = json.get("name").toString();
            
            StudentGroup group = new StudentGroup(name);            
            AccessFactory.GroupsAccess().addOrUpdateItem(group);
            
            JSONObject result = new JSONObject();
            result.put("id", group.getId());
            result.writeJSONString(response.getWriter());
        } catch(Exception ex) {
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
    }

}