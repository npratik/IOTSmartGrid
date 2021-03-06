/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.CommunityEnergyReserveRole;

import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Network.Network;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class CommunityEnergyReserveAdminJPanel extends javax.swing.JPanel {
    //userProcessContainer, account, (CommunityEnergyReserveEnterprise) enterprise, network
    
    private JPanel userProcessContainer;
    private CommunityEnergyReserveEnterprise enterprise;
    //private CommunityOrganization organization;  
    private UserAccount userAccount;
    private Network network;
    
    
    /**
     * Creates new form EnergyReserveAdminJPanel
     */
    public CommunityEnergyReserveAdminJPanel(JPanel userProcessContainer,UserAccount userAccount, CommunityEnergyReserveEnterprise enterprise,Network network ) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = userAccount;
        this.enterprise = enterprise;
        this.network = network;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        manageEnterpriseJButton = new javax.swing.JButton();
        manageEnterpriseJButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Energy Reserve Admin Work Area");

        manageEnterpriseJButton.setBackground(new java.awt.Color(0, 153, 102));
        manageEnterpriseJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        manageEnterpriseJButton.setForeground(new java.awt.Color(255, 255, 255));
        manageEnterpriseJButton.setText("Manage Energy Requests");
        manageEnterpriseJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageEnterpriseJButtonActionPerformed(evt);
            }
        });

        manageEnterpriseJButton2.setBackground(new java.awt.Color(0, 153, 102));
        manageEnterpriseJButton2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        manageEnterpriseJButton2.setForeground(new java.awt.Color(255, 255, 255));
        manageEnterpriseJButton2.setText("Automate Power Distribution");
        manageEnterpriseJButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageEnterpriseJButton2ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/CommunityEnergyReserveRole/EnergyReserve.PNG"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(manageEnterpriseJButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(manageEnterpriseJButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(51, 51, 51)
                .addComponent(manageEnterpriseJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(manageEnterpriseJButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void manageEnterpriseJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageEnterpriseJButtonActionPerformed

        ComEnergyReserveManageInputPowerJPanel comEnergyReserveManageInputPower = new ComEnergyReserveManageInputPowerJPanel(userProcessContainer,userAccount, enterprise,network );
        userProcessContainer.add("comEnergyReserveManageInputPower", comEnergyReserveManageInputPower);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_manageEnterpriseJButtonActionPerformed

    private void manageEnterpriseJButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageEnterpriseJButton2ActionPerformed
        // TODO add your handling code here:
        CommunityEnergyReserveAutomateRequestJPanel communityEnergyReserveAutomateRequest = new CommunityEnergyReserveAutomateRequestJPanel(userProcessContainer,userAccount, enterprise,network );
        userProcessContainer.add("communityEnergyReserveAutomateRequest", communityEnergyReserveAutomateRequest);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_manageEnterpriseJButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton manageEnterpriseJButton;
    private javax.swing.JButton manageEnterpriseJButton2;
    // End of variables declaration//GEN-END:variables
}
