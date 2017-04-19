/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.UserAccount;

import Business.Person.Person;
import Business.Role.Role;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueueEnterprise.WorkQueueEnterprise;

/**
 *
 * @author Pratik
 */
public class UserAccount {
    
    private String username;
    private String password;
    private Person employee;
    private Role role;
    private WorkQueue workQueue;
    private WorkQueueEnterprise workQueueEnterprise;

    public UserAccount() {
        workQueue = new WorkQueue();
        workQueueEnterprise = new WorkQueueEnterprise();
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the employee
     */
    public Person getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the workQueue
     */
    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    /**
     * @param workQueue the workQueue to set
     */
    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    
    @Override
    public String toString() {
        return username;
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
    
}
