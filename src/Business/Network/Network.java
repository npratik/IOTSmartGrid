/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Network;

import Business.Enterprise.EnterpriseDirectory;
import Business.Organization.Organization;
import Business.Role.PowerRegulationAuthorityRole;
import Business.Role.Role;
import Business.Role.SystemAdminRole;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public abstract class Network extends Organization{
    //private String name;
    private EnterpriseDirectory enterpriseDirectory;
    private NetworkType networkType;
    
    
    public Network(String name, NetworkType type) {
        super(name);
        this.networkType = type;
        enterpriseDirectory = new EnterpriseDirectory();
    }
    

    public EnterpriseDirectory getEnterpriseDirectory() {
        return enterpriseDirectory;
    }
    
    public enum NetworkType{
        PowerRegulationAuthority("City Power Regulation Authority");
        
        private String value;

        private NetworkType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//   
//    @Override
//    public String toString() {
//        return name;
//    }
    
    @Override                     //Check**********
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList = new ArrayList<>();
        //roleList.add(new SystemAdminRole()); // craete network Admin Role*****
        roleList.add(new PowerRegulationAuthorityRole());
        return roleList;
    }
    
}
