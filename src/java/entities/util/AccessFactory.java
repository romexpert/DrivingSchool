/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.util;

import entities.access.impl.GroupsAccess;
import entities.access.impl.LectureStatusAccess;
import entities.access.impl.LecturesAccess;
import entities.access.impl.PeopleAccess;
import entities.access.impl.SessionAccess;
import entities.access.impl.TestsAccess;

/**
 *
 * @author Екатерина
 */
public class AccessFactory {
    private static LecturesAccess lecturesAccess = null;
    private static PeopleAccess peopleAccess = null;
    private static GroupsAccess groupsAccess = null;
    private static TestsAccess testsAccess = null;
    private static SessionAccess sessionAccess = null;
    private static LectureStatusAccess lectureStatusAccess = null;
    private static AccessFactory accessFactory = null;
    
    public static LecturesAccess LecturesAccess() {
        if(lecturesAccess == null)
            lecturesAccess = new LecturesAccess();
        return lecturesAccess;
    }
    
    public static PeopleAccess PeopleAccess() {
        if(peopleAccess == null)
            peopleAccess = new PeopleAccess();
        return peopleAccess;
    }
    
    public static GroupsAccess GroupsAccess() {
        if(groupsAccess == null)
            groupsAccess = new GroupsAccess();
        return groupsAccess;
    }
    
    public static TestsAccess TestsAccess() {
        if(testsAccess == null)
            testsAccess = new TestsAccess();
        return testsAccess;
    }
    
    public static LectureStatusAccess LectureStatusAccess() {
        if(lectureStatusAccess == null)
            lectureStatusAccess = new LectureStatusAccess();
        return lectureStatusAccess;

    public static SessionAccess SessionAccess() {
        if(sessionAccess == null)
            sessionAccess = new SessionAccess();
        return sessionAccess;

    }
}
