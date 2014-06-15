/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.util;

import entities.Group;
import entities.Lecture;
import entities.Person;
import entities.access.IItemsAccess;
import entities.access.impl.GroupsAccess;
import entities.access.impl.LecturesAccess;
import entities.access.impl.PeopleAccess;

/**
 *
 * @author Екатерина
 */
public class AccessFactory {
    private static LecturesAccess lecturesAccess = null;
    private static PeopleAccess peopleAccess = null;
    private static GroupsAccess groupsAccess = null;
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
}
