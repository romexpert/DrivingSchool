import entities.AccountRole;
import entities.Lecture;
import entities.TestQuestion;
import entities.TestQuestionVariant;
import entities.access.IItemsAccess;
import entities.access.impl.LecturesAccess;
import entities.util.AccessFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        
        
        try {
            if(AccessFactory.TestsAccess().getAllItems().size() < 1) {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                    getServletContext().getRealPath("/images/questions.xml"));
            Node topArray = doc.getElementsByTagName("array").item(0);
            if(topArray != null)
            {
                int nLecture = 0;
                for(Node currentNode = topArray.getFirstChild(); currentNode != null; currentNode = currentNode.getNextSibling())
                {
                    if(!currentNode.getNodeName().equalsIgnoreCase("array"))
                        continue;
                    nLecture++;
                    Lecture currentLecture = AccessFactory.LecturesAccess().getByNumber(nLecture);
                    if(currentLecture == null)
                        continue;
                    for(Node currentQuestionNode = currentNode.getFirstChild();
                            currentQuestionNode != null;
                            currentQuestionNode = currentQuestionNode.getNextSibling())
                    {
                        if(!currentQuestionNode.getNodeName().equalsIgnoreCase("array"))
                            continue;
                        int nItem = 0;
                        TestQuestion newQuestion = new TestQuestion();
                        newQuestion.setLecture(currentLecture);
                        for(Node itemNode = currentQuestionNode.getFirstChild();
                                itemNode != null;
                                itemNode = itemNode.getNextSibling())
                        {
                            String sName = itemNode.getNodeName();
                            if(!(sName.equalsIgnoreCase("string") || sName.equalsIgnoreCase("integer")))
                                continue;
                            String sText;
                            Node textNode = itemNode.getFirstChild();
                            if(textNode == null)
                                sText = "";
                            else
                            {
                                sText = textNode.getNodeValue();
                                if(sText == null)
                                    sText = "";
                            }
                            switch(nItem) {
                                case 0:
                                    newQuestion.setQuestion(sText);
                                    break;
                                case 1:
                                    newQuestion.setImage(sText);
                                    break;
                                case 2:
                                    newQuestion.setComment(sText);
                                    break;
                                case 3:
                                    if(!sText.isEmpty())
                                        newQuestion.setCorrectResult(Integer.parseInt(sText));
                                    break;
                                default:
                                    TestQuestionVariant variant = new TestQuestionVariant();
                                    variant.setNumber(nItem - 3);
                                    variant.setText(sText);
                                    variant.setQuestion(newQuestion);
                                    newQuestion.getVariants().add(variant);
                                    break;
                            }
                            nItem++;
                        }
                        if(newQuestion.getQuestion() != null && !newQuestion.getQuestion().isEmpty())
                            AccessFactory.TestsAccess().addOrUpdateItem(newQuestion);
                        
                    }
                }
            }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        
        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}