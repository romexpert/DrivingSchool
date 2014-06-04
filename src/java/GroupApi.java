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
            IItemsAccess<Group> groupsAccess = AccessFactory.getAccessFactory().GroupsAccess();
            List<Group> groups = groupsAccess.getAllItems();

            if(groups.isEmpty()){
                
                IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
                Optional<Person> teacher = personAccess.getAllItems().stream().filter(person -> person.getAccountRole() == AccountRole.Teacher).findFirst();
                if(teacher.isPresent()){
                    groups.add(new Group("Группа 1", teacher.get()));
                    groups.add(new Group("Группа 2", teacher.get()));
                    groups.add(new Group("Группа 3", teacher.get()));
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
        try {
            JSONObject json = (JSONObject)JSONValue.parse(request.getReader());
            
            String name = json.get("name").toString();
            int teacherId = Integer.parseInt(json.get("teacherId").toString());
            //TODO: validation
            
            IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
            Person teacher = personAccess.getItem(teacherId);
            if(teacher.getAccountRole() != AccountRole.Teacher)
                throw new Exception("not a teacher");
            
            Group group = new Group(name, teacher);
            
            IItemsAccess<Group> groupsAccess = AccessFactory.getAccessFactory().GroupsAccess();
            groupsAccess.addOrUpdateItem(group);
            
            JSONObject result = new JSONObject();
            //TODO
            result.put("id", group.getId());
            result.writeJSONString(response.getWriter());
        } catch(Exception ex) {
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
    }

}