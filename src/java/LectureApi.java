import entities.AccountRole;
import entities.Lecture;
import entities.access.IItemsAccess;
import entities.access.impl.LecturesAccess;
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
        try {
            for(int i = 1; i < 13; i++) {
                if(AccessFactory.LecturesAccess().getByNumber(i) == null)
                    AccessFactory.LecturesAccess().addOrUpdateItem(new Lecture(i, "Лекция " + String.valueOf(i), "Не пройдена"));
            }
            data.addAll(AccessFactory.LecturesAccess().getAllItems());
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