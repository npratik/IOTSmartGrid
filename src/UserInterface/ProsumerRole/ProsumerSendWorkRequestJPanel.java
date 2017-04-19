/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.ProsumerRole;

import Business.Enterprise.CommunityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Organization.ProsumerOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class ProsumerSendWorkRequestJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private ProsumerOrganization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;
    //currentPowerUtilized, currentPowerInputGenerated, currentExcessPower
    private int currentPowerUtilized;
    private int currentPowerInputGenerated;
    private int currentExcessPower;

    /**
     * Creates new form ProsumerSendWorkRequestJPanel
     */
    public ProsumerSendWorkRequestJPanel(JPanel userProcessContainer, UserAccount account, ProsumerOrganization organization, Enterprise enterprise, int currentPowerUtilized, int currentPowerInputGenerated) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.enterprise = enterprise;
        this.userAccount = account;
//        this.currentPowerUtilized = currentPowerUtilized;
//        this.currentPowerInputGenerated = currentPowerInputGenerated;
//        this.currentExcessPower = currentExcessPower;

        txtCurrentUtilizedPower.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentUtilizedPower()));
        txtCurrentPowerInputGenerated.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentPowerGenerated()));
        txtCurrentExcessPower.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentExcessPower()));

//        txtCurrentUtilizedPower.setText(String.valueOf(currentPowerUtilized));
//        txtCurrentPowerInputGenerated.setText(String.valueOf(currentPowerInputGenerated));
//         
//        currentExcessPower = currentPowerInputGenerated - currentPowerUtilized;
//        txtCurrentExcessPower.setText(String.valueOf(currentExcessPower));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtCurrentUtilizedPower = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCurrentPowerInputGenerated = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();
        txtSendPower = new javax.swing.JTextField();
        txtCurrentExcessPower = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Current Utilized Power");

        txtCurrentUtilizedPower.setEnabled(false);
        txtCurrentUtilizedPower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentUtilizedPowerActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Current Power Input/Generated");

        txtCurrentPowerInputGenerated.setEnabled(false);
        txtCurrentPowerInputGenerated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPowerInputGeneratedActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Current Excess Power ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Send Power ");

        btnSend.setBackground(new java.awt.Color(0, 153, 102));
        btnSend.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSend.setForeground(new java.awt.Color(255, 255, 255));
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtSendPower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSendPowerActionPerformed(evt);
            }
        });

        txtCurrentExcessPower.setEnabled(false);
        txtCurrentExcessPower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentExcessPowerActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Share Power with Community");

        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("<< Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSend)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCurrentUtilizedPower)
                                    .addComponent(txtCurrentPowerInputGenerated)
                                    .addComponent(txtSendPower)
                                    .addComponent(txtCurrentExcessPower, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel1)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCurrentUtilizedPower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtCurrentPowerInputGenerated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCurrentExcessPower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSendPower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend)
                    .addComponent(jButton1))
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCurrentUtilizedPowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentUtilizedPowerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentUtilizedPowerActionPerformed

    private void txtCurrentPowerInputGeneratedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentPowerInputGeneratedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentPowerInputGeneratedActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        
         if (!ValidateSentPower()) {
                JOptionPane.showMessageDialog(this, "Enter Valid Number/Integer for Power to be sent", "Create Request", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        
        int sendPower = Integer.parseInt(txtSendPower.getText());
        currentExcessPower = Integer.parseInt(txtCurrentExcessPower.getText());
    
        if (Integer.parseInt(txtCurrentExcessPower.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry ! You cannot send Power generated, as there is no excess power", "Send Power", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (sendPower<= 0){
           JOptionPane.showMessageDialog(this, "Please enter a positive integer to Send Power", "Send Power", JOptionPane.ERROR_MESSAGE);
            return;  
        }

        if (sendPower > Integer.parseInt(txtCurrentExcessPower.getText())) {
            JOptionPane.showMessageDialog(this, "You cannot send more than the Excess Power generated", "Send Power", JOptionPane.ERROR_MESSAGE);
            return; //check***
        } else {
            
//            if (enterprise instanceof CommunityEnterprise){
//                for(UserAccount uA : enterprise.getUserAccountDirectory().getUserAccountList()){
//                    if(uA!=null){
//                        if(uA.getEmployee().getCommunity().equals(enterprise.getName())){
//                            
//                        } 
//                    }
//                }
//            }

            PowerManagementWorkRequest request = new PowerManagementWorkRequest();
            request.setProsumerSentpower(sendPower);
            request.setCurrentFalingShortPower(0); //power that is falling short - prosumer does not have any defficiency in power*
            request.setSender(userAccount);
            request.setStatus("Sent");
            if (organization != null) {
                organization.getWorkQueue().getWorkRequestList().add(request);
                userAccount.getWorkQueue().getWorkRequestList().add(request);
                enterprise.getWorkQueue().getWorkRequestList().add(request); //Check*** added new remove org queue if not required*****
            }
            //Setting Excess Power to Zero as the Power has been Sent to Community Head
            userAccount.getEmployee().getProsumer().setCurrentExcessPower(currentExcessPower - sendPower);
            txtCurrentExcessPower.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentExcessPower()));
            
            JOptionPane.showMessageDialog(this, "Thank you!, Excess Power generated has been sent to Community Admin", "Send Power", JOptionPane.INFORMATION_MESSAGE);
        }

        //request.setReceiver(enterprise.);
//        for ( UserAccount uAcc: enterprise.getUserAccountDirectory().getUserAccountList()){
//            
//            if (userAccount.getEmployee().getCommunity().equals(enterprise.getName())){
//                
//            }
//        }
//        if (userAccount.getEmployee().getCommunity().equals(enterprise.getName()))
//             enterprise.get
//        Organization org = null;
//        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()){
//            if (organization instanceof ProsumerOrganization){
//                org = organization;
//                break;
//            }
//        }
//        if (org!=null){
//            org.getWorkQueue().getWorkRequestList().add(request);
//            userAccount.getWorkQueue().getWorkRequestList().add(request);
//        }
        //Check**** as organization is already send on the panel, reusing it instead of this additional logic****

    }//GEN-LAST:event_btnSendActionPerformed

    private void txtSendPowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSendPowerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSendPowerActionPerformed

    private void txtCurrentExcessPowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentExcessPowerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentExcessPowerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        Component[] componentArray1 = userProcessContainer.getComponents();
        Component component = componentArray1[componentArray1.length - 1];
      
        ManageEnergyResoursesProsumerJPanel ManageEnergyResoursesProsumer = (ManageEnergyResoursesProsumerJPanel) component;
        ManageEnergyResoursesProsumer.populateRequestTable();
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtCurrentExcessPower;
    private javax.swing.JTextField txtCurrentPowerInputGenerated;
    private javax.swing.JTextField txtCurrentUtilizedPower;
    private javax.swing.JTextField txtSendPower;
    // End of variables declaration//GEN-END:variables


 private boolean ValidateSentPower() {
        try {
            Integer.parseInt(txtSendPower.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
