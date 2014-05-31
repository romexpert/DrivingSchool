import entities.AccountRole;
import entities.Lecture;
import entities.access.ILecturesAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
        
        //TODO
        JSONArray data = new JSONArray();
        ILecturesAccess access = AccessFactory.getAccessFactory().LecturesAccess();
        try {
            List<Lecture> lectures = access.getAllLectures();
            if(lectures.size() < 1) {
                lectures.add(new Lecture(1, "Первая лекция", "Пройдена"));
                lectures.add(new Lecture(2, "Вторая лекция", "Пройдена"));
                lectures.add(new Lecture(3, "Третья лекция", "Не пройдена"));
                lectures.add(new Lecture(4, "Четверная лекция", "Не пройдена"));
                access.addOrUpdateLecturesPack(lectures);
            }
            else {
                access.removeLecture(lectures.get(0));
            }
            
            data.addAll(access.getAllLectures());
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}