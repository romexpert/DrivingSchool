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
public class TeacherApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account)request.getAttribute("account");
        if(AccountRole.Admin != account.getRole()){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        response.setContentType("application/json; charset=utf-8");
        //TODO
        
        ArrayList<Person> teachers = new ArrayList<>();
        teachers.add(new Person(1, "Преподаватель 1", "1 2345678", AccountRole.Teacher));
        teachers.add(new Person(2, "Преподаватель 2", "2 2345678", AccountRole.Teacher));
        teachers.add(new Person(3, "Преподаватель 3", "3 2345678", AccountRole.Teacher));
        
        JSONArray data = new JSONArray();
        data.addAll(teachers);

        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
