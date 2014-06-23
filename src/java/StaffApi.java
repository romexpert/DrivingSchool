import entities.AccountRole;
import entities.StudentGroup;
import entities.Person;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
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
        if(!RoleHelper.IsInRole(request, response, _staffRoles)){
            return;
        }
        
        //TODO
        Integer groupId = null;
        try{
            groupId = Integer.parseInt(request.getParameter("groupId"));
        } catch(Exception ex){
            
        }
        Integer instructorId = null;
        try{
            instructorId = Integer.parseInt(request.getParameter("instructorId"));
        } catch(Exception ex){
            
        }
        
        String roleParam = request.getParameter("role");
        AccountRole role = roleParam != null ? AccountRole.valueOf(roleParam) : null;
        try {
            List<Person> staff = AccessFactory.PeopleAccess().getAllItems();
            
            List<Person> result = new ArrayList();
            
            if(instructorId != null){
                result.addAll(AccessFactory.PeopleAccess().getItem(instructorId).getStudents());
            }
            //Get staff
            else if(groupId == null){
                for(Person item : staff)
                    if((role == null && _staffRoles.contains(item.getAccountRole())) || item.getAccountRole() == role)
                        result.add(item);
            //Get group students
            } else {
                StudentGroup group = AccessFactory.GroupsAccess().getItem(groupId);
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
        if(!RoleHelper.IsInRole(request, response, AccountRole.Admin)){
            return;
        }
        
        try {
            JSONObject json = (JSONObject)JSONValue.parse(request.getReader());
            
            String mail = json.get("mail").toString();
            String name = json.get("name").toString();
            String phone = "";
            try{
                phone = json.get("phone").toString();
            }catch(Exception ex){
                
            }
            
            AccountRole role = AccountRole.valueOf(json.get("role").toString());
            String password = json.get("password").toString();
            //TODO: validation
            
            Person person = new Person(name, mail, phone, role, password, null);
            
            Integer groupId = null;
            try{
                groupId = Integer.parseInt(request.getParameter("groupId"));
            } catch(Exception ex){

            }
            Integer instructorId = null;
            try{
                instructorId = Integer.parseInt(json.get("instructorId").toString());
            } catch(Exception ex){

            }
            
            if(groupId != null){
                StudentGroup group = new StudentGroup();
                group.setId(groupId);
                person.setGroup(group);
            }
            
            if(instructorId != null){
                Person instructor = new Person();
                instructor.setId(instructorId);
                person.setInstructor(instructor);
            }
            
            AccessFactory.PeopleAccess().addOrUpdateItem(person);
        } catch(Exception ex) {
            throw new IOException("Error parsing JSON request string" + ex.toString());
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!RoleHelper.IsInRole(request, response, AccountRole.Admin)){
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            AccessFactory.PeopleAccess().removeItem(AccessFactory.PeopleAccess().getItem(id));
        } catch(Exception ex) {
            throw new ServletException("Error during deleting");
        }
    }

}