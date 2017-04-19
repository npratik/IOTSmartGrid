/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.CommunityAdminRole;

import Business.Enterprise.CommunityEnergyReserveEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
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
public class CommunityViewDataAnalyticsJPanel extends javax.swing.JPanel {
//userProcessContainer, userAccount, enterprise, network
    
    private JPanel userProcessContainer;
    private Enterprise enterprise;
    private Organization organization;
    private UserAccount userAccount;
    private Network network;
   
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
    /**
     * Creates new form CommunityViewDataAnalyticsJPanel
     */
    public CommunityViewDataAnalyticsJPanel(JPanel userProcessContainer,UserAccount userAccount,Enterprise enterprise,Network network ) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = userAccount;
        this.enterprise = enterprise;
        this.organization = organization;
        this.network = network;
        
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
        
        
        //Best Prosumer
        totalPowerContributedProsumer = 0;
        bestProsumerContributedAmount = 0;
        
//        for (Enterprise enter : network.getEnterpriseDirectory().getEnterpriseList()) {
//            Enterprise commEnterprise = null;
//            if (enter instanceof CommunityEnterprise) {
//                commEnterprise = enter;
//            }
             int currentProsumerPowerContributed = 0;
//              totalPowerContributedProsumer = 0;
//            if (commEnterprise != null) {
                for (WorkRequest request : enterprise.getWorkQueue().getWorkRequestList()) {

                    //communityGrandContribution = communityGrandContribution + ((PowerManagementWorkRequest) request).getProsumerSentpower()
                      totalProsumersContribution = totalProsumersContribution + ((PowerManagementWorkRequest) request).getProsumerSentpower();
                    currentProsumerPowerContributed = 0;
                    String currentProsumerUserName = " ";
                    String currentProsumerCommunityUserName = " ";
                    if ((request != null) && (request.getStatus().equalsIgnoreCase("Processed"))) {
                        for (WorkRequest request2 : enterprise.getWorkQueue().getWorkRequestList()) {
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
                            bestProsumerName = currentProsumerUserName;
                            prosumerCommunityUserName = currentProsumerCommunityUserName;
                        }
                    }
                    
                }

//            }
//            
////            if( bestProsumerContributedAmount < totalPowerContributedProsumer ) {
////                bestProsumerName = prosumerUserName;
////                bestProsumerContributedAmount = totalPowerContributedProsumer;
////                bestProsumerCommunityName = prosumerCommunityUserName;
////            }
//
//        }
        
        txtProsumerName.setText(bestProsumerName);
       
        txtPowerContributedByProsumer.setText(String.valueOf(totalPowerContributedProsumer));
        txttotalProsumersContribution.setText(String.valueOf(totalProsumersContribution));

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEnergyConserveUserName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEnergyConservePowerContributed = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEnergyReserveGradTotalOthers = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPowerContributedByProsumer = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txttotalProsumersContribution = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtProsumerName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnShowChart = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );

        btnShowChart.setBackground(new java.awt.Color(0, 153, 102));
        btnShowChart.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnShowChart.setForeground(new java.awt.Color(255, 255, 255));
        btnShowChart.setText("View Chart");
        btnShowChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowChartActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(0, 153, 102));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(btnShowChart)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(174, 174, 174)
                                .addComponent(txtEnergyConserveUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(175, 175, 175)
                                .addComponent(txtEnergyConservePowerContributed, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(32, 32, 32)
                                .addComponent(txtEnergyReserveGradTotalOthers, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(92, 92, 92)
                                .addComponent(txtProsumerName, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addGap(82, 82, 82)
                                            .addComponent(txtPowerContributedByProsumer, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txttotalProsumersContribution, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(60, 60, 60))
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtProsumerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtPowerContributedByProsumer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txttotalProsumersContribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShowChart)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack)
                .addGap(8, 8, 8))
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

    private void txtPowerContributedByProsumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPowerContributedByProsumerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPowerContributedByProsumerActionPerformed

    private void txttotalProsumersContributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalProsumersContributionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalProsumersContributionActionPerformed

    private void txtProsumerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsumerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProsumerNameActionPerformed

    private void btnShowChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowChartActionPerformed
        // TODO add your handling code here:

        
        if (bestCommunityEnergyReserve != null) {
            createChartCommunityReserve();
        }

        if (bestProsumerName != null) {
            createChartBestProsumer();
        }

       
    }//GEN-LAST:event_btnShowChartActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed
private void createChartCommunityReserve() {
        DefaultCategoryDataset CommunityReservePwrContriDataset = new DefaultCategoryDataset();

       // NetworkViewChartsJPanel p = new NetworkViewChartsJPanel(chart);
        Date dt = new Date();
        // for (VitalSign CommunityPowerContribution : CommunityPowerContributionList) {communityGrandContribution
        CommunityReservePwrContriDataset.addValue(totalPowerContributedEnergyReserve, "Best Community Power Reserve", dt);

        CommunityReservePwrContriDataset.addValue(groupTotalPowerContributed, "All Community Power Reserves", dt);
//            CommunityReservePwrContriDataset.addValue(CommunityPowerContribution.getHeartRate(),"HR", CommunityPowerContribution.getTimestamp());
//            CommunityReservePwrContriDataset.addValue(CommunityPowerContribution.getBloodPressure(),"BP", CommunityPowerContribution.getTimestamp());
//            CommunityReservePwrContriDataset.addValue(vitalSign.getWeight(),"WT", vitalSign.getTimestamp());
        // }

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

private void createChartBestProsumer() {
        DefaultCategoryDataset ProsumerPowerContributionDataset = new DefaultCategoryDataset();

       // NetworkViewChartsJPanel p = new NetworkViewChartsJPanel(chart);
        Date dt = new Date();
        // for (VitalSign CommunityPowerContribution : CommunityPowerContributionList) {communityGrandContribution
        ProsumerPowerContributionDataset.addValue(totalPowerContributedProsumer, "Best Prosumer", dt);

        ProsumerPowerContributionDataset.addValue(totalProsumersContribution, "All Prosumers", dt);
//            ProsumerPowerContributionDataset.addValue(CommunityPowerContribution.getHeartRate(),"HR", CommunityPowerContribution.getTimestamp());
//            ProsumerPowerContributionDataset.addValue(CommunityPowerContribution.getBloodPressure(),"BP", CommunityPowerContribution.getTimestamp());
//            ProsumerPowerContributionDataset.addValue(vitalSign.getWeight(),"WT", vitalSign.getTimestamp());
        // }

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnShowChart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtEnergyConservePowerContributed;
    private javax.swing.JTextField txtEnergyConserveUserName;
    private javax.swing.JTextField txtEnergyReserveGradTotalOthers;
    private javax.swing.JTextField txtPowerContributedByProsumer;
    private javax.swing.JTextField txtProsumerName;
    private javax.swing.JTextField txttotalProsumersContribution;
    // End of variables declaration//GEN-END:variables
}
