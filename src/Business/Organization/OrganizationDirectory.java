/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Pratik
 */
public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;
    
    public OrganizationDirectory(){
        organizationList = new ArrayList<>();
    }
    
    public ArrayList<Organization> getOrganizationList(){
        return organizationList;
    }
    
//    public Organization createOrganization(Organization.Type type){
//        Organization organization = null;
////        if (type.getValue().equals(Organization.Type.City.getValue())){
////            organization = new CityOrganization();
////            organizationList.add(organization);
////        }
////        else 
////        if (type.getValue().equals(Organization.Type.Community.getValue())){
////            organization = new CommunityOrganization();
////            organizationList.add(organization);
////        }
////        else 
//        if(type.getValue().equals(Organization.Type.Consumer.getValue())){
//            organization = new ConsumerOrganization();
//            organizationList.add(organization);
//        } else if (type.getValue().equals(Organization.Type.Prosumer.getValue())){
//            organization = new ProsumerOrganization();
//            organizationList.add(organization);
//        }
//        return organization;
//    }
    
    
public Organization createOrganization(Organization.Type type){
     Organization organization = null;
     boolean isOrganizationPresent = false;
        for (Organization o : organizationList){
             if(o.getName().equalsIgnoreCase(type.getValue())){
                 isOrganizationPresent = true;
                 break;
             }
        }
        if(!isOrganizationPresent){
            
            if(type.getValue().equals(Organization.Type.Consumer.getValue())){
                    organization = new ConsumerOrganization();
                     organizationList.add(organization);
            }
            else if (type.getValue().equals(Organization.Type.Prosumer.getValue())){
            organization = new ProsumerOrganization();
            organizationList.add(organization);
            
            }
        }else{
            JOptionPane.showMessageDialog(null,"Organization already exists","Error",JOptionPane.ERROR_MESSAGE);
        }
                return organization;
    }           
}   
    


