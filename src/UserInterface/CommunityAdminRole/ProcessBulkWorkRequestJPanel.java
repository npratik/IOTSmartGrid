/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.CommunityAdminRole;

import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Enterprise.CommunityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueueEnterprise.PowerManagementWorkRequestEnterprise;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueue.WorkRequest;
import com.sun.xml.internal.bind.v2.schemagen.Util;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pratik
 */
public class ProcessBulkWorkRequestJPanel extends javax.swing.JPanel {

    //userProcessContainer,userAccount, totalExcessPower,totalRequiredPower, enterprise);
    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private int totalExcessPower;
    private int totalRequiredPower;
    private Enterprise enterprise;
    private Network network;
    private UserAccount uaMax;

    private int totalCurrentExcessPower;
    private int totalPowerFallingShort;
    private int totalRemainingPower;
    private String networkSentRequestsAvailable;

    private WorkQueue workQueueProConsumer;
    
    private String powerfallingShortAlreadyExists;
    private String sentPowerAlreadyExists;
    
    private Enterprise communityEnergyResrvEnterprise;

    /**
     * Creates new form ProcessBulkWorkRequestJPanel
     */
    public ProcessBulkWorkRequestJPanel(JPanel userProcessContainer, UserAccount userAccount, int totalExcessPower, int totalRequiredPower, Enterprise enterprise, Network network, WorkQueue workQueueProConsumer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = userAccount;
        this.enterprise = enterprise;
        this.network = network;
        this.workQueueProConsumer = workQueueProConsumer;
        this.totalExcessPower = totalExcessPower;
        this.totalRequiredPower = totalRequiredPower;
        populateTable();
        populateTableReserve();

        totalCurrentExcessPower = userAccount.getEmployee().getProsumer().getCurrentExcessPower();
        totalPowerFallingShort = userAccount.getEmployee().getProsumer().getCurrentPowerFallingShort();
        txtTotalAvailablePowerFrmProsumers.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentExcessPower()));
        txtTotalPowerFallingShortProsumers.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentPowerFallingShort()));

        energyReserveJComboBox.removeAllItems();
       
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
             Enterprise comEnergyReserve = null;
            if (enter != null) {
                if (enter instanceof CommunityEnergyReserveEnterprise) {
                    comEnergyReserve = enter;
                }
            }
            if (comEnergyReserve != null) {
                for (UserAccount ua : comEnergyReserve.getUserAccountDirectory().getUserAccountList()) {
                    energyReserveJComboBox.addItem(ua);
                    //populateTextField(ua);//(enter);
                }
                UserAccount selectedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();
                populateTextField(selectedUA);
            }
        }

    }

    private void populateTextField(UserAccount ua) {//(Enterprise enter) {

        if (ua != null) {
            txtPowerAtEnergyReserve.setText(Integer.toString(ua.getEmployee().getProsumer().getEnergyConserveAvailablePower()));
        }

    }

    public void populateTable() {
        DefaultTableModel model = (DefaultTableModel) workRequestJTable.getModel();
//        totalExcessPower = 0;
//        totalRequiredPower = 0;
        model.setRowCount(0);
        networkSentRequestsAvailable = "N";

        for (PowerManagementWorkRequestEnterprise request : network.getWorkQueueEnterprise().getWorkRequestList()) {
            //if (request.getSender().getEmployee().getCommunity().equals(enterprise.getName())) {
            Object[] row = new Object[6];
            row[0] = request;
            row[1] = request.getSender().getEmployee().getName();
            if (request.getReceiver() != null) {
                row[2] = request.getReceiver().getEmployee().getName();//userAccount.getEmployee().getName();//request.getReceiver() == null ? null : request.getReceiver().getEmployee().getName();
            }
            //request.setReceiver(userAccount); //check
            row[3] = request.getStatus();
            row[4] = request.getCommunitySentPower();
            row[5] = request.getCurrentFalingShortPower();

            //Check this Lgic*******
            //Only the request sent from the specific community will be diaplayed
            if (request.getSender() != null) {
                if (request.getSender().equals(userAccount)) {
                    model.addRow(row);
                }
            }

            if (request.getStatus().equalsIgnoreCase("Sent")) {
                networkSentRequestsAvailable = "Y";
            }
            //}

        }
        // }
        //}

    }

    public void populateTableReserve() {
        DefaultTableModel model = (DefaultTableModel) workRequestJTable1.getModel();

        model.setRowCount(0);
        // for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()) {
        // if ((ent != null) && (ent instanceof CommunityEnergyReserveEnterprise)) {

        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            Enterprise energyConserveEnterprise = null;
            if (enter instanceof CommunityEnergyReserveEnterprise) {
                energyConserveEnterprise = enter;
            }
        
            if (energyConserveEnterprise != null) {
                for (UserAccount ua : energyConserveEnterprise.getUserAccountDirectory().getUserAccountList()) {
                    for (PowerManagementWorkRequestEnterprise request : ua.getWorkQueueEnterprise().getWorkRequestList()) {
                        Object[] row = new Object[6];
                        row[0] = request;
                        row[1] = request.getSender().getEmployee().getName();
                        if (request.getReceiver() != null) {
                            row[2] = request.getReceiver().getEmployee().getName();//userAccount.getEmployee().getName();//request.getReceiver() == null ? null : request.getReceiver().getEmployee().getName();
                        }
                        //request.setReceiver(userAccount); //check
                        row[3] = request.getStatus();
                        row[4] = request.getCommunitySentPower();//((PowerManagementWorkRequestEnterprise) request).getCommunitySentPower();//((PowerManagementWorkRequestEnterprise) request).getCommunitySentPower();
                        row[5] = request.getCurrentFalingShortPower();//(PowerManagementWorkRequestEnterprise) request).getCurrentFalingShortPower();
//                        if (((PowerManagementWorkRequest) request).getProsumerSentpower() > 0) {
//                            totalExcessPower = totalExcessPower + ((PowerManagementWorkRequest) request).getProsumerSentpower();//request.getProsumerSentpower();
//                        } else {
//                            totalRequiredPower = totalRequiredPower + ((PowerManagementWorkRequest) request).getProsumerSentpower(); // check****
//                        }
                        model.addRow(row);
                        //}

                    }
                }
        // }
                // }
                // }
                //}
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

        btnSendPowerToEnergyReserve = new javax.swing.JButton();
        btnSendPowerToCity = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        backJButton = new javax.swing.JButton();
        btnCompensatePowerAtCommLevel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        workRequestJTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        workRequestJTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTotalPowerFallingShortProsumers = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotalAvailablePowerFrmProsumers = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        energyReserveJComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtPowerAtEnergyReserve = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        btnSendPowerToEnergyReserve.setBackground(new java.awt.Color(0, 153, 102));
        btnSendPowerToEnergyReserve.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSendPowerToEnergyReserve.setForeground(new java.awt.Color(255, 255, 255));
        btnSendPowerToEnergyReserve.setText("Send Power to Community Energy Reserve");
        btnSendPowerToEnergyReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPowerToEnergyReserveActionPerformed(evt);
            }
        });

        btnSendPowerToCity.setBackground(new java.awt.Color(0, 153, 102));
        btnSendPowerToCity.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSendPowerToCity.setForeground(new java.awt.Color(255, 255, 255));
        btnSendPowerToCity.setText("Send Power to City");
        btnSendPowerToCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPowerToCityActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Manage Community Multiple Work Request");

        backJButton.setBackground(new java.awt.Color(0, 153, 102));
        backJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        backJButton.setForeground(new java.awt.Color(255, 255, 255));
        backJButton.setText("<< Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        btnCompensatePowerAtCommLevel.setBackground(new java.awt.Color(0, 153, 102));
        btnCompensatePowerAtCommLevel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCompensatePowerAtCommLevel.setForeground(new java.awt.Color(255, 255, 255));
        btnCompensatePowerAtCommLevel.setText("Compensate Power at Community Level");
        btnCompensatePowerAtCommLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompensatePowerAtCommLevelActionPerformed(evt);
            }
        });

        workRequestJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Message", "Sender", "Receiver", "Status", "Power Available", "Shortfall Power"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(workRequestJTable);

        workRequestJTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Message", "Sender", "Receiver", "Status", "Power Available", "Shortfall Power"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(workRequestJTable1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Total Power Falling Short ");

        txtTotalPowerFallingShortProsumers.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Total Availble Power From Prosumers");

        txtTotalAvailablePowerFrmProsumers.setEnabled(false);
        txtTotalAvailablePowerFrmProsumers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalAvailablePowerFrmProsumersActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Select the Energy Reserve for Request");

        energyReserveJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyReserveJComboBoxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Power Available at this Reserve");

        txtPowerAtEnergyReserve.setEnabled(false);
        txtPowerAtEnergyReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPowerAtEnergyReserveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(backJButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(121, 121, 121)
                                        .addComponent(btnCompensatePowerAtCommLevel)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSendPowerToCity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSendPowerToEnergyReserve, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTotalPowerFallingShortProsumers, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(40, 40, 40)
                                    .addComponent(txtTotalAvailablePowerFrmProsumers, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(energyReserveJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPowerAtEnergyReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotalAvailablePowerFrmProsumers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotalPowerFallingShortProsumers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSendPowerToCity)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSendPowerToEnergyReserve)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(energyReserveJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPowerAtEnergyReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCompensatePowerAtCommLevel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backJButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendPowerToEnergyReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPowerToEnergyReserveActionPerformed
        // TODO add your handling code here:
        // for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
        // if (enter instanceof CommunityEnergyReserveEnterprise) {
        if (totalPowerFallingShort > 0) {
            JOptionPane.showMessageDialog(this, "Currently the community is falling short of some power for Consumers, please compensate the power at community level before the transfer ", "Transfer Power", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Enterprise energyConserveEnterprise = null;
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (enter instanceof CommunityEnergyReserveEnterprise) {
                energyConserveEnterprise = enter;
            }
        }

        if (energyConserveEnterprise == null) {
            JOptionPane.showMessageDialog(this, "Please create an Energy Reserve Enterprise to Transfer Power", "Transfer Power", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sentRequestsAvailable;
        sentRequestsAvailable = "N";
        for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {
            if (request.getStatus().equalsIgnoreCase("Sent")) {
                sentRequestsAvailable = "Y";
            }
        }
        if (sentRequestsAvailable == "Y") { // Test*** Very Imp**** what if requests are not available**** then power can be sent***** this scenario might not exist check once*****

            PowerManagementWorkRequestEnterprise energyReserveComRequestBulk = new PowerManagementWorkRequestEnterprise();
            energyReserveComRequestBulk.setCommunitySentPower(totalExcessPower); //check****
            //energyReserveComRequestBulk.setCurrentFalingShortPower(totalRequiredPower);//(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
            energyReserveComRequestBulk.setCurrentFalingShortPower(totalRequiredPower);
            energyReserveComRequestBulk.setSender(userAccount);
            //energyReserveComRequestBulk.setReceiver(userAccount);
            energyReserveComRequestBulk.setStatus("Sent");
            UserAccount assignedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();

            energyReserveComRequestBulk.setReceiver(assignedUA);

            //Setting Bulk Status 
            energyReserveComRequestBulk.setBulkRequest("Y");
            energyConserveEnterprise.setWorkQueue(workQueueProConsumer); //Check*****

            if (energyConserveEnterprise != null) {
                //assignedUA.getWorkQueue().getWorkRequestList().add(energyReserveComRequestBulk);
                //energyConserveEnterprise.getWorkQueue().getWorkRequestList().add(energyReserveComRequestBulk);
                if (assignedUA != null) {

                    assignedUA.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestBulk);
                    energyConserveEnterprise.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestBulk);
                    //user the user account of the sending person if required****

                    for (WorkRequest WR : enterprise.getWorkQueue().getWorkRequestList()) {
                        if (WR.getStatus().equalsIgnoreCase("Sent")) {
                            //if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                            if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(enterprise.getName())) {
                                WR.setStatus("Processing");

                            }
                            // }
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Power Transfer Request has been sent to Energy Reserve", "Bulk Requests", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Please create a User Account for the Energy Reserve Admin", "Bulk Requests", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            populateTableReserve();
        } else {
            JOptionPane.showMessageDialog(this, "There are No Requests Sent to Community to Process", "Bulk Requests", JOptionPane.ERROR_MESSAGE); //Test*****
            return;
        }

    }//GEN-LAST:event_btnSendPowerToEnergyReserveActionPerformed

    private void btnSendPowerToCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPowerToCityActionPerformed

        if (totalPowerFallingShort > 0) {
            JOptionPane.showMessageDialog(this, "Currently the community is falling short of some power for Consumers, please compensate the power at community level before the transfer ", "Transfer Power", JOptionPane.ERROR_MESSAGE);
            return;
        }

        networkSentRequestsAvailable = "N";
        for (WorkRequest WR : enterprise.getWorkQueue().getWorkRequestList()) {
            if (WR.getStatus().equalsIgnoreCase("Sent")) {
                networkSentRequestsAvailable = "Y";
            }
        }

        if (networkSentRequestsAvailable == "Y") {
            // TODO add your handling code here:
            //sentPower = request.getProsumerSentpower();
            //PowerManagementWorkRequest CommunityRequest = new PowerManagementWorkRequest();
            PowerManagementWorkRequestEnterprise CommunityRequest = new PowerManagementWorkRequestEnterprise();
            CommunityRequest.setCommunitySentPower(totalExcessPower);
            CommunityRequest.setCurrentFalingShortPower(totalRequiredPower);// check******(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
            CommunityRequest.setSender(userAccount);//(request.getReceiver());
            CommunityRequest.setStatus("Sent");

            //Setting the request type and request from prosumer or consumer
            CommunityRequest.setBulkRequest("Y");
            CommunityRequest.setCommunityEnterprise((CommunityEnterprise) enterprise);
            //energyReserveComRequestBulk.setPowerManagementWorkRequest(request);
            //CommunityRequest.setPowerManagementWorkRequest(req);

            if (network != null) {
                for (WorkRequest WR : enterprise.getWorkQueue().getWorkRequestList()) {
                    if (WR.getStatus().equalsIgnoreCase("Sent")) {
                        //if (WR.getReceiver().equals(energyConserveRequest.getSender())) {//Check****
                        if (WR.getSender().getEmployee().getCommunity().equalsIgnoreCase(enterprise.getName())) {
                            WR.setStatus("Processing");

                        }
                        // }
                    }
                }
                network.getWorkQueueEnterprise().getWorkRequestList().add(CommunityRequest);
                userAccount.getWorkQueueEnterprise().getWorkRequestList().add(CommunityRequest);
                JOptionPane.showMessageDialog(this, "Power Transfer Request has been sent to City Reserve", "Bulk Requests", JOptionPane.INFORMATION_MESSAGE);
            }

            populateTable();
        } else {
             JOptionPane.showMessageDialog(this, "There are No New Requests Sent to Community,to Send Power to City Network", "Bulk Requests", JOptionPane.ERROR_MESSAGE); //Test*****
            return;
        }

    }//GEN-LAST:event_btnSendPowerToCityActionPerformed

    private void btnCompensatePowerAtCommLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompensatePowerAtCommLevelActionPerformed
        // TODO add your handling code here:
        // if total power from prosumer > total power falling short at consumer
        
        if(totalPowerFallingShort == 0) {
             JOptionPane.showMessageDialog(this, "There is No shortfall of Power to compensate", "Send Power", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((totalCurrentExcessPower == 0) && (totalPowerFallingShort == 0)) {
            JOptionPane.showMessageDialog(this, "There is No Power in Excess or Falling short to compensate", "Send Power", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Need to recheck the entire logic******* of community reserve and community enterprise****
        communityEnergyResrvEnterprise = null;
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (enter instanceof CommunityEnergyReserveEnterprise) {
                communityEnergyResrvEnterprise = enter;
            }
        }

        if (communityEnergyResrvEnterprise == null) {
            JOptionPane.showMessageDialog(this, "Please create an Energy Reserve Enterprise to Compensate Power", "Transfer Power", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userAccountFound = "N";
        if (communityEnergyResrvEnterprise != null) {
            for (UserAccount account : communityEnergyResrvEnterprise.getUserAccountDirectory().getUserAccountList()) {
                userAccountFound = "Y";
            }
            if (userAccountFound == "N") {
                JOptionPane.showMessageDialog(this, "Please create a User Account for the Energy Reserve Admin", "Bulk Requests", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
         powerfallingShortAlreadyExists = "N";
         sentPowerAlreadyExists = "N";
         for (Enterprise enterp : network.getEnterpriseDirectory().getEnterpriseList()) {
            Enterprise energyConserveEnterprise = null;
            if (enterp instanceof CommunityEnergyReserveEnterprise) {
                energyConserveEnterprise = enterp;
            }     
            if (energyConserveEnterprise != null) {
                for (UserAccount ua : energyConserveEnterprise.getUserAccountDirectory().getUserAccountList()) {
                    for (PowerManagementWorkRequestEnterprise request : ua.getWorkQueueEnterprise().getWorkRequestList()) {
                        if(request.getSender().equals(userAccount)){
                            if(request.getStatus().equalsIgnoreCase("Sent")){
                                if(request.getCurrentFalingShortPower()== totalPowerFallingShort ){
                                    powerfallingShortAlreadyExists = "Y";
                                }
                                if(request.getCommunitySentPower()== totalCurrentExcessPower ){
                                    sentPowerAlreadyExists = "Y";
                                }
                                
                            }
                        }
                        
                    }
                }
        
            }
         }
            
            if(( powerfallingShortAlreadyExists == "Y") ||  (sentPowerAlreadyExists == "Y")){
                JOptionPane.showMessageDialog(this, "A request has been already raised for Community  Energy Reserve", "Bulk Requests", JOptionPane.ERROR_MESSAGE);
                return;
            }
        

        if ((totalCurrentExcessPower > totalPowerFallingShort) && (totalPowerFallingShort > 0)) {

            //compensate the power at the consumer level
            //update the work queue requests for prosumer and consumer, that the request has been processed
            //for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
            for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {//for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                if (request.getStatus().equalsIgnoreCase("Sent")) {
                    if (request.getSender().getEmployee().getCommunity().equals(enterprise.getName())) {
                        request.setMessage("Request has been Processed");
                        request.setStatus("Processed");
                        ((PowerManagementWorkRequest) request).setResult("Completed"); // Check**** is status should be placed as complete here itself*******

                        //For Consumer******
                        //Need to set shortfall to zero
                        int powerfallingShort = request.getSender().getEmployee().getProsumer().getCurrentPowerFallingShort();
                        if (powerfallingShort > 0) {
                            request.getSender().getEmployee().getProsumer().setCurrentPowerFallingShort(0);
                        }
                        //How about prosumers ???**** Check*****
                    }
                }
            }
                
            totalRemainingPower = totalCurrentExcessPower - totalPowerFallingShort;

            userAccount.getEmployee().getProsumer().setCurrentPowerFallingShort(0);
            userAccount.getEmployee().getProsumer().setCurrentExcessPower(totalRemainingPower);

            txtTotalAvailablePowerFrmProsumers.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentExcessPower()));
            txtTotalPowerFallingShortProsumers.setText(String.valueOf(userAccount.getEmployee().getProsumer().getCurrentPowerFallingShort()));

            int flag = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Power Compensation is Performed,Would you like to Send the Remaining  Power to City Admin or Energy Reserve",
                    "Warning", flag);
            if (dialogResult == JOptionPane.YES_OPTION) {
                int flagYes = JOptionPane.YES_NO_OPTION;
                int dialogResultYes = JOptionPane.showConfirmDialog(null, "Would you like to Send the Remaining  Power to City Admin ? Please click Yes, else the Power would be sent to Energy Reserve",
                        "Warning", flag);
                if (dialogResultYes == JOptionPane.YES_OPTION) {
                    //send power to city admin through a request 
                    //PowerManagementWorkRequest communityRequestComp = new PowerManagementWorkRequest();
                    PowerManagementWorkRequestEnterprise communityRequestComp = new PowerManagementWorkRequestEnterprise();

                    communityRequestComp.setCommunitySentPower(totalRemainingPower);
                    communityRequestComp.setCurrentFalingShortPower(0);// check******(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
                    communityRequestComp.setSender(userAccount);//(request.getReceiver());
                    communityRequestComp.setStatus("Sent");
                    communityRequestComp.setBulkRequest("Y");
                    communityRequestComp.setCommunityEnterprise((CommunityEnterprise) enterprise);

                    if (enterprise != null) {
                        userAccount.getWorkQueueEnterprise().getWorkRequestList().add(communityRequestComp);
                        network.getWorkQueueEnterprise().getWorkRequestList().add(communityRequestComp);

                    }
                    populateTable();
                } else { //Sent to energy Reserve 
                    //send power to Energy Reserve through a request 
                   

                    Enterprise comEnergyReserveMax = null;
                    int maxPowerAtEnergyConserve = 0;
                    int poweratEnergyReserve = 0;
                    uaMax = null;
                    for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
                        if (enter != null) {
                            if (enter instanceof CommunityEnergyReserveEnterprise) {
                                comEnergyReserveMax = enter;
                            }
                        }
                        if (comEnergyReserveMax != null) {
                            for (UserAccount ua : comEnergyReserveMax.getUserAccountDirectory().getUserAccountList()) {
                                //energyReserveJComboBox.addItem(ua);
                                //populateTextField(ua);//(enter);

                                if (ua != null) {
                                    poweratEnergyReserve = ua.getEmployee().getProsumer().getEnergyConserveAvailablePower();
                                }
                                if (poweratEnergyReserve > maxPowerAtEnergyConserve) {
                                    maxPowerAtEnergyConserve = poweratEnergyReserve;
                                    uaMax = ua;
                                }

                            }
                        }
                    }

                    PowerManagementWorkRequestEnterprise energyReserveComRequestComp = new PowerManagementWorkRequestEnterprise();
                    energyReserveComRequestComp.setCommunitySentPower(totalRemainingPower); //check****
                    energyReserveComRequestComp.setCurrentFalingShortPower(0);//(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
                    energyReserveComRequestComp.setSender(userAccount);
                    //energyReserveComRequest.setReceiver(userAccount);
                    energyReserveComRequestComp.setStatus("Sent");
                    energyReserveComRequestComp.setBulkRequest("Y");
                    energyReserveComRequestComp.setCommunityEnterprise((CommunityEnterprise) enterprise);
                    if (communityEnergyResrvEnterprise != null) {
                                //enter.getWorkQueue().getWorkRequestList().add(energyReserveComRequestComp);

                        if (uaMax != null) {
                            uaMax.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);
                        } else {
                            communityEnergyResrvEnterprise.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);
                        }
                        userAccount.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);

                    }
                    populateTableReserve();
                    //}
                    //}
                }
            }

            //Give an option to send the remaining power to city or community reserve
        }

        if (totalCurrentExcessPower < totalPowerFallingShort) {
            //testing Check******
            //if (totalCurrentExcessPower > totalPowerFallingShort) {
            //be cautious while compensating the power here to the 
            //***** better request some power from community reserve or city and compensate the power****
            // Send a new request, requesting for some power.
            // the concerned admin will review the request and release the power.

            Object[] possibleValues = {"City Admin", "Community Energy Reserve"};
            Object selectedValue = JOptionPane.showInputDialog(null,
                    "Total Power available from Prosumers is falling short,Would you like to request Power from", "Input",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);

            if (selectedValue == "City Admin") {
                //send request to city Admin

                //PowerManagementWorkRequest communityRequestComp = new PowerManagementWorkRequest();
                PowerManagementWorkRequestEnterprise communityRequestComp = new PowerManagementWorkRequestEnterprise();
                communityRequestComp.setCommunitySentPower(0);
                communityRequestComp.setCurrentFalingShortPower(totalPowerFallingShort - totalCurrentExcessPower);// check******(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
                communityRequestComp.setSender(userAccount);//(request.getReceiver());
                communityRequestComp.setStatus("Sent");
                communityRequestComp.setBulkRequest("Y");
                communityRequestComp.setCommunityEnterprise((CommunityEnterprise) enterprise);

                // communityRequestComp.setWorkQueue(workQueueProConsumer);
                if (enterprise != null) {
                    //Check*******
//                    enterprise.getWorkQueue().getWorkRequestList().add(communityRequestComp);//.add(request);
//                    userAccount.getWorkQueue().getWorkRequestList().add(communityRequestComp);//.add(request);

                    for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {//for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                        if (request.getStatus().equalsIgnoreCase("Sent")) {
                            if (request.getSender().getEmployee().getCommunity().equals(enterprise.getName())) {
                                //request.setMessage("Request has been Processed");
                                request.setStatus("Processing");
                            }
                        }
                    }

                    network.getWorkQueueEnterprise().getWorkRequestList().add(communityRequestComp);
                    JOptionPane.showMessageDialog(this, "Request has been sent to City Admin for Short Fall of Power", "Send Power", JOptionPane.INFORMATION_MESSAGE);
                }
                populateTable();

            }

            if (selectedValue == "Community Energy Reserve") {
                
                PowerManagementWorkRequestEnterprise energyReserveComRequestComp = new PowerManagementWorkRequestEnterprise();
                energyReserveComRequestComp.setCommunitySentPower(0); //check****
                energyReserveComRequestComp.setCurrentFalingShortPower(totalPowerFallingShort - totalCurrentExcessPower);//(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
                energyReserveComRequestComp.setSender(userAccount);
                //energyReserveComRequest.setReceiver(userAccount);
                energyReserveComRequestComp.setStatus("Sent");
                energyReserveComRequestComp.setBulkRequest("Y");

                energyReserveComRequestComp.setCommunityEnterprise((CommunityEnterprise) enterprise);

                if (communityEnergyResrvEnterprise != null) {
                    //enter.getWorkQueue().getWorkRequestList().add(energyReserveComRequestComp);
                    for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {//for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                        if (request.getStatus().equalsIgnoreCase("Sent")) {
                            if (request.getSender().getEmployee().getCommunity().equals(enterprise.getName())) {
                                //request.setMessage("Request has been Processed");
                                request.setStatus("Processing");
                            }
                        }
                    }

                    Enterprise comEnergyReserveMax = null;
                    int maxPowerAtEnergyConserve = 0;
                    int poweratEnergyReserve = 0;
                    uaMax = null;
                    for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
                        comEnergyReserveMax = null;
                        if (enter != null) {
                            if (enter instanceof CommunityEnergyReserveEnterprise) {
                                comEnergyReserveMax = enter;
                            }
                        }
                        if (comEnergyReserveMax != null) {
                            for (UserAccount ua : comEnergyReserveMax.getUserAccountDirectory().getUserAccountList()) {
                                // energyReserveJComboBox.addItem(ua);
                                //populateTextField(ua);//(enter);
                                if (ua != null) {
                                    poweratEnergyReserve = ua.getEmployee().getProsumer().getEnergyConserveAvailablePower();
                                }
                                if (poweratEnergyReserve > maxPowerAtEnergyConserve) {
                                    maxPowerAtEnergyConserve = poweratEnergyReserve;
                                    uaMax = ua;
                                }

                            }

//                UserAccount selectedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();
//                populateTextField(selectedUA);
                        }
                    }

                    userAccount.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);
                    if (uaMax != null) {
                        uaMax.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);
                    } else {
                        communityEnergyResrvEnterprise.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequestComp);
                    }

                    JOptionPane.showMessageDialog(this, "Request has been sent to Community Energy Reserve for Short Fall of Power", "Send Power", JOptionPane.INFORMATION_MESSAGE);
                }
                populateTableReserve();
              
            }
        }
        populateTableReserve();
    }//GEN-LAST:event_btnCompensatePowerAtCommLevelActionPerformed

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        ProsumerManageReceivingPowerJPanel prosumerManageReceive = (ProsumerManageReceivingPowerJPanel) component;
        prosumerManageReceive.populateTable();
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_backJButtonActionPerformed

    private void energyReserveJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_energyReserveJComboBoxActionPerformed
        // TODO add your handling code here:
        UserAccount selectedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();
        populateTextField(selectedUA);
    }//GEN-LAST:event_energyReserveJComboBoxActionPerformed

    private void txtPowerAtEnergyReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPowerAtEnergyReserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPowerAtEnergyReserveActionPerformed

    private void txtTotalAvailablePowerFrmProsumersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalAvailablePowerFrmProsumersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAvailablePowerFrmProsumersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backJButton;
    private javax.swing.JButton btnCompensatePowerAtCommLevel;
    private javax.swing.JButton btnSendPowerToCity;
    private javax.swing.JButton btnSendPowerToEnergyReserve;
    private javax.swing.JComboBox energyReserveJComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtPowerAtEnergyReserve;
    private javax.swing.JTextField txtTotalAvailablePowerFrmProsumers;
    private javax.swing.JTextField txtTotalPowerFallingShortProsumers;
    private javax.swing.JTable workRequestJTable;
    private javax.swing.JTable workRequestJTable1;
    // End of variables declaration//GEN-END:variables
}
