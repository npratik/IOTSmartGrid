/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.CommunityAdminRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.ConsumerOrganization;
import Business.Organization.Organization;
import Business.Organization.ProsumerOrganization;
//import Business.Organization.CommunityOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Pratik
 */
public class CommunityAdminWorkAreaJPanel extends javax.swing.JPanel {
//userProcessContainer, account, organization,enterprise, business
//userProcessContainer, account, (CommunityOrganization)organization, enterprise    

    JPanel userProcessContainer;
    Enterprise enterprise;
    private Organization organization;
    private UserAccount userAccount;
    private Network network;
    private EcoSystem business;

    private int currentPowerRequired;
    private int currentPowerInputGenerated;
    private int currentPowerFallingShort;
    private int checkCurrentPowerGenerated;
    private String resetConsumerSensorData;

    private int currentPowerUtilized;
    //private int currentPowerInputGenerated;
    private int currentExcessPower;

    /**
     * Creates new form CommunityAdminJPanel
     */
    public CommunityAdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, Network network, EcoSystem business) {//(JPanel userProcessContainer,Enterprise enterprise ) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.enterprise = enterprise;
        this.userAccount = account;
        this.network = network;
        this.business = business;

        resetConsumerSensorData = "N";
        // Create the sensor Data for consumers
        automateWorkRequestsConsumer();
        automateWorkRequestProsumer();

    }

    public void automateWorkRequestsConsumer() {

        Organization org = null;
        for (Organization organize : enterprise.getOrganizationDirectory().getOrganizationList()) {
            if (organize instanceof ConsumerOrganization) {
                org = organize;
            }
        }

        //check ifthere are any pending/requests 
        //here this is checked for only Consumer organization
//        String checkRequestStatus = "N";
//        if (org != null) {
//            for (WorkRequest WR : org.getWorkQueue().getWorkRequestList()) {
//                if (WR.getStatus() == "Sent") {
//                    checkRequestStatus = "Y";
//                }
//            }
//        }
        //if (checkRequestStatus == "N") {
        if (org != null) { //&& (org instanceof ConsumerOrganization))
            for (UserAccount consumerUA : org.getUserAccountDirectory().getUserAccountList()) {
              if(consumerUA!=null){
                if (consumerUA.getEmployee().getCommunity().equals(enterprise.getName())) {

                    Random r1 = new Random();
                    currentPowerInputGenerated = r1.nextInt(100 - 60) + 60;

                    Random r2 = new Random();
                    currentPowerRequired = r2.nextInt(150 - 100) + 104;

                    currentPowerFallingShort = currentPowerRequired - currentPowerInputGenerated;

                    checkCurrentPowerGenerated = consumerUA.getEmployee().getProsumer().getCurrentPowerGenerated();

                    if (checkCurrentPowerGenerated <= 0) {//if ((checkCurrentPowerGenerated <= 0) || (resetConsumerSensorData == "Y")) {
                        consumerUA.getEmployee().getProsumer().setCurrentPowerFallingShort(currentPowerFallingShort);
                        consumerUA.getEmployee().getProsumer().setCurrentPowerGenerated(currentPowerInputGenerated);
                        consumerUA.getEmployee().getProsumer().setCurrentPowerRequired(currentPowerRequired);
                    }
                    String checkUserAccountConsumExists = "N";
                    String checkRequestStillProcessingConsum = "N";
                    //for (WorkRequest WR : consumerUA.getWorkQueue().getWorkRequestList()) {
                    for (WorkRequest WR : enterprise.getWorkQueue().getWorkRequestList()) {
                        if (WR != null) {
                            if (WR.getSender().equals(consumerUA)) {
                                checkUserAccountConsumExists = "Y";
                                   if (WR.getStatus() == "Sent") {
                                        checkRequestStillProcessingConsum = "Y";
                                   }
                            }
//                            if (WR.getStatus() == "Sent") {
//                                checkRequestStillProcessingConsum = "Y";
//                            }
                        }
                    }
                    
                    if ((checkUserAccountConsumExists == "Y") && (checkRequestStillProcessingConsum == "Y")) {
                         //Dont raise a request****
                    }else { //raise a request
  
                    //if ((checkUserAccountConsumExists == "N") || (checkRequestStillProcessingConsum == "N")) {
                      if(consumerUA.getEmployee().getProsumer().getCurrentPowerFallingShort() > 0){  
                        PowerManagementWorkRequest request = new PowerManagementWorkRequest();
                        request.setProsumerSentpower(0);
                        request.setCurrentFalingShortPower(consumerUA.getEmployee().getProsumer().getCurrentPowerFallingShort()); //power that is falling short - prosumer does not have any defficiency in power*
                        request.setSender(consumerUA);
                        request.setReceiver(userAccount);
                        request.setStatus("Sent");
                       // org.getWorkQueue().getWorkRequestList().add(request);
                        consumerUA.getWorkQueue().getWorkRequestList().add(request);
                        enterprise.getWorkQueue().getWorkRequestList().add(request);
                      }
                    }
                }
              }  
            }
        }
        //}
        // }
    }

    public void automateWorkRequestProsumer() {
        Organization orgPro = null;
        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            if (organization instanceof ProsumerOrganization) {
                orgPro = organization;
            }
        }

//        String checkRequestStatusProsumer = "N";
//        if (orgPro != null) {
//            for (WorkRequest WR : orgPro.getWorkQueue().getWorkRequestList()) {
//                if (WR.getStatus() == "Sent") {
//                    checkRequestStatusProsumer = "Y";
//                }
//                
//                
//            }
//        }
        if (orgPro != null) { //&& (org instanceof ConsumerOrganization))
            for (UserAccount prosumerUA : orgPro.getUserAccountDirectory().getUserAccountList()) {
                if (prosumerUA.getEmployee().getCommunity().equals(enterprise.getName())) {

                    Random r1 = new Random();
                    currentPowerUtilized = r1.nextInt(100 - 60) + 60;

                    Random r2 = new Random();
                    currentPowerInputGenerated = r2.nextInt(220 - 100) + 104;

                    currentExcessPower = currentPowerInputGenerated - currentPowerUtilized;

                    checkCurrentPowerGenerated = prosumerUA.getEmployee().getProsumer().getCurrentPowerGenerated();

                    if (checkCurrentPowerGenerated <= 0) {
                        prosumerUA.getEmployee().getProsumer().setCurrentExcessPower(currentExcessPower);
                        prosumerUA.getEmployee().getProsumer().setCurrentPowerGenerated(currentPowerInputGenerated);
                        prosumerUA.getEmployee().getProsumer().setCurrentUtilizedPower(currentPowerUtilized);
                    }

                    String checkUserAccountExists = "N";
                    String checkRequestStillProcessingPros = "N";
                    if (prosumerUA.getEmployee().getCommunity().equals(enterprise.getName())) {
                        if (prosumerUA.getEmployee().getProsumer().getAutomatePowerDistribution().equalsIgnoreCase("Y")) {
                            for (WorkRequest WR : prosumerUA.getWorkQueue().getWorkRequestList()) {
                                if (WR != null) {
                                    if (WR.getSender().equals(prosumerUA)) {
                                        checkUserAccountExists = "Y";
                                    }
                                    String statusCheck = WR.getStatus();
                                    if (WR.getStatus() == "Sent") {
                                        checkRequestStillProcessingPros = "Y";
                                    }
                                    // if ((WR.getSender().equals(request.getSender()))
                                    //Need to add logic to send only one request
                                }
                            }
                            if ((checkUserAccountExists == "N") || (checkRequestStillProcessingPros == "N")) {
                                PowerManagementWorkRequest requestProsum = new PowerManagementWorkRequest();

                                int proTresholdAmount = prosumerUA.getEmployee().getProsumer().getSetThresholdForPowerSupply();
                                int procurrentExcessPower = prosumerUA.getEmployee().getProsumer().getCurrentExcessPower();
                                if (proTresholdAmount > procurrentExcessPower) {
                                    //Cant sent power;
                                } else { //current is greater than or equal to treshold amount, send whole excess amount.
                                    //int sendPower = currentExcessPower; //- proTresholdAmount;
                                    requestProsum.setProsumerSentpower(procurrentExcessPower);
                                    requestProsum.setCurrentFalingShortPower(0); //power that is falling short - prosumer does not have any defficiency in power*
                                    requestProsum.setSender(prosumerUA);
                                    requestProsum.setReceiver(userAccount);
                                    requestProsum.setStatus("Sent");
                                    orgPro.getWorkQueue().getWorkRequestList().add(requestProsum);
                                    prosumerUA.getWorkQueue().getWorkRequestList().add(requestProsum);
                                    prosumerUA.getEmployee().getProsumer().setCurrentExcessPower(0);
                                    //Also adjust other prosumer fields if required******
                                    enterprise.getWorkQueue().getWorkRequestList().add(requestProsum);
                                    
                                }
                            }
                        }
                    }
                }
            }

        }
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
        btnManageProsumerConsumer = new javax.swing.JButton();
        btnManageProsumerConsumer1 = new javax.swing.JButton();
        btnManageProsumerConsumer2 = new javax.swing.JButton();
        manageOrganizationJButton = new javax.swing.JButton();
        btnAutomateWorkFlow = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Community Admin Work Area");

        btnManageProsumerConsumer.setBackground(new java.awt.Color(0, 153, 102));
        btnManageProsumerConsumer.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnManageProsumerConsumer.setForeground(new java.awt.Color(255, 255, 255));
        btnManageProsumerConsumer.setText("Manage Prosumer / Consumer");
        btnManageProsumerConsumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageProsumerConsumerActionPerformed(evt);
            }
        });

        btnManageProsumerConsumer1.setBackground(new java.awt.Color(0, 153, 102));
        btnManageProsumerConsumer1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnManageProsumerConsumer1.setForeground(new java.awt.Color(255, 255, 255));
        btnManageProsumerConsumer1.setText("Manage Power Allocation");
        btnManageProsumerConsumer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageProsumerConsumer1ActionPerformed(evt);
            }
        });

        btnManageProsumerConsumer2.setBackground(new java.awt.Color(0, 153, 102));
        btnManageProsumerConsumer2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnManageProsumerConsumer2.setForeground(new java.awt.Color(255, 255, 255));
        btnManageProsumerConsumer2.setText("View Data Analytics");
        btnManageProsumerConsumer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageProsumerConsumer2ActionPerformed(evt);
            }
        });

        manageOrganizationJButton.setBackground(new java.awt.Color(0, 153, 102));
        manageOrganizationJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        manageOrganizationJButton.setForeground(new java.awt.Color(255, 255, 255));
        manageOrganizationJButton.setText("Manage Organization");
        manageOrganizationJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageOrganizationJButtonActionPerformed(evt);
            }
        });

        btnAutomateWorkFlow.setBackground(new java.awt.Color(0, 153, 102));
        btnAutomateWorkFlow.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAutomateWorkFlow.setForeground(new java.awt.Color(255, 255, 255));
        btnAutomateWorkFlow.setText("Automate Work Flow");
        btnAutomateWorkFlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutomateWorkFlowActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/CommunityAdminRole/Substation_Image_f.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnManageProsumerConsumer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnManageProsumerConsumer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnManageProsumerConsumer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAutomateWorkFlow, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(manageOrganizationJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(157, 157, 157))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(39, 39, 39)
                .addComponent(manageOrganizationJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnManageProsumerConsumer, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnManageProsumerConsumer1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAutomateWorkFlow, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btnManageProsumerConsumer2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageProsumerConsumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageProsumerConsumerActionPerformed
        // TODO add your handling code here:
//        for (UserAccount Ua : network.getUserAccountDirectory().getUserAccountList()) {
//            Ua.
//        }
        ManageProsumerJPanel manageProsumer = new ManageProsumerJPanel(userProcessContainer,network, enterprise);
        userProcessContainer.add("manageProsumer", manageProsumer);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);

    }//GEN-LAST:event_btnManageProsumerConsumerActionPerformed

    private void btnManageProsumerConsumer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageProsumerConsumer1ActionPerformed
        // TODO add your handling code here:
        
        ProsumerManageReceivingPowerJPanel prosumerManageReceivingPower = new ProsumerManageReceivingPowerJPanel(userProcessContainer, userAccount, organization, enterprise,network, business);
        userProcessContainer.add("prosumerManageReceivingPower", prosumerManageReceivingPower);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
