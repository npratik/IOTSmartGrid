/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.ProsumerOrganization;
import Business.UserAccount.UserAccount;
import UserInterface.ProsumerRole.ProsumerWorkAreaPanel;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class ProsumerRole extends Role{
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise,Network network, EcoSystem business) {
    // Need to add***    return new DoctorWorkAreaJPanel(userProcessContainer, account, (DoctorOrganization)organization, enterprise);
    return new ProsumerWorkAreaPanel(userProcessContainer, account, (ProsumerOrganization)organization, enterprise);
    }
}
