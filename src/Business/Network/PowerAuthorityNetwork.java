/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Network;

import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class PowerAuthorityNetwork extends Network{
    
    public PowerAuthorityNetwork(String name) {
        super(name, NetworkType.PowerRegulationAuthority);
    }

   
    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }
    
    
}