//
//        ViewManagePowerAllocationJPanel viewManagePowerAllocation = new ViewManagePowerAllocationJPanel(userProcessContainer, userAccount, organization, enterprise, network, business);
//        userProcessContainer.add("viewManagePowerAllocation", viewManagePowerAllocation);
//
//        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
//        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnManageProsumerConsumer1ActionPerformed

    private void manageOrganizationJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageOrganizationJButtonActionPerformed

        ManageOrganizationJPanel manageOrganizationJPanel = new ManageOrganizationJPanel(userProcessContainer, enterprise.getOrganizationDirectory());
        userProcessContainer.add("manageOrganizationJPanel", manageOrganizationJPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_manageOrganizationJButtonActionPerformed

    private void btnManageProsumerConsumer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageProsumerConsumer2ActionPerformed
        // TODO add your handling code here:
        CommunityViewDataAnalyticsJPanel communityViewDataAnalytics = new CommunityViewDataAnalyticsJPanel(userProcessContainer, userAccount, enterprise, network);
        userProcessContainer.add("communityViewDataAnalytics", communityViewDataAnalytics);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnManageProsumerConsumer2ActionPerformed

    private void btnAutomateWorkFlowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutomateWorkFlowActionPerformed
        // TODO add your handling code here:
        
         CommunityAutomateWorkRequestJPanel communityAutomateWorkRequest = new CommunityAutomateWorkRequestJPanel(userProcessContainer, userAccount, enterprise, network);
        userProcessContainer.add("communityAutomateWorkRequest", communityAutomateWorkRequest);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnAutomateWorkFlowActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutomateWorkFlow;
    private javax.swing.JButton btnManageProsumerConsumer;
    private javax.swing.JButton btnManageProsumerConsumer1;
    private javax.swing.JButton btnManageProsumerConsumer2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton manageOrganizationJButton;
    // End of variables declaration//GEN-END:variables
}
