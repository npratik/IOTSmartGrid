/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.ConsumerOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import UserInterface.ConsumerRole.ConsumerWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class ConsumerRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise,Network network, EcoSystem business) {
        return new ConsumerWorkAreaJPanel(userProcessContainer, account, (ConsumerOrganization)organization, enterprise);//(userProcessContainer, account, organization, business);
    //return null;
        //Check**** if these many are requiredto pass for Consumer****
    }
    
    
}
