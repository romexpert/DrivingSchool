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
            if(staff.size() < 1) {
                staff.add(new Person("Админ Андрюша", "admin@driftman.ru", "1 2345678", AccountRole.Admin, "admin@driftman.ru"));
                
                staff.add(new Person("Инструктор Андрюша", "instructor@driftman.ru", "2 2345678", AccountRole.Instructor, "instructor123"));
                staff.add(new Person("Инструктор 2", "instructor@driftman.ru", "3 2345678", AccountRole.Instructor, "instructor2123"));
                staff.add(new Person("Инструктор 3", "instructor@driftman.ru", "4 2345678", AccountRole.Instructor, "instructor3123"));
        
                staff.add(new Person("Андрюша", "student@driftman.ru", "5 2345678", AccountRole.Student, "student123"));
                staff.add(new Person("Студент 2", "student2@driftman.ru", "6 2345678", AccountRole.Student, "student2123"));
                staff.add(new Person("Студент 3", "student3@driftman.ru", "7 2345678", AccountRole.Student, "student3123"));
                
                
                personAccess.addOrUpdateItemsList(staff);
            }
            
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
            
            Person person = new Person(name, mail, phone, role, password);
            
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