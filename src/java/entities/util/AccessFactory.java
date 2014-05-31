/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.util;

import entities.Lecture;
import entities.Person;
import entities.access.IItemsAccess;
import entities.access.impl.LecturesAccess;
import entities.access.impl.PeopleAccess;

/**
 *
 * @author Екатерина
 */
public class AccessFactory {
    private static IItemsAccess<Lecture> lecturesAccess = null;
    private static IItemsAccess<Person> peopleAccess = null;
    private static AccessFactory accessFactory = null;
    
    private AccessFactory() {
        
    }
    
    public static synchronized AccessFactory getAccessFactory() {
        if(accessFactory == null)
            accessFactory = new AccessFactory();
        return accessFactory;
    }
    
    public IItemsAccess LecturesAccess() {
        if(lecturesAccess == null)
            lecturesAccess = new LecturesAccess();
        return lecturesAccess;
    }
    
    public IItemsAccess<Person> PeopleAccess() {
        if(peopleAccess == null)
            peopleAccess = new PeopleAccess();
        return peopleAccess;
    }
}
