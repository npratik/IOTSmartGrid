/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.ConsumerRole;

import Business.Enterprise.Enterprise;
import Business.Organization.ConsumerOrganization;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class ConsumerWorkAreaJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private ConsumerOrganization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;
    /**
     * Creates new form ConsumerWorkAreaJPanel
     */
    public ConsumerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, ConsumerOrganization   organization, Enterprise enterprise) {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.enterprise = enterprise;
        this.userAccount = account;
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
        btnManageEnergyResources = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Consumer Work Area");

        btnManageEnergyResources.setBackground(new java.awt.Color(0, 153, 102));
        btnManageEnergyResources.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnManageEnergyResources.setForeground(new java.awt.Color(255, 255, 255));
        btnManageEnergyResources.setText("View Energy Usage");
        btnManageEnergyResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageEnergyResourcesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManageEnergyResources, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btnManageEnergyResources)
                .addContainerGap(228, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageEnergyResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageEnergyResourcesActionPerformed
        // TODO add your handling code here:

        ConsumerViewEnergyUsageJPanel consumerViewEnergyUsage = new ConsumerViewEnergyUsageJPanel(userProcessContainer, userAccount, (ConsumerOrganization)organization, enterprise);
        userProcessContainer.add("consumerViewEnergyUsage", consumerViewEnergyUsage); //similar to map functions*** >> ash map*** Check*** if this is required****
        CardLayout cardLayout = (CardLayout) userProcessContainer.getLayout();
        cardLayout.next(userProcessContainer);
    }//GEN-LAST:event_btnManageEnergyResourcesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnManageEnergyResources;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}