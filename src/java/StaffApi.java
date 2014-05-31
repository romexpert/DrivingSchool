import entities.AccountRole;
import entities.Person;
import entities.access.IItemsAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author admin
 */
public class StaffApi extends HttpServlet {

    private static final EnumSet<AccountRole> _staffRoles = EnumSet.of(AccountRole.Admin, AccountRole.Teacher);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!RoleHelper.IsInRole(request, response, AccountRole.Admin)){
            return;
        }
        
        //TODO
        //Handle "role" filter
        String roleParam = request.getParameter("role");
        AccountRole role = roleParam != null ? AccountRole.valueOf(roleParam) : null;
        IItemsAccess<Person> personAccess = AccessFactory.getAccessFactory().PeopleAccess();
        List<Person> staff;
        try {
            staff = personAccess.getAllItems();
            if(staff.size() < 1) {
                staff.add(new Person("Админ Андрюша", "admin@driftman.ru", "1 2345678", AccountRole.Admin));
                staff.add(new Person("Преподаватель 2", "teacher@driftman.ru", "2 2345678", AccountRole.Teacher));
                staff.add(new Person("Преподаватель 3", "teacher1@driftman.ru", "3 2345678", AccountRole.Teacher));
                staff.add(new Person("Преподаватель 4", "teacher2@driftman.ru", "4 2345678", AccountRole.Teacher));
        
                staff.add(new Person("Студент 5", "student@driftman.ru", "5 2345678", AccountRole.Student));
                staff.add(new Person("Студент 6", "student2@driftman.ru", "6 2345678", AccountRole.Student));
                staff.add(new Person("Студент 7", "student3@driftman.ru", "7 2345678", AccountRole.Student));
                
                
                personAccess.addOrUpdateItemsList(staff);
            }
            
            List<Person> result = staff.stream().filter(person -> (role == null && _staffRoles.contains(person.getAccountRole())) || person.getAccountRole() == role).collect(Collectors.toList());
        
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
        
    }

}