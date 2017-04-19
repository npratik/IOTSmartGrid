/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Network.PowerAuthorityNetwork;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import UserInterface.NetworkPowerControlAdmin.NetworkAdminWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class PowerRegulationAuthorityRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise,Network network, EcoSystem business) {
       // return new PowerRegulationAuthorityWorkAreaJPanel();//userProcessContainer, account, organization, business);
           return new NetworkAdminWorkAreaJPanel(userProcessContainer,account, (PowerAuthorityNetwork)network);// check****//userProcessContainer, account, organization, business); 
    }
    
    
}