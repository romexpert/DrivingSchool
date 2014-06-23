import entities.Account;
import entities.AccountRole;
import entities.Lecture;
import entities.LectureStatus;
import entities.LectureStatusEnum;
import entities.Person;
import entities.TestQuestion;
import entities.TestQuestionVariant;
import entities.util.AccessFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

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
        
        Integer lectureNumber = null;
        try{
            lectureNumber = Integer.parseInt(request.getParameter("number"));
        } catch(Exception ex){
            
        }
        
        if(lectureNumber == null){
        
            try {
                Person person = new Person();
                person.setId(((Account)request.getAttribute("account")).getId());

                for(int i = 1; i < 13; i++) {
                    Lecture lect = AccessFactory.LecturesAccess().getByNumber(i);
                    if(lect == null)
                    {
                        lect = new Lecture(i, "Лекция " + String.valueOf(i));
                        AccessFactory.LecturesAccess().addOrUpdateItem(lect);
                    }
                }

                person = AccessFactory.PeopleAccess().getItem(person.getId());
                Set<LectureStatus> personLectures = person.getLectures();
                if(personLectures.isEmpty()){
                    for(Lecture lecture : AccessFactory.LecturesAccess().getAllItems()){

                        LectureStatus stat = new LectureStatus();
                        stat.setPerson(person);
                        stat.setLecture(lecture);
                        stat.setStatus(LectureStatusEnum.NotTaken);
                        AccessFactory.LectureStatusAccess().addOrUpdateItem(stat);
                    }

                    person = AccessFactory.PeopleAccess().getItem(person.getId());
                }

                data.addAll(person.getLectures());
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
        } else {
            try{
                for(TestQuestion question : AccessFactory.TestsAccess().getAllItems()){
                    if(question.getLecture().getNumber() == lectureNumber)
                        data.add(question);
                }
            } catch(SQLException ex){
                throw new ServletException(ex);
            }
        }
        
        data.writeJSONString(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Integer lectureNumber = null;
        try{
            lectureNumber = Integer.parseInt(request.getParameter("lectureNumber"));
        } catch(Exception ex){
            throw new IOException();
        }
        
        JSONArray json = (JSONArray)JSONValue.parse(request.getReader());

        Map<Integer,Integer> idAnswer = new HashMap<Integer,Integer>();
        
        for(int i = 0; i < json.size(); i++){
            JSONObject item = (JSONObject)json.get(i);
            Integer id = Integer.parseInt(item.get("id").toString());
            Integer answer = Integer.parseInt(item.get("answer").toString());
            idAnswer.put(id, answer);
        }
        
        int lectureQuestions = 0;
        int correctAnswers = 0;
        try{
            for(TestQuestion question : AccessFactory.TestsAccess().getAllItems()){
                if(question.getLecture().getNumber() == lectureNumber){
                    lectureQuestions++;
                    
                    if(idAnswer.containsKey(question.getId())){
                        Integer answer = idAnswer.get(question.getId());
                        if(answer == question.getCorrectResult())
                            correctAnswers++;
                    }
                }
            }
            boolean result = correctAnswers/lectureQuestions > 0.7;
            
            Person person = AccessFactory.PeopleAccess().getItem(((Account)request.getAttribute("account")).getId());
            for(LectureStatus st : person.getLectures()){
                if(st.getLecture().getNumber() == lectureNumber){
                    LectureStatus stInt = AccessFactory.LectureStatusAccess().getItem(st.getIid());
                    stInt.setStatus(result ? LectureStatusEnum.Passed : LectureStatusEnum.Failed);
                    
                    AccessFactory.LectureStatusAccess().addOrUpdateItem(stInt);
                    break;
                }
            }
            
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("result", result);
            jsonResult.put("message", String.format("На %s вопросов из %s вы ответили верно. Тест%s пройден.", correctAnswers, lectureQuestions, result ? "" : " не"));
            
            jsonResult.writeJSONString(response.getWriter());
        } catch(Exception ex){
            throw new ServletException(ex);
        }
    }

}