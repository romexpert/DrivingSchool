/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.util;

import entities.access.ILecturesAccess;
import entities.access.impl.LecturesAccess;

/**
 *
 * @author Екатерина
 */
public class AccessFactory {
    private static ILecturesAccess lecturesAccess = null;
    private static AccessFactory accessFactory = null;
    
    private AccessFactory() {
        
    }
    
    public static synchronized AccessFactory getAccessFactory() {
        if(accessFactory == null)
            accessFactory = new AccessFactory();
        return accessFactory;
    }
    
    public ILecturesAccess LecturesAccess() {
        if(lecturesAccess == null)
            lecturesAccess = new LecturesAccess();
        return lecturesAccess;
    }
}
