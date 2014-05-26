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
        staff.add(new Person(1, "Преподаватель 4", "teacher2@driftman.ru", "4 2345678", AccountRole.Teacher));
        
        List<Person> result = staff.stream().filter(person -> role == null || person.getRole() == role).collect(Collectors.toList());
        
        JSONArray data = new JSONArray();
        data.addAll(result);

        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}