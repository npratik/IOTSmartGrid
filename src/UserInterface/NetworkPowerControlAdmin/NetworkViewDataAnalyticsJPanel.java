/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.NetworkPowerControlAdmin;

import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Enterprise.CommunityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueueEnterprise.PowerManagementWorkRequestEnterprise;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Pratik
 */
public class NetworkViewDataAnalyticsJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Network network;
    private UserAccount userAccount;
    private int totalPowerContributedEnergyReserve;
    private String bestCommunityEnergyReserve;

    private int totalPowerContributedCommunity;
    private String bestCommunityName;
    private int communityGrandContribution;
    private int groupTotalPowerContributed;
    
    private int totalPowerContributedProsumer;
    private String prosumerUserName;
    private String prosumerCommunityUserName;
    private String bestProsumerName;
    private String bestProsumerCommunityName;
    private int    bestProsumerContributedAmount;
    private int    totalProsumersContribution;
    
    private int currentCityAvailablePower;
    private int previousCityAvailablePower;
    private int communityGrandFallingShort;

    /**
     * Creates new form ViewDataAnalyticsJPanel
     */
    public NetworkViewDataAnalyticsJPanel(JPanel userProcessContainer, UserAccount userAccount, Network network) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.network = network;
        this.userAccount = userAccount;

        calculateDataAanalytics();
    }

    public void calculateDataAanalytics() {

        Enterprise commEnergyReserveEnterprise = null;
        Enterprise commEneterprise = null;

        bestCommunityEnergyReserve = "";
        totalPowerContributedEnergyReserve = 0;
        groupTotalPowerContributed = 0;
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            commEnergyReserveEnterprise = null;
            if (enter instanceof CommunityEnergyReserveEnterprise) {
                commEnergyReserveEnterprise = enter;
            }

        //Best Energy Reserve
            if (commEnergyReserveEnterprise != null) {

                for (UserAccount comEnergyUA : commEnergyReserveEnterprise.getUserAccountDirectory().getUserAccountList()) {
                    int currentPowerFallingShort = 0;
                    String energyReserveUserName = " ";
                    for (PowerManagementWorkRequestEnterprise enterpriseWR : comEnergyUA.getWorkQueueEnterprise().getWorkRequestList()) {
                        if (enterpriseWR.getStatus().equalsIgnoreCase("Processed")) {
                      //    if(enterpriseWR.getReceiver()!=null){
                            // if(enterpriseWR.getReceiver().equals(comEnergyUA)){
                            if (enterpriseWR.getCurrentFalingShortPower() > 0) {
                                currentPowerFallingShort = currentPowerFallingShort + enterpriseWR.getCurrentFalingShortPower();
                                energyReserveUserName = enterpriseWR.getReceiver().getUsername();
                                groupTotalPowerContributed = groupTotalPowerContributed + enterpriseWR.getCurrentFalingShortPower();
                            }
                             // }
                            // }

                        }
                    }

                    if (currentPowerFallingShort > totalPowerContributedEnergyReserve) {
                        totalPowerContributedEnergyReserve = currentPowerFallingShort;
                        bestCommunityEnergyReserve = energyReserveUserName;
                    }

                }
            }
        }
        txtEnergyConservePowerContributed.setText(String.valueOf(totalPowerContributedEnergyReserve));
        txtEnergyConserveUserName.setText(bestCommunityEnergyReserve);
        txtEnergyReserveGradTotalOthers.setText(String.valueOf(groupTotalPowerContributed));

        //Best Community 
        totalPowerContributedCommunity = 0;
        communityGrandFallingShort = 0;
        bestCommunityName = " ";
        String CommunityNetworkName = " ";
        for (PowerManagementWorkRequestEnterprise WR : network.getWorkQueueEnterprise().getWorkRequestList()) {

            communityGrandContribution = communityGrandContribution + WR.getCommunitySentPower();
            
            communityGrandFallingShort = communityGrandFallingShort + WR.getCurrentFalingShortPower();
               if(WR !=null){
                   if(WR.getSender().getEmployee().getCity().equalsIgnoreCase(network.getName())){
                       CommunityNetworkName = network.getName();
                   }
               }
            int currentPowerContributed = 0;
            String communityUserName = " ";
            if ((WR != null) && (WR.getStatus().equalsIgnoreCase("Processed"))) {
                for (PowerManagementWorkRequestEnterprise WR2 : network.getWorkQueueEnterprise().getWorkRequestList()) {
                    if (WR2.getStatus().equalsIgnoreCase("Processed")) {
                        if (WR.getSender().equals(WR2.getSender())) {
                            currentPowerContributed = currentPowerContributed + WR2.getCommunitySentPower();
                            communityUserName = WR2.getSender().getEmployee().getName();
                        }
                    }
                }
                if (totalPowerContributedCommunity < currentPowerContributed) {
                    totalPowerContributedCommunity = currentPowerContributed;
                    bestCommunityName = communityUserName;
                }
            }
        }

        txtBestCommunityName.setText(bestCommunityName);
        txtPowerContributedByCommunity.setText(String.valueOf(totalPowerContributedCommunity));
        txtCommunityGrandContributions.setText(String.valueOf(communityGrandContribution));

      //Best PowerManagement
        totalPowerContributedProsumer = 0;
        bestProsumerContributedAmount = 0;
        
        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
            Enterprise commEnterprise = null;
            if (enter instanceof CommunityEnterprise) {
                commEnterprise = enter;
            }
              int currentProsumerPowerContributed = 0;
              totalPowerContributedProsumer = 0;
            if (commEnterprise != null) {
                for (WorkRequest request : commEnterprise.getWorkQueue().getWorkRequestList()) {

                    //communityGrandContribution = communityGrandContribution + ((PowerManagementWorkRequest) request).getProsumerSentpower()
                      totalProsumersContribution = totalProsumersContribution + ((PowerManagementWorkRequest) request).getProsumerSentpower();
                    currentProsumerPowerContributed = 0;
                    String currentProsumerUserName = " ";
                    String currentProsumerCommunityUserName = " ";
                    if ((request != null) && (request.getStatus().equalsIgnoreCase("Processed"))) {
                        for (WorkRequest request2 : commEnterprise.getWorkQueue().getWorkRequestList()) {
                            if (request2.getStatus().equalsIgnoreCase("Processed")) {
                                if (request.getSender().equals(request2.getSender())) {
                                    currentProsumerPowerContributed = currentProsumerPowerContributed + ((PowerManagementWorkRequest) request2).getProsumerSentpower();
                                    currentProsumerUserName = request2.getSender().getEmployee().getName();
                                    currentProsumerCommunityUserName = request2.getReceiver().getEmployee().getName();
                                }
                            }
                        }
                        if (totalPowerContributedProsumer < currentProsumerPowerContributed) {
                            totalPowerContributedProsumer = currentProsumerPowerContributed;
                            prosumerUserName = currentProsumerUserName;
                            prosumerCommunityUserName = currentProsumerCommunityUserName;
                        }
                    }
                    
                }

            }
            
            if( bestProsumerContributedAmount < totalPowerContributedProsumer ) {
                bestProsumerName = prosumerUserName;
                bestProsumerContributedAmount = totalPowerContributedProsumer;
                bestProsumerCommunityName = prosumerCommunityUserName;
            }

        }
        
        txtProsumerName.setText(bestProsumerName);
        txtProsumerCommunityName.setText(bestProsumerCommunityName);
        txtPowerContributedByProsumer.setText(String.valueOf(bestProsumerContributedAmount));
        txttotalProsumersContribution.setText(String.valueOf(totalProsumersContribution));
        
        
        //Increase in Power Availability at City Network
        currentCityAvailablePower = 0;
        for(UserAccount ua : network.getUserAccountDirectory().getUserAccountList()){
          
          if(network.getName().equalsIgnoreCase(CommunityNetworkName)) {
             currentCityAvailablePower =  ua.getEmployee().getProsumer().getCityNetworkAvailablePower();
          }
           
        }
        
       if( currentCityAvailablePower > 0){ 
        previousCityAvailablePower = currentCityAvailablePower + communityGrandFallingShort - communityGrandContribution;
       }
       
       txtCityName.setText(network.getName());
        txtCityBeforeContributions.setText(String.valueOf(previousCityAvailablePower));
        txtCityAfterContributions.setText(String.valueOf(currentCityAvailablePower));

        
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEnergyConserveUserName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEnergyConservePowerContributed = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEnergyReserveGradTotalOthers = new javax.swing.JTextField();
        backJButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtPowerContributedByCommunity = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCommunityGrandContributions = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBestCommunityName = new javax.swing.JTextField();
        btnShowChart = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtPowerContributedByProsumer = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txttotalProsumersContribution = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtProsumerName = new javax.swing.JTextField();
        txtProsumerCommunityName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtCityBeforeContributions = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCityAfterContributions = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCityName = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Data Analytics");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Best Energy Reserve");

        jLabel3.setText("Admin User Name ");

        txtEnergyConserveUserName.setEnabled(false);
        txtEnergyConserveUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnergyConserveUserNameActionPerformed(evt);
            }
        });

        jLabel4.setText("Power Contributed");

        txtEnergyConservePowerContributed.setEnabled(false);
        txtEnergyConservePowerContributed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnergyConservePowerContributedActionPerformed(evt);
            }
        });

        jLabel5.setText("Total Contributions by other Energy Reserve");

        txtEnergyReserveGradTotalOthers.setEnabled(false);
        txtEnergyReserveGradTotalOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnergyReserveGradTotalOthersActionPerformed(evt);
            }
        });

        backJButton.setBackground(new java.awt.Color(0, 153, 102));
        backJButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        backJButton.setForeground(new java.awt.Color(255, 255, 255));
        backJButton.setText("<< Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Power Contributed");

        txtPowerContributedByCommunity.setEnabled(false);
        txtPowerContributedByCommunity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPowerContributedByCommunityActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Contributions by Communities");

        txtCommunityGrandContributions.setEnabled(false);
        txtCommunityGrandContributions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCommunityGrandContributionsActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Best Community");

        jLabel9.setText("Community Name ");

        txtBestCommunityName.setEnabled(false);
        txtBestCommunityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBestCommunityNameActionPerformed(evt);
            }
        });

        btnShowChart.setBackground(new java.awt.Color(0, 153, 102));
        btnShowChart.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnShowChart.setForeground(new java.awt.Color(255, 255, 255));
        btnShowChart.setText("View Chart");
        btnShowChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowChartActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel10.setText("Power Contributed");

        txtPowerContributedByProsumer.setEnabled(false);
        txtPowerContributedByProsumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPowerContributedByProsumerActionPerformed(evt);
            }
        });

        jLabel11.setText("Total Contributions by Prsomers");

        txttotalProsumersContribution.setEnabled(false);
        txttotalProsumersContribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalProsumersContributionActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Best Prosumer");

        jLabel13.setText("Prosumer Name ");

        txtProsumerName.setEnabled(false);
        txtProsumerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProsumerNameActionPerformed(evt);
            }
        });

        txtProsumerCommunityName.setEnabled(false);
        txtProsumerCommunityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProsumerCommunityNameActionPerformed(evt);
            }
        });

        jLabel14.setText("Community Name ");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );

        jLabel15.setText("Before Contributions");

        txtCityBeforeContributions.setEnabled(false);
        txtCityBeforeContributions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityBeforeContributionsActionPerformed(evt);
            }
        });

        jLabel16.setText("After Contributions");

        txtCityAfterContributions.setEnabled(false);
        txtCityAfterContributions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityAfterContributionsActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel17.setText("City NetWork Details");

        jLabel18.setText("City Name ");

        txtCityName.setEnabled(false);
        txtCityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityNameActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(backJButton)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(29, 29, 29)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEnergyConserveUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtEnergyConservePowerContributed, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                                .addComponent(txtEnergyReserveGradTotalOthers)))))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtBestCommunityName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtPowerContributedByCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCommunityGrandContributions, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnShowChart)
                        .addGap(167, 167, 167)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(92, 92, 92)
                                .addComponent(txtProsumerName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtProsumerCommunityName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPowerContributedByProsumer, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txttotalProsumersContribution, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCityName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCityBeforeContributions, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCityAfterContributions, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(137, 137, 137))))
            .addGroup(layout.createSequentialGroup()
                .addGap(514, 514, 514)
                .addComponent(jLabel1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEnergyConserveUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtEnergyConservePowerContributed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtEnergyReserveGradTotalOthers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtBestCommunityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtPowerContributedByCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtCommunityGrandContributions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19)
                        .addComponent(btnShowChart))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtProsumerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtProsumerCommunityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtPowerContributedByProsumer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtCityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtCityBeforeContributions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtCityAfterContributions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txttotalProsumersContribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addComponent(backJButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtEnergyConserveUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnergyConserveUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnergyConserveUserNameActionPerformed

    private void txtEnergyConservePowerContributedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnergyConservePowerContributedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnergyConservePowerContributedActionPerformed

    private void txtEnergyReserveGradTotalOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnergyReserveGradTotalOthersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnergyReserveGradTotalOthersActionPerformed

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_backJButtonActionPerformed

    private void txtPowerContributedByCommunityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPowerContributedByCommunityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPowerContributedByCommunityActionPerformed

    private void txtCommunityGrandContributionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCommunityGrandContributionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommunityGrandContributionsActionPerformed

    private void txtBestCommunityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBestCommunityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBestCommunityNameActionPerformed

    private void btnShowChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowChartActionPerformed
        // TODO add your handling code here:

        if (bestCommunityName != null) {
            createChartCommunity();
        }

        if (bestCommunityEnergyReserve != null) {
            createChartCommunityReserve();
        }
        
         if (bestProsumerName != null) {
            createChartBestProsumer();
        }
         
         if (network.getName() != null) {
            createChartCityNetwork();
        }

    }//GEN-LAST:event_btnShowChartActionPerformed

    private void txtPowerContributedByProsumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPowerContributedByProsumerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPowerContributedByProsumerActionPerformed

    private void txttotalProsumersContributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalProsumersContributionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalProsumersContributionActionPerformed

    private void txtProsumerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsumerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProsumerNameActionPerformed

    private void txtProsumerCommunityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsumerCommunityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProsumerCommunityNameActionPerformed

    private void txtCityBeforeContributionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityBeforeContributionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityBeforeContributionsActionPerformed

    private void txtCityAfterContributionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityAfterContributionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityAfterContributionsActionPerformed

    private void txtCityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityNameActionPerformed

    private void createChartCommunityReserve() {
        DefaultCategoryDataset CommunityReservePwrContriDataset = new DefaultCategoryDataset();

       // NetworkViewChartsJPanel p = new NetworkViewChartsJPanel(chart);
        Date dt = new Date();
        
        CommunityReservePwrContriDataset.addValue(totalPowerContributedEnergyReserve, "Best Community Power Reserve", dt);

        CommunityReservePwrContriDataset.addValue(groupTotalPowerContributed, "All Community Power Reserves", dt);


        JFreeChart CommunityReservePwrContriChart = ChartFactory.createBarChart3D("Best Community Power Reserve Contribution Chart", "Time Stamp", "Power in KW", CommunityReservePwrContriDataset, PlotOrientation.VERTICAL, true, false, false);
        CommunityReservePwrContriChart.setBackgroundPaint(Color.white);
        CategoryPlot CommunityReservePwrContriChartPlot = CommunityReservePwrContriChart.getCategoryPlot();
        CommunityReservePwrContriChartPlot.setBackgroundPaint(Color.lightGray);

        CategoryAxis CommunityPowerContributionDomainAxis = CommunityReservePwrContriChartPlot.getDomainAxis();
        CommunityPowerContributionDomainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        NumberAxis CommunityPowerContributionRangeAxis = (NumberAxis) CommunityReservePwrContriChartPlot.getRangeAxis();
        CommunityPowerContributionRangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //ChartFrame chartFrame = new ChartFrame("Chart", CommunityReservePwrContriChart);
        //chartFrame.setVisible(true);
        //chartFrame.setSize(500, 500);
        ChartPanel p = new ChartPanel(CommunityReservePwrContriChart);
        p.setSize(jPanel2.getWidth(), jPanel2.getHeight());
        p.setVisible(true);
        jPanel2.add(p);
        jPanel2.repaint();

    }

    private void createChartCommunity() {
        DefaultCategoryDataset CommunityPowerContributionDataset = new DefaultCategoryDataset();

       
        Date dt = new Date();
       
        CommunityPowerContributionDataset.addValue(totalPowerContributedCommunity, "Best Community", dt);

        CommunityPowerContributionDataset.addValue(communityGrandContribution, "All Communities", dt);


        JFreeChart CommunityPowerContributionChart = ChartFactory.createBarChart3D("Best Community Contribution Chart", "Time Stamp", "Power in KW", CommunityPowerContributionDataset, PlotOrientation.VERTICAL, true, false, false);
        CommunityPowerContributionChart.setBackgroundPaint(Color.white);
        CategoryPlot CommunityPowerContributionChartPlot = CommunityPowerContributionChart.getCategoryPlot();
        CommunityPowerContributionChartPlot.setBackgroundPaint(Color.lightGray);

        CategoryAxis CommunityPowerContributionDomainAxis = CommunityPowerContributionChartPlot.getDomainAxis();
        CommunityPowerContributionDomainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        NumberAxis CommunityPowerContributionRangeAxis = (NumberAxis) CommunityPowerContributionChartPlot.getRangeAxis();
        CommunityPowerContributionRangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        
        ChartPanel p = new ChartPanel(CommunityPowerContributionChart);
        p.setSize(jPanel1.getWidth(), jPanel1.getHeight());
        p.setVisible(true);
        jPanel1.add(p);
        jPanel1.repaint();

    }
    private void createChartBestProsumer() {
        DefaultCategoryDataset ProsumerPowerContributionDataset = new DefaultCategoryDataset();

       
        Date dt = new Date();
       
        ProsumerPowerContributionDataset.addValue(bestProsumerContributedAmount, "Best Prosumer", dt);

        ProsumerPowerContributionDataset.addValue(totalProsumersContribution, "All Prosumers", dt);


        JFreeChart ProsumerPowerContributionChart = ChartFactory.createBarChart3D("Best Prosumer Contribution Chart", "Time Stamp", "Power in KW", ProsumerPowerContributionDataset, PlotOrientation.VERTICAL, true, false, false);
        ProsumerPowerContributionChart.setBackgroundPaint(Color.white);
        CategoryPlot ProsumerPowerContributionChartPlot = ProsumerPowerContributionChart.getCategoryPlot();
        ProsumerPowerContributionChartPlot.setBackgroundPaint(Color.lightGray);

        CategoryAxis CommunityPowerContributionDomainAxis = ProsumerPowerContributionChartPlot.getDomainAxis();
        CommunityPowerContributionDomainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        NumberAxis CommunityPowerContributionRangeAxis = (NumberAxis) ProsumerPowerContributionChartPlot.getRangeAxis();
        CommunityPowerContributionRangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //ChartFrame chartFrame = new ChartFrame("Chart", ProsumerPowerContributionChart);
        //chartFrame.setVisible(true);
        //chartFrame.setSize(500, 500);
        ChartPanel p = new ChartPanel(ProsumerPowerContributionChart);
        p.setSize(jPanel3.getWidth(), jPanel3.getHeight());
        p.setVisible(true);
        jPanel3.add(p);
        jPanel3.repaint();

    }
    
    //createChartCityNetwork()
    
     private void createChartCityNetwork() {
        DefaultCategoryDataset CityPowerContributionDataset = new DefaultCategoryDataset();

       
        Date dt = new Date();
        
        CityPowerContributionDataset.addValue(previousCityAvailablePower, "Before Contributions", dt);

        CityPowerContributionDataset.addValue(currentCityAvailablePower, "AfterContributions", dt);


        JFreeChart CityPowerContributionChart = ChartFactory.createBarChart3D("City Network Contribution Chart", "Time Stamp", "Power in KW", CityPowerContributionDataset, PlotOrientation.VERTICAL, true, false, false);
        CityPowerContributionChart.setBackgroundPaint(Color.white);
        CategoryPlot CityPowerContributionChartPlot = CityPowerContributionChart.getCategoryPlot();
        CityPowerContributionChartPlot.setBackgroundPaint(Color.lightGray);

        CategoryAxis CommunityPowerContributionDomainAxis = CityPowerContributionChartPlot.getDomainAxis();
        CommunityPowerContributionDomainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        NumberAxis CommunityPowerContributionRangeAxis = (NumberAxis) CityPowerContributionChartPlot.getRangeAxis();
        CommunityPowerContributionRangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //ChartFrame chartFrame = new ChartFrame("Chart", CityPowerContributionChart);
        //chartFrame.setVisible(true);
        //chartFrame.setSize(500, 500);
        ChartPanel p = new ChartPanel(CityPowerContributionChart);
        p.setSize(jPanel4.getWidth(), jPanel4.getHeight());
        p.setVisible(true);
        jPanel4.add(p);
        jPanel4.repaint();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backJButton;
    private javax.swing.JButton btnShowChart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtBestCommunityName;
    private javax.swing.JTextField txtCityAfterContributions;
    private javax.swing.JTextField txtCityBeforeContributions;
    private javax.swing.JTextField txtCityName;
    private javax.swing.JTextField txtCommunityGrandContributions;
    private javax.swing.JTextField txtEnergyConservePowerContributed;
    private javax.swing.JTextField txtEnergyConserveUserName;
    private javax.swing.JTextField txtEnergyReserveGradTotalOthers;
    private javax.swing.JTextField txtPowerContributedByCommunity;
    private javax.swing.JTextField txtPowerContributedByProsumer;
    private javax.swing.JTextField txtProsumerCommunityName;
    private javax.swing.JTextField txtProsumerName;
    private javax.swing.JTextField txttotalProsumersContribution;
    // End of variables declaration//GEN-END:variables
}
