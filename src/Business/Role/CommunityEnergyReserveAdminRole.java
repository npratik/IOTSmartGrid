/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
//import Business.Network.PowerAuthorityNetwork;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import UserInterface.CommunityEnergyReserveRole.CommunityEnergyReserveAdminJPanel;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class CommunityEnergyReserveAdminRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise,Network network, EcoSystem business) {
        //return new CommunityAdminWorkAreaJPanel(userProcessContainer, account, organization,enterprise, business); //need to modify*****
        //return null; JPanel userProcessContainer;
    //Enterprise enterprise;
        //return new CommunityEnergyReserveAdminJPanel(userProcessContainer, account, (CommunityEnergyReserveEnterprise) enterprise, (PowerAuthorityNetwork)network);//(userProcessContainer,enterprise);
        return new CommunityEnergyReserveAdminJPanel(userProcessContainer, account, (CommunityEnergyReserveEnterprise) enterprise,network);
    }
    
}
