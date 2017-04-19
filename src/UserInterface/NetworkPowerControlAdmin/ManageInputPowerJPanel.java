/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.NetworkPowerControlAdmin;

import Business.Person.Person;
//import Business.Enterprise.CityEnterprise;
import Business.Enterprise.CommunityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.ProsumerOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueueEnterprise.PowerManagementWorkRequestEnterprise;
import Business.WorkQueue.WorkRequest;
import UserInterface.CommunityAdminRole.ProcessEachWorkRequestJPanel;
import java.awt.CardLayout;
import static java.lang.Math.abs;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pratik
 */
public class ManageInputPowerJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    //private EcoSystem system;
    private Network network;
    private UserAccount userAccount;
    private int totalExcessPower;
    private int totalRequiredPower;
    private int totalPowerFromCommunity;
    private int totalPowerFallingShortFromCommunity;
    private int rowCount;
    private String sentRequestsAvailable;
    private String powerCompensated;
    /**
     * Creates new form ManageInputPowerJPanel
     */
    public ManageInputPowerJPanel(JPanel userProcessContainer, UserAccount userAccount, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.network = network;
        this.userAccount = userAccount;
        populateTable();
        txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
        txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
        txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
        AutomateCityWorkRequest();
    }

    public void populateTable() {
        DefaultTableModel model = (DefaultTableModel) workRequestJTable.getModel();
        totalExcessPower = 0;
        totalRequiredPower = 0;
        model.setRowCount(0);
        rowCount = 0;
        totalPowerFromCommunity = 0;
        totalPowerFallingShortFromCommunity = 0;
        sentRequestsAvailable = "N";

       
                for (PowerManagementWorkRequestEnterprise request : network.getWorkQueueEnterprise().getWorkRequestList()) {// org.getWorkQueue().getWorkRequestList()) {

                    if (request.getSender().getEmployee().getCity().equals(network.getName())) {
                        Object[] row = new Object[6];
                        row[0] = request;
                        row[1] = request.getSender().getEmployee().getName();
                        row[2] = userAccount.getEmployee().getName();//request.getReceiver() == null ? null : request.getReceiver().getEmployee().getName();

                        request.setReceiver(userAccount); //check
                        row[3] = request.getStatus();
                        row[4] = request.getCommunitySentPower();
                        row[5] = request.getCurrentFalingShortPower();
                        
                        if( request.getStatus().equalsIgnoreCase("Sent")){
                            sentRequestsAvailable = "Y";
                            totalPowerFromCommunity = totalPowerFromCommunity + request.getCommunitySentPower();
                            totalPowerFallingShortFromCommunity = totalPowerFallingShortFromCommunity + request.getCurrentFalingShortPower();
                        }
                        
                        model.addRow(row);
                    }
                }
               
                 rowCount = model.getRowCount();
    }
    
    public void AutomateCityWorkRequest() {
        
       if (userAccount.getEmployee().getProsumer().getAutomatePowerDistribution().equalsIgnoreCase("Y")) {  
        if (totalPowerFallingShortFromCommunity > userAccount.getEmployee().getProsumer().getSetThresholdForPowerSupply()) {   
        for (PowerManagementWorkRequestEnterprise energyConserveRequest : network.getWorkQueueEnterprise().getWorkRequestList()) {// org.getWorkQueue().getWorkRequestList()) { 
        if (energyConserveRequest != null) {
            if (energyConserveRequest.getStatus().equals("Sent")) { // Request is Still Not Processed******
                //int powerfallingshortCheck = request.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                //if (energyConserveRequest.getBulkRequest().equals("N")) {
                int communitySentPower = energyConserveRequest.getCommunitySentPower();
                int communityShortFall = energyConserveRequest.getCurrentFalingShortPower();
                int cityNetworkAvailablePower = userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower();
                // City Level has unlimited power logically as an assumption
                int resultantPowerAtCityNetwork = 0;
                int currentTotalCommunityPower = 0;
                int netPowerCommunityPower = 0;
                if ((communitySentPower > 0) && (communityShortFall == 0)) { //Check* ==0 ******
                    //update the community and energy Reserve Power*****
                    //Also the requests of community and Energy Reserve*
                    resultantPowerAtCityNetwork = cityNetworkAvailablePower + communitySentPower;

                    //increase energy reserve power
                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Decrease community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower - communitySentPower;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    String powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        powerCompensated = "Y";
                    } else { //Bulk Process*****
                       // if (communityEnterprise != null) {
                            if(energyConserveRequest.getCommunityEnterprise()!=null) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                            WR.setMessage("Request has been Processed");
                                            powerCompensated = "Y";
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }  
                       // }
                    }
                    populateTable();
                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                       // JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
                    populateTable();
                   // txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
                   // txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
                  //  txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }
                //Add Logic for Community Short Fall****
                resultantPowerAtCityNetwork = 0;
                currentTotalCommunityPower = 0;
                netPowerCommunityPower = 0;
                if (communityShortFall > 0) {
                    
                    //This Scenario will ideally never occur
                    if (cityNetworkAvailablePower < communityShortFall) {
                        JOptionPane.showMessageDialog(this, "Power at City Network is falling Short, This request should be processed Only after required power is made available or generated", "Single Request", JOptionPane.ERROR_MESSAGE);   //Check if any other flow can be performed here *****
                        return;
                    }

                    resultantPowerAtCityNetwork = cityNetworkAvailablePower - communityShortFall;

                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Increase community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower + communityShortFall;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        //Single consumer request are not processed, so we might not need additional logic as written in else part
                        //check*****
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        energyConserveRequest.getPowerManagementWorkRequest().setMessage("Request has been Processed");
                        powerCompensated = "Y";
                    } else {
                       // if (communityEnterprise != null) {
                         if(energyConserveRequest.getCommunityEnterprise()!=null) {
                            //for (WorkRequest WR : communityEnterprise.getWorkQueue().getWorkRequestList()) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            WR.setMessage("Request has been Processed");
                                            int curExcessPower = WR.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                                            int curFallingShorPower = WR.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                                            if (curFallingShorPower > 0) {
                                                ((PowerManagementWorkRequest) WR).setResult("Compensated");
                                                WR.getSender().getEmployee().getProsumer().setCurrentPowerFallingShort(0);
                                            } else {
                                                ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                                WR.getSender().getEmployee().getProsumer().setCurrentExcessPower(0);
                                                powerCompensated = "Y";
                                            }
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    populateTable();

                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                        //JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
//                   txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
//                    txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
//                    txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }

            }
        }
       }
     }
       } 
       populateTable();
        txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
        txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
        txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        refreshJButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTotalAvailablePowerFrmCommunity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTotalPowerFallingShortCommunity = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        workRequestJTable = new javax.swing.JTable();
        BtnProcessBulkRequest = new javax.swing.JButton();
        BtnProcessEachRequest = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTotalAvailablePowerAtCityNetwork = new javax.swing.JTextField();
        backJButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 153, 153));

        refreshJButton.setBackground(new java.awt.Color(0, 153, 102));
        refreshJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        refreshJButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshJButton.setText("Refresh");
        refreshJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshJButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Manage Input/Receiving Power");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("Total Availble Power From Communities");

        txtTotalAvailablePowerFrmCommunity.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Total Power Falling Short ");

        txtTotalPowerFallingShortCommunity.setEnabled(false);

        workRequestJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Message", "Sender", "Receiver", "Status", "Power Available", "Power Falling Short"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(workRequestJTable);

        BtnProcessBulkRequest.setBackground(new java.awt.Color(0, 153, 102));
        BtnProcessBulkRequest.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BtnProcessBulkRequest.setForeground(new java.awt.Color(255, 255, 255));
        BtnProcessBulkRequest.setText("Process Bulk Request");
        BtnProcessBulkRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProcessBulkRequestActionPerformed(evt);
            }
        });

        BtnProcessEachRequest.setBackground(new java.awt.Color(0, 153, 102));
        BtnProcessEachRequest.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BtnProcessEachRequest.setForeground(new java.awt.Color(255, 255, 255));
        BtnProcessEachRequest.setText("Process Single Request");
        BtnProcessEachRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProcessEachRequestActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("Total PowerAvailable at City Network ");

        txtTotalAvailablePowerAtCityNetwork.setEnabled(false);

        backJButton.setBackground(new java.awt.Color(0, 153, 102));
        backJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        backJButton.setForeground(new java.awt.Color(255, 255, 255));
        backJButton.setText("<< Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalPowerFallingShortCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalAvailablePowerAtCityNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalAvailablePowerFrmCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(backJButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnProcessEachRequest)
                        .addGap(97, 97, 97)
                        .addComponent(BtnProcessBulkRequest)))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshJButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(206, 206, 206)
                    .addComponent(jLabel1)
                    .addContainerGap(379, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(refreshJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalAvailablePowerFrmCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPowerFallingShortCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotalAvailablePowerAtCityNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnProcessEachRequest)
                    .addComponent(BtnProcessBulkRequest))
                .addGap(39, 39, 39)
                .addComponent(backJButton)
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel1)
                    .addContainerGap(442, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshJButtonActionPerformed
        // populateTable();
    }//GEN-LAST:event_refreshJButtonActionPerformed

    private void BtnProcessBulkRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProcessBulkRequestActionPerformed
       
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "There are no Requests to process", "Bulk Requests", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(sentRequestsAvailable == "Y"){
            
        }else{
            JOptionPane.showMessageDialog(this, "There are No Sent Request to Process", "Bulk Requests", JOptionPane.ERROR_MESSAGE); //Test*****
            return;
        }
         
       for (PowerManagementWorkRequestEnterprise energyConserveRequest : network.getWorkQueueEnterprise().getWorkRequestList()) {// org.getWorkQueue().getWorkRequestList()) { 
        if (energyConserveRequest != null) {
            if (energyConserveRequest.getStatus().equals("Sent")) { // Request is Still Not Processed******
                //int powerfallingshortCheck = request.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                //if (energyConserveRequest.getBulkRequest().equals("N")) {
                int communitySentPower = energyConserveRequest.getCommunitySentPower();
                int communityShortFall = energyConserveRequest.getCurrentFalingShortPower();
                int cityNetworkAvailablePower = userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower();
                // City Level has unlimited power logically as an assumption
                int resultantPowerAtCityNetwork = 0;
                int currentTotalCommunityPower = 0;
                int netPowerCommunityPower = 0;
                if ((communitySentPower > 0) && (communityShortFall == 0)) { //Check* ==0 ******
                    //update the community and energy Reserve Power*****
                    //Also the requests of community and Energy Reserve*
                    resultantPowerAtCityNetwork = cityNetworkAvailablePower + communitySentPower;

                    //increase energy reserve power
                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Decrease community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower - communitySentPower;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    String powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        powerCompensated = "Y";
                    } else { //Bulk Process*****
                       // if (communityEnterprise != null) {
                            if(energyConserveRequest.getCommunityEnterprise()!=null) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                            WR.setMessage("Request has been Processed");
                                            powerCompensated = "Y";
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }  
                       // }
                    }
                    populateTable();
                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                       // JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
                    populateTable();
                    txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
                    txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
                    txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }
                //Add Logic for Community Short Fall****
                resultantPowerAtCityNetwork = 0;
                currentTotalCommunityPower = 0;
                netPowerCommunityPower = 0;
                if (communityShortFall > 0) {
                    
                    //This Scenario will ideally never occur
                    if (cityNetworkAvailablePower < communityShortFall) {
                        JOptionPane.showMessageDialog(this, "Power at City Network is falling Short, This request should be processed Only after required power is made available or generated", "Single Request", JOptionPane.ERROR_MESSAGE);   //Check if any other flow can be performed here *****
                        return;
                    }

                    resultantPowerAtCityNetwork = cityNetworkAvailablePower - communityShortFall;

                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Increase community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower + communityShortFall;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        //Single consumer request are not processed, so we might not need additional logic as written in else part
                        //check*****
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        energyConserveRequest.getPowerManagementWorkRequest().setMessage("Request has been Processed");
                        powerCompensated = "Y";
                    } else {
                       // if (communityEnterprise != null) {
                         if(energyConserveRequest.getCommunityEnterprise()!=null) {
                            //for (WorkRequest WR : communityEnterprise.getWorkQueue().getWorkRequestList()) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            WR.setMessage("Request has been Processed");
                                            int curExcessPower = WR.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                                            int curFallingShorPower = WR.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                                            if (curFallingShorPower > 0) {
                                                ((PowerManagementWorkRequest) WR).setResult("Compensated");
                                                WR.getSender().getEmployee().getProsumer().setCurrentPowerFallingShort(0);
                                            } else {
                                                ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                                WR.getSender().getEmployee().getProsumer().setCurrentExcessPower(0);
                                                powerCompensated = "Y";
                                            }
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    populateTable();

                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                        JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
                    txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
                    txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
                    txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }

            }
        }
       }
    
    }//GEN-LAST:event_BtnProcessBulkRequestActionPerformed

    private void BtnProcessEachRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProcessEachRequestActionPerformed
        // TODO add your handling code here:
       int selectedRow = workRequestJTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "There are no Requests to process, Please select a request", "Single Requests", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Enterprise communityEnterprise = null;
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (enter instanceof CommunityEnterprise) {
                communityEnterprise = enter;
            }
        }

        PowerManagementWorkRequestEnterprise energyConserveRequest = (PowerManagementWorkRequestEnterprise) workRequestJTable.getValueAt(selectedRow, 0);
        if (energyConserveRequest != null) {
            if (energyConserveRequest.getStatus().equals("Sent")) { // Request is Still Not Processed******
                //int powerfallingshortCheck = request.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                //if (energyConserveRequest.getBulkRequest().equals("N")) {
                int communitySentPower = energyConserveRequest.getCommunitySentPower();
                int communityShortFall = energyConserveRequest.getCurrentFalingShortPower();
                int cityNetworkAvailablePower = userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower();
                // City Level has unlimited power logically as an assumption
                int resultantPowerAtCityNetwork = 0;
                int currentTotalCommunityPower = 0;
                int netPowerCommunityPower = 0;
                if ((communitySentPower > 0) && (communityShortFall == 0)) { //Check* ==0 ******
                    //update the community and energy Reserve Power*****
                    //Also the requests of community and Energy Reserve*
                    resultantPowerAtCityNetwork = cityNetworkAvailablePower + communitySentPower;

                    //increase energy reserve power
                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Decrease community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower - communitySentPower;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    String powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        powerCompensated = "Y";
                    } else { //Bulk Process*****
                       // if (communityEnterprise != null) {
                            if(energyConserveRequest.getCommunityEnterprise()!=null) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                            WR.setMessage("Request has been Processed");
                                            powerCompensated = "Y";
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }  
                       // }
                    }
                    populateTable();
                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                        //JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
                    populateTable();
                    txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
                    txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
                    txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }
                //Add Logic for Community Short Fall****
                resultantPowerAtCityNetwork = 0;
                currentTotalCommunityPower = 0;
                netPowerCommunityPower = 0;
                if (communityShortFall > 0) {
                    
                    //This Scenario will ideally never occur
                    if (cityNetworkAvailablePower < communityShortFall) {
                        JOptionPane.showMessageDialog(this, "Power at City Network is falling Short, This request should be processed Only after required power is made available or generated", "Single Request", JOptionPane.ERROR_MESSAGE);   //Check if any other flow can be performed here *****
                        return;
                    }

                    resultantPowerAtCityNetwork = cityNetworkAvailablePower - communityShortFall;

                    userAccount.getEmployee().getProsumer().setCityNetworkAvailablePower(resultantPowerAtCityNetwork);

                    //Increase community power
                    currentTotalCommunityPower = energyConserveRequest.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                    netPowerCommunityPower = currentTotalCommunityPower + communityShortFall;
                    energyConserveRequest.getSender().getEmployee().getProsumer().setCurrentExcessPower(netPowerCommunityPower);
                    //userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalExcessPower);                           
                    //energyConserveRequest.getSender().getEmployee().getProsumer().get

                    energyConserveRequest.setStatus("Processed");
                    energyConserveRequest.setResult("Accepted");
                    energyConserveRequest.setMessage("Request has been Processed");

                    String powerCompensated = "N";
                    if (energyConserveRequest.getBulkRequest().equals("N")) {
                        //Single consumer request are not processed, so we might not need additional logic as written in else part
                        //check*****
                        energyConserveRequest.getPowerManagementWorkRequest().setResult("Accepted");
                        energyConserveRequest.getPowerManagementWorkRequest().setStatus("Processed");
                        energyConserveRequest.getPowerManagementWorkRequest().setMessage("Request has been Processed");
                        powerCompensated = "Y";
                    } else {
                       // if (communityEnterprise != null) {
                         if(energyConserveRequest.getCommunityEnterprise()!=null) {
                            //for (WorkRequest WR : communityEnterprise.getWorkQueue().getWorkRequestList()) {
                             for (WorkRequest WR : energyConserveRequest.getCommunityEnterprise().getWorkQueue().getWorkRequestList()) {
                                if (WR.getStatus().equalsIgnoreCase("Processing")) {
                                    if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(energyConserveRequest.getCommunityEnterprise().getName())) {
                                            WR.setStatus("Processed");
                                            WR.setMessage("Request has been Processed");
                                            int curExcessPower = WR.getSender().getEmployee().getProsumer().getCurrentExcessPower();
                                            int curFallingShorPower = WR.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();

                                            if (curFallingShorPower > 0) {
                                                ((PowerManagementWorkRequest) WR).setResult("Compensated");
                                                WR.getSender().getEmployee().getProsumer().setCurrentPowerFallingShort(0);
                                            } else {
                                                ((PowerManagementWorkRequest) WR).setResult("Accepted");
                                                WR.getSender().getEmployee().getProsumer().setCurrentExcessPower(0);
                                                powerCompensated = "Y";
                                            }
                                            //energyConserveRequest.setStatus("Processed");
                                            //energyConserveRequest.setResult("Accepted");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    populateTable();

                    if (powerCompensated == "Y") {
                        energyConserveRequest.setReceiver(userAccount); //Setting Receiver once the request is processed
                       // JOptionPane.showMessageDialog(this, "The selected Request is Processed", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                    }
                    txtTotalAvailablePowerFrmCommunity.setText(Integer.toString(totalPowerFromCommunity));
                    txtTotalPowerFallingShortCommunity.setText(Integer.toString(totalPowerFallingShortFromCommunity));
                    txtTotalAvailablePowerAtCityNetwork.setText(Integer.toString(userAccount.getEmployee().getProsumer().getCityNetworkAvailablePower()));
                }

            } 
//            else {
//                JOptionPane.showMessageDialog(this, "The selected Request is either Processed or under Processing, Please select a new Request", "Single Requests", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
        }
        
        if(powerCompensated!=null){
            JOptionPane.showMessageDialog(this, "Bulk Request has been Processed", "Bulk Requests", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_BtnProcessEachRequestActionPerformed

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed
        userProcessContainer.remove(this);
        //        Component[] componentArray = userProcessContainer.getComponents();
        //        Component component = componentArray[componentArray.length - 1];
        //        SystemAdminWorkAreaJPanel sysAdminwjp = (SystemAdminWorkAreaJPanel) component;
        //        sysAdminwjp.populateTree();
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_backJButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnProcessBulkRequest;
    private javax.swing.JButton BtnProcessEachRequest;
    private javax.swing.JButton backJButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshJButton;
    private javax.swing.JTextField txtTotalAvailablePowerAtCityNetwork;
    private javax.swing.JTextField txtTotalAvailablePowerFrmCommunity;
    private javax.swing.JTextField txtTotalPowerFallingShortCommunity;
    private javax.swing.JTable workRequestJTable;
    // End of variables declaration//GEN-END:variables
}
