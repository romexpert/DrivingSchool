/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access;

import entities.Lecture;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Екатерина
 */
public interface ILecturesAccess {
    public void addOrUpdateLecture(Lecture lecture) throws SQLException;
    public void addOrUpdateLecturesPack(List<Lecture> lectures) throws SQLException;
    public Lecture getLecture(int id) throws SQLException;
    public List getAllLectures() throws SQLException;
    public void removeLecture(Lecture lecture) throws SQLException;
}
