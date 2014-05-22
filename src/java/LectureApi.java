import entities.Lecture;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;

/**
 *
 * @author admin
 */
public class LectureApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!RoleHelper.IsInRole(request, response, AccountRole.Student)){
            return;
        }
        
        response.setContentType("application/json; charset=utf-8");
        //TODO
        
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture(1, "Первая лекция", "Пройдена"));
        lectures.add(new Lecture(2, "Вторая лекция", "Пройдена"));
        lectures.add(new Lecture(3, "Третья лекция", "Не пройдена"));
        lectures.add(new Lecture(4, "Четверная лекция", "Не пройдена"));
        
        JSONArray data = new JSONArray();
        data.addAll(lectures);

        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}