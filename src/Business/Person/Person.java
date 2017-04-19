/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import static Business.Organization.Organization.Type.Prosumer;
import Business.PowerManagement.PowerManagement;

/**
 *
 * @author Pratik
 */
public class Person {

    /**
     * @return the count
     */
    public static int getCount() {
        return count;
    }
    
    private String name;
    private int id;
    private static int count = 1;
    private String firstName;
    private String lastName;
    private String address;
    private String community; //Check**** if any other naming convention is to be used
    private String city;
    private String personId;
    
    private PowerManagement prosumer;
    
    
    public Person(){
        id = count;
        this.personId = "Person " + id;
        count++;
        //Prosumer Data 
        this.prosumer = new PowerManagement();
    }
    
    public int getId(){
        return id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the community
     */
    public String getCommunity() {
        return community;
    }

    /**
     * @param community the community to set
     */
    public void setCommunity(String community) {
        this.community = community;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @return the prosumer
     */
    public PowerManagement getProsumer() {
        return prosumer;
    }

    /**
     * @param prosumer the prosumer to set
     */
    public void setProsumer(PowerManagement prosumer) {
        this.prosumer = prosumer;
    }
}
