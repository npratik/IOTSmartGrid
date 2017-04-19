/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Person.PersonDirectory;
import Business.PowerManagement.ProsumerDirectory;
import Business.Role.Role;
import Business.UserAccount.UserAccountDirectory;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueueEnterprise.WorkQueueEnterprise;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public abstract class Organization {
    
    private String name;
    private WorkQueue workQueue;
    private PersonDirectory employeeDirectory;
    private UserAccountDirectory userAccountDirectory;
    private int organizationID;
    private static int counter;
    private WorkQueueEnterprise workQueueEnterprise;
    
    //Check if the below mentioned attributes are required*******
    
    
    private ProsumerDirectory prosumerDirectory;

    /**
     * @return the personDirectory
     */
   

    /**
     * @return the prosumerDirectory
     */
    public ProsumerDirectory getProsumerDirectory() {
        return prosumerDirectory;
    }

    /**
     * @return the workQueueEnterprise
     */
    public WorkQueueEnterprise getWorkQueueEnterprise() {
        return workQueueEnterprise;
    }

    /**
     * @param workQueueEnterprise the workQueueEnterprise to set
     */
    public void setWorkQueueEnterprise(WorkQueueEnterprise workQueueEnterprise) {
        this.workQueueEnterprise = workQueueEnterprise;
    }
    
    public enum Type{
        Prosumer("Prosumer Organization"), Consumer("Consumer Organization");
//        Admin("Admin"),Prosumer("Prosumer Organization"), Consumer("Consumer Organization"), Community("Community Organization"),
//        City("City Organization"),Authority("PowerControlAuthority Organization");
        private String value;
        private Type(String value) { //check if this is constructor*******
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    public Organization(String name) {
        this.name = name;
        workQueue = new WorkQueue();
        workQueueEnterprise = new WorkQueueEnterprise();
        employeeDirectory = new PersonDirectory();
        userAccountDirectory = new UserAccountDirectory();
        prosumerDirectory = new ProsumerDirectory();
        organizationID = counter;
        ++counter;
    }

    
    
    public abstract ArrayList<Role> getSupportedRole(); //**** why role at hospital enterprise level******
    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public PersonDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }
    
    public String getName() {
        return name;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
