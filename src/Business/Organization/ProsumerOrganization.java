/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.ProsumerRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class ProsumerOrganization extends Organization{

    public ProsumerOrganization() {
        super(Organization.Type.Prosumer.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ProsumerRole());
        return roles;
    }
     
    
}
