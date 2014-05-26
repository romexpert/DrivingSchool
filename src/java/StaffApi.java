import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
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
        ArrayList<Person> staff = new ArrayList<>();
        staff.add(new Person(1, "Админ Андрюша", "admin@driftman.ru", "1 2345678", AccountRole.Admin));
        staff.add(new Person(2, "Преподаватель 2", "teacher@driftman.ru", "2 2345678", AccountRole.Teacher));
        staff.add(new Person(3, "Преподаватель 3", "teacher1@driftman.ru", "3 2345678", AccountRole.Teacher));
        staff.add(new Person(4, "Преподаватель 4", "teacher2@driftman.ru", "4 2345678", AccountRole.Teacher));
        
        staff.add(new Person(5, "Студент 5", "student@driftman.ru", "5 2345678", AccountRole.Student));
        staff.add(new Person(6, "Студент 6", "student2@driftman.ru", "6 2345678", AccountRole.Student));
        staff.add(new Person(7, "Студент 7", "student3@driftman.ru", "7 2345678", AccountRole.Student));
        
        List<Person> result = staff.stream().filter(person -> (role == null && _staffRoles.contains(person.getRole())) || person.getRole() == role).collect(Collectors.toList());
        
        JSONArray data = new JSONArray();
        data.addAll(result);

        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}