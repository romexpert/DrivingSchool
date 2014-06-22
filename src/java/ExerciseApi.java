import entities.AccountRole;
import entities.Person;
import entities.PracticeStatus;
import entities.util.AccessFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
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
        if(!RoleHelper.IsInRole(request, response, EnumSet.of(AccountRole.Student, AccountRole.Instructor))){
            return;
        }
        
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        
        Person student;
        try{
            student = AccessFactory.PeopleAccess().getItem(studentId);
        
            Set<PracticeStatus> practices = student.getPractices();
            if(practices.size() == 0){
                for(int i = 1; i <= 20; i++){
                    PracticeStatus status = new PracticeStatus();
                    status.setPracticeNumber(i);
                    status.setStudent(student);
                    practices.add(status);
                }
                AccessFactory.PeopleAccess().addOrUpdateItem(student);
            }

            JSONArray data = new JSONArray();
            data.addAll(practices);

            data.writeJSONString(response.getWriter());
        
        } catch(Exception ex){
            throw new ServletException(ex);
        }
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
        if(!RoleHelper.IsInRole(request, response, AccountRole.Instructor)){
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            PracticeStatus status = AccessFactory.PracticeStatusAccess().getItem(id);
            status.setDate(new Date());
            status.setCompleted(true);
            AccessFactory.PracticeStatusAccess().addOrUpdateItem(status);
        } catch(Exception ex){
            throw new ServletException(ex);
        }
    }
    
}