import entities.AccountRole;
import entities.Lecture;
import entities.access.IItemsAccess;
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
        IItemsAccess access = AccessFactory.getAccessFactory().LecturesAccess();
        try {
            List<Lecture> lectures = access.getAllItems();
            if(lectures.size() < 1) {
                lectures.add(new Lecture(1, "Первая лекция", "Пройдена"));
                lectures.add(new Lecture(2, "Вторая лекция", "Пройдена"));
                lectures.add(new Lecture(3, "Третья лекция", "Не пройдена"));
                lectures.add(new Lecture(4, "Четверная лекция", "Не пройдена"));
                access.addOrUpdateItemsList(lectures);
            }
            else {
                access.removeItem(lectures.get(0));
            }
            
            data.addAll(access.getAllItems());
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