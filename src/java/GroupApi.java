import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

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
        
        response.setContentType("application/json; charset=utf-8");
        //TODO
        
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group(1, 1));
        groups.add(new Group(2, 2));
        groups.add(new Group(3, 3));
        
        JSONArray data = new JSONArray();
        data.addAll(groups);

        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
