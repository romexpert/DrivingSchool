import entities.AccountRole;
import entities.Group;
import entities.Person;
import entities.access.IItemsAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;
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
public class StaffApi extends HttpServlet {

    private static final EnumSet<AccountRole> _staffRoles = EnumSet.of(AccountRole.Admin, AccountRole.Instructor);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!RoleHelper.IsInRole(request, response, AccountRole.Admin)){
            return;
        }
        
        //TODO
        Integer groupId = null;
        try{
            groupId = Integer.parseInt(request.getParameter("groupId"));
        } catch(Exception ex){
            
        }
        
        String roleParam = request.getParameter("role");
        AccountRole role = roleParam != null ? AccountRole.valueOf(roleParam) : null;
        IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
        List<Person> staff;
        try {
            staff = personAccess.getAllItems();
            
            List<Person> result;
            
            if(groupId == null){
                result = staff.stream().filter(person -> (role == null && _staffRoles.contains(person.getAccountRole())) || person.getAccountRole() == role).collect(Collectors.toList());
            } else {
                IItemsAccess<Group> groupsAccess = AccessFactory.getAccessFactory().GroupsAccess();
                
                Group group = groupsAccess.getItem(groupId);
                result = new ArrayList(group.getStudents());
            }
        
            JSONArray data = new JSONArray();
            data.addAll(result);

            data.writeJSONString(response.getWriter());
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject json = (JSONObject)JSONValue.parse(request.getReader());
            
            String mail = json.get("mail").toString();
            String name = json.get("name").toString();
            String phone = json.get("phone").toString();
            AccountRole role = AccountRole.valueOf(json.get("role").toString());
            String password = json.get("password").toString();
            //TODO: validation
            
            Person person = new Person(name, mail, phone, role, password, null);
            
            Integer groupId = null;
            try{
                groupId = Integer.parseInt(request.getParameter("groupId"));
            } catch(Exception ex){

            }
            if(groupId != null){
                Group group = new Group();
                group.setId(groupId);
                person.setGroup(group);
            }
            
            IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
            personAccess.addOrUpdateItem(person);
        } catch(Exception ex) {
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
    }

}