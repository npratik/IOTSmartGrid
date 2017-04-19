/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.CommunityAdminRole;

import Business.EcoSystem;
import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Enterprise.CommunityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.ConsumerOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueueEnterprise.PowerManagementWorkRequestEnterprise;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pratik
 */
public class ProcessEachWorkRequestJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private PowerManagementWorkRequest request;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private int sentPower;
    private EcoSystem business;
    private Network network;

    /**
     * Creates new form ProcessEachWorkRequestJPanel
     */
    public ProcessEachWorkRequestJPanel(JPanel userProcessContainer, UserAccount userAccount, PowerManagementWorkRequest request, Enterprise enterprise, Network network, EcoSystem business) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.request = request;
        this.userAccount = userAccount;
        this.enterprise = enterprise;
        this.business = business;
        this.network = network;
        populateTable();
        populateTableReserve();

        energyReserveJComboBox.removeAllItems();
        Enterprise comEnergyReserve = null;
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (enter != null) {
                if (enter instanceof CommunityEnergyReserveEnterprise) {
                    comEnergyReserve = enter;
                }
            }    
                if (comEnergyReserve != null) {
                    for (UserAccount ua : enter.getUserAccountDirectory().getUserAccountList()) {
                        energyReserveJComboBox.addItem(ua);
                        //populateTextField(ua);//(enter);
                    }
                    UserAccount selectedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();
                    populateTextField(selectedUA);
                }
            }
        //}

//        for(Organization org : e.getOrganizationDirectory().getOrganizationList()){
//            if(org.getName().equals("Doctor Organization")){
//                for(UserAccount ua : org.getUserAccountDirectory().getUserAccountList()){
//                    comboDoctor.addItem(ua);
//                }
//            }
//        }
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

       // if ((enterprise != null) && (enterprise instanceof CommunityEnterprise)) {
          //  for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {
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
                
                //check*** testing Start******
                // row[4] = ((PowerManagementWorkRequestEnterprise) request).getCommunitySentPower();
                //check*** testing End******
//                        if (((PowerManagementWorkRequest) request).getProsumerSentpower() > 0) {
//                            totalExcessPower = totalExcessPower + ((PowerManagementWorkRequest) request).getProsumerSentpower();//request.getProsumerSentpower();
//                        } else {
//                            totalRequiredPower = totalRequiredPower + ((PowerManagementWorkRequest) request).getProsumerSentpower(); // check****
//                        }
                model.addRow(row);
                //}

            }
        //}
        // }
        //}

    }

    public void populateTableReserve() {
        DefaultTableModel model = (DefaultTableModel) workRequestJTable1.getModel();

        model.setRowCount(0);
        Enterprise energyEnt = null;
        for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()) {
            if (ent instanceof CommunityEnergyReserveEnterprise) {
                energyEnt = ent;
            }
        }
        if (energyEnt != null) {
            //for (WorkRequest request : energyEnt.getWorkQueue().getWorkRequestList()) {
            for (PowerManagementWorkRequestEnterprise request : energyEnt.getWorkQueueEnterprise().getWorkRequestList()) {
                Object[] row = new Object[5];
                row[0] = request;
                row[1] = request.getSender().getEmployee().getName();
                if (request.getReceiver() != null) {
                    row[2] = request.getReceiver().getEmployee().getName();//userAccount.getEmployee().getName();//request.getReceiver() == null ? null : request.getReceiver().getEmployee().getName();
                }
                //request.setReceiver(userAccount); //check
                row[3] = request.getStatus();
                //check*** testing Start******
                row[4] = request.getCommunitySentPower();//((PowerManagementWorkRequestEnterprise) request).getCommunitySentPower();
                //check*** testing End******
//                        if (((PowerManagementWorkRequest) request).getProsumerSentpower() > 0) {
//                            totalExcessPower = totalExcessPower + ((PowerManagementWorkRequest) request).getProsumerSentpower();//request.getProsumerSentpower();
//                        } else {
//                            totalRequiredPower = totalRequiredPower + ((PowerManagementWorkRequest) request).getProsumerSentpower(); // check****
//                        }
                model.addRow(row);
                //}

            }
        }
        //}
        // }
        //}

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        backJButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnSendPowerToEnergyReserve = new javax.swing.JButton();
        btnSendPowerToCity = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        workRequestJTable = new javax.swing.JTable();
        refreshJButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        workRequestJTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        energyReserveJComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtPowerAtEnergyReserve = new javax.swing.JTextField();

        jLabel4.setText("jLabel4");

        setBackground(new java.awt.Color(255, 255, 255));

        backJButton.setBackground(new java.awt.Color(0, 153, 102));
        backJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        backJButton.setForeground(new java.awt.Color(255, 255, 255));
        backJButton.setText("<< Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Manage Single Work Request");

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
                false, true, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(workRequestJTable);

        refreshJButton.setBackground(new java.awt.Color(0, 153, 102));
        refreshJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        refreshJButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshJButton.setText("Refresh");
        refreshJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshJButtonActionPerformed(evt);
            }
        });

        workRequestJTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Message", "Sender", "Receiver", "Status", "Power Available"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(workRequestJTable1);

        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Select the Energy Reserve for Request");

        energyReserveJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyReserveJComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("Power Available at this Reserve");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(btnSendPowerToCity, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(refreshJButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(61, 61, 61))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSendPowerToEnergyReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(energyReserveJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPowerAtEnergyReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(backJButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSendPowerToCity)
                    .addComponent(refreshJButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnSendPowerToEnergyReserve)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(energyReserveJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPowerAtEnergyReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backJButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed

        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        ProsumerManageReceivingPowerJPanel dwjp = (ProsumerManageReceivingPowerJPanel) component;
        dwjp.populateTable();
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_backJButtonActionPerformed

    private void btnSendPowerToEnergyReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPowerToEnergyReserveActionPerformed
        // TODO add your handling code here:

        if (request.getStatus().equals("Sent")) {
           // for (Network net : business.getNetworkDirectory().getNetworkList()) {
               // for (Enterprise enter : net.getEnterpriseDirectory().getEnterpriseList()) {
                  //  if (enter instanceof CommunityEnergyReserveEnterprise) {
            
             Enterprise energyConserveEnterprise = null;
                for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
                    if (enter instanceof CommunityEnergyReserveEnterprise) { 
                         energyConserveEnterprise = enter;
                    }
                }
                
                if (energyConserveEnterprise==null){
                    JOptionPane.showMessageDialog(this, "Please create an Energy Reserve Enterprise to Transfer Power", "Transfer Power", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                        //enter.getUserAccountDirectory().getUserAccountList()
                        sentPower = request.getProsumerSentpower();
                        //PowerManagementWorkRequest energyReserveComRequest = new PowerManagementWorkRequest();
                        PowerManagementWorkRequestEnterprise energyReserveComRequest = new PowerManagementWorkRequestEnterprise();
                        energyReserveComRequest.setCommunitySentPower(sentPower);
                        energyReserveComRequest.setCurrentFalingShortPower(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
                        energyReserveComRequest.setSender(request.getReceiver());

                        //Setting the request type and request from prosumer or consumer
                        energyReserveComRequest.setBulkRequest("N");
                        energyReserveComRequest.setPowerManagementWorkRequest(request);

                        UserAccount assignedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();

                        energyReserveComRequest.setReceiver(assignedUA);
                        energyReserveComRequest.setStatus("Sent");
                        energyReserveComRequest.setCommunityEnterprise((CommunityEnterprise)enterprise);

                        if (energyConserveEnterprise != null) {
//                            energyConserveEnterprise.getWorkQueue().getWorkRequestList().add(energyReserveComRequest);
//                            assignedUA.getWorkQueue().getWorkRequestList().add(energyReserveComRequest);
                           
                            if(assignedUA !=null){
                            //Very Imp**** Remove the below mentioned line, that is it cant be assigned to an energy conserve enterprise
                            energyConserveEnterprise.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequest);
                            assignedUA.getWorkQueueEnterprise().getWorkRequestList().add(energyReserveComRequest);
                            //user the user account of the sending person if required****
                            JOptionPane.showMessageDialog(this, "Power Transfer Request has been sent to Energy Reserve", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this, "Please create a User Account for the Energy Reserve Admin", "Single Requests", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            
                        }
                        populateTableReserve();
                        //sending acknowledgement to the prosumer
                        request.setStatus("Processing");
                   // }
                //}
            //}
        } else {
            JOptionPane.showMessageDialog(this, "The selected Request is either Processed or under Processing, Please select a new Request", "Single Requests", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnSendPowerToEnergyReserveActionPerformed

    private void btnSendPowerToCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPowerToCityActionPerformed
        // TODO add your handling code here:

        if (request.getStatus().equals("Sent")) {
            sentPower = request.getProsumerSentpower();
            //PowerManagementWorkRequest CommunityRequest = new PowerManagementWorkRequest();PowerManagementWorkRequestEnterprise
            PowerManagementWorkRequestEnterprise CommunityRequest = new PowerManagementWorkRequestEnterprise();
            CommunityRequest.setCommunitySentPower(sentPower);
            CommunityRequest.setCurrentFalingShortPower(request.getCurrentFalingShortPower()); //power that is falling short - prosumer does not have any defficiency in power*
            CommunityRequest.setSender(request.getReceiver());
            CommunityRequest.setStatus("Sent");

            //Setting the request type and request from prosumer or consumer
            CommunityRequest.setBulkRequest("N");
            CommunityRequest.setPowerManagementWorkRequest(request);
            CommunityRequest.setCommunityEnterprise((CommunityEnterprise)enterprise);
//            for(UserAccount netUA : network.getUserAccountDirectory().getUserAccountList()){
//                if(netUA!=null){
//                    //planning to assign the city user acoount to receiver
//                }
//            }
            
            //this logic is not required*****
//            for (UserAccount UserAcc : network.getUserAccountDirectory().getUserAccountList()) {
//                //request.getSender().getEmployee().getCity().equals(network.getName())
//                if (CommunityRequest.getSender().getEmployee().getCity().equals(network.getName())) {
//                    CommunityRequest.setReceiver(UserAcc);
//                }
//            }

            if (enterprise != null) {
                //enterprise.getWorkQueue().getWorkRequestList().add(CommunityRequest);//.add(request);
                //enterprise.getWorkQueueEnterprise().getWorkRequestList().add(CommunityRequest);
                
           ///Very Imp***** dont use the community eneterprise for assiging, as its used by prosumer and consumer*****
                //*** instead use , city network******
//also can be set to network Work queue
                //userAccount.getWorkQueue().getWorkRequestList().add(CommunityRequest);//.add(request);
                network.getWorkQueueEnterprise().getWorkRequestList().add(CommunityRequest);
                userAccount.getWorkQueueEnterprise().getWorkRequestList().add(CommunityRequest);
            }

            //sending acknowledgement to the prosumer
            request.setStatus("Processing");//("Accepted");//("Completed");
            populateTable();
            JOptionPane.showMessageDialog(this, "Power Transfer Request has been sent to City Network", "Single Requests", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "The selected Request is either Processed or under Processing, Please select a new Request", "Single Requests", JOptionPane.ERROR_MESSAGE);
            return;
        }


    }//GEN-LAST:event_btnSendPowerToCityActionPerformed

    private void refreshJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshJButtonActionPerformed
        populateTable();
        populateTableReserve();
    }//GEN-LAST:event_refreshJButtonActionPerformed

    private void energyReserveJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_energyReserveJComboBoxActionPerformed
        // TODO add your handling code here:
        UserAccount selectedUA = (UserAccount) energyReserveJComboBox.getSelectedItem();
        populateTextField(selectedUA);
    }//GEN-LAST:event_energyReserveJComboBoxActionPerformed

    private void txtPowerAtEnergyReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPowerAtEnergyReserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPowerAtEnergyReserveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backJButton;
    private javax.swing.JButton btnSendPowerToCity;
    private javax.swing.JButton btnSendPowerToEnergyReserve;
    private javax.swing.JComboBox energyReserveJComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton refreshJButton;
    private javax.swing.JTextField txtPowerAtEnergyReserve;
    private javax.swing.JTable workRequestJTable;
    private javax.swing.JTable workRequestJTable1;
    // End of variables declaration//GEN-END:variables
}
