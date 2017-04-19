/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueueEnterprise;

import Business.Enterprise.CommunityEnterprise;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PowerManagementWorkRequest;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author Pratik
 */
public class PowerManagementWorkRequestEnterprise  {
    
    private String message;
    private UserAccount sender;
    private UserAccount receiver;
    private String status;
    private Date requestDate;
    private Date resolveDate;
    private int currentExcessPower;
    private int currentFalingShortPower;
    //private int prosumerSentpower;
    private int communitySentPower;
    private String Result;
    private PowerManagementWorkRequest powerManagementWorkRequest;
    private String bulkRequest;
    private WorkQueue workQueue;
    private CommunityEnterprise communityEnterprise;

    
     public PowerManagementWorkRequestEnterprise(){
        requestDate = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }
    /**
     * @return the currentExcessPower
     */
    public int getCurrentExcessPower() {
        return currentExcessPower;
    }

    /**
     * @param currentExcessPower the currentExcessPower to set
     */
    public void setCurrentExcessPower(int currentExcessPower) {
        this.currentExcessPower = currentExcessPower;
    }

    /**
     * @return the currentFalingShortPower
     */
    public int getCurrentFalingShortPower() {
        return currentFalingShortPower;
    }

    /**
     * @param currentFalingShortPower the currentFalingShortPower to set
     */
    public void setCurrentFalingShortPower(int currentFalingShortPower) {
        this.currentFalingShortPower = currentFalingShortPower;
    }

    /**
     * @return the communitySentPower
     */
    public int getCommunitySentPower() {
        return communitySentPower;
    }

    /**
     * @param communitySentPower the communitySentPower to set
     */
    public void setCommunitySentPower(int communitySentPower) {
        this.communitySentPower = communitySentPower;
    }

    /**
     * @return the Result
     */
    public String getResult() {
        return Result;
    }

    /**
     * @param Result the Result to set
     */
    public void setResult(String Result) {
        this.Result = Result;
    }

    /**
     * @return the powerManagementWorkRequest
     */
    public PowerManagementWorkRequest getPowerManagementWorkRequest() {
        return powerManagementWorkRequest;
    }

    /**
     * @param powerManagementWorkRequest the powerManagementWorkRequest to set
     */
    public void setPowerManagementWorkRequest(PowerManagementWorkRequest powerManagementWorkRequest) {
        this.powerManagementWorkRequest = powerManagementWorkRequest;
    }

    /**
     * @return the bulkRequest
     */
    public String getBulkRequest() {
        return bulkRequest;
    }

    /**
     * @param bulkRequest the bulkRequest to set
     */
    public void setBulkRequest(String bulkRequest) {
        this.bulkRequest = bulkRequest;
    }

    /**
     * @return the workQueue
     */
    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    /**
     * @param workQueue the workQueue to set
     */
    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    @Override
    public String toString() {
        return  message;
    }

    /**
     * @return the communityEnterprise
     */
    public CommunityEnterprise getCommunityEnterprise() {
        return communityEnterprise;
    }

    /**
     * @param communityEnterprise the communityEnterprise to set
     */
    public void setCommunityEnterprise(CommunityEnterprise communityEnterprise) {
        this.communityEnterprise = communityEnterprise;
    }
    
    
}
