import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author admin
 */
public class ExerciseApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        //TODO
        
        ArrayList<Exercise> lectures = new ArrayList<>();
        lectures.add(new Exercise(1, new Date(), "Пройдено"));
        lectures.add(new Exercise(2, new Date(), "Пройдено"));
        lectures.add(new Exercise(3, new Date(), "Не пройдено"));
        
        JSONArray data = new JSONArray();
        data.addAll(lectures);

        data.writeJSONString(response.getWriter());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
}
