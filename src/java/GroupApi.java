import entities.AccountRole;
import entities.Group;
import entities.Person;
import entities.access.IItemsAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
            IItemsAccess<Group> groupsAccess = AccessFactory.getAccessFactory().GroupsAccess();
            List<Group> groups = groupsAccess.getAllItems();

            if(groups.isEmpty()){
                
                IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
                Optional<Person> teacher = personAccess.getAllItems().stream().filter(person -> person.getAccountRole() == AccountRole.Teacher).findFirst();
                if(teacher.isPresent()){
                    groups.add(new Group(1, "Группа 1", teacher.get()));
                    groups.add(new Group(2, "Группа 2", teacher.get()));
                    groups.add(new Group(3, "Группа 3", teacher.get()));
                }
            }

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
        JSONObject result = new JSONObject();
        //TODO
        result.put("id", 3);

        result.writeJSONString(response.getWriter());
    }

}