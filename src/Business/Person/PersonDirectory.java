/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class PersonDirectory {
    
    private ArrayList<Person> employeeList;
    
    public PersonDirectory(){
        employeeList = new ArrayList<>();
    }

    /**
     * @return the employeeList
     */
    public ArrayList<Person> getEmployeeList() {
        return employeeList;
    }

   public Person createEmployee(String name){
        Person employee = new Person();
        employee.setName(name);
        employeeList.add(employee);
        return employee;
    } 
   
   public boolean checkIfNameIsUnique(String username) {
        for (Person p : employeeList) {
            if (p.getName().equals(username)) {
                return false;
            }
        }
        return true;
    }
    
    
    
    
}
