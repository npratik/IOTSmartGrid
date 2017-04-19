/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Person.Person;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;

/**
 *
 * @author Pratik
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        EcoSystem system = EcoSystem.getInstance();
        
        //create a network
        //create an enterprise
        //initialze some organizations
        //create some employees
        // create user account for theemployees
        
        Person employee = system.getEmployeeDirectory().createEmployee("RRH");
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        
        return system;
    }
    
}
