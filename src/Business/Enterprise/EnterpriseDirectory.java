/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class EnterpriseDirectory {
     private ArrayList<Enterprise> enterpriseList;

    public EnterpriseDirectory() {
        enterpriseList = new ArrayList<>();
    }

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }
    
    public Enterprise createAndAddEnterprise(String name, Enterprise.EnterpriseType type){
        Enterprise enterprise = null;
//        if (type == Enterprise.EnterpriseType.City){
//            enterprise = new CityEnterprise(name);
//            enterpriseList.add(enterprise);
//        } else 
        if(type == Enterprise.EnterpriseType.Community){
            enterprise = new CommunityEnterprise(name);
            enterpriseList.add(enterprise);
//        } else if(type == Enterprise.EnterpriseType.HouseHold){
//            enterprise = new HouseHoldEnterprise(name);
//            enterpriseList.add(enterprise);
        }else if(type == Enterprise.EnterpriseType.CommunityEngerReserve){
            enterprise = new CommunityEnergyReserveEnterprise(name);
            enterpriseList.add(enterprise);
        }
        return enterprise;
    }
    
     public boolean checkIfEnterpriseNameIsUnique(String name) {
        for (Enterprise ent : enterpriseList) {
            if (name.equalsIgnoreCase(ent.getName())) {
                return false;
            }
        }
        return true;
    }
}
