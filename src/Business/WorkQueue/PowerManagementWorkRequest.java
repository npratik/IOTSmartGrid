/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

/**
 *
 * @author Pratik
 */
public class PowerManagementWorkRequest extends WorkRequest {
    
    private int currentExcessPower;
    private int currentFalingShortPower;
    private int prosumerSentpower;
    private int communitySentPower;
    private String Result;
    //private PowerManagementWorkRequest powerManagementWorkRequest;
    

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
     * @return the prosumerSentpower
     */
    public int getProsumerSentpower() {
        return prosumerSentpower;
    }

    /**
     * @param prosumerSentpower the prosumerSentpower to set
     */
    public void setProsumerSentpower(int prosumerSentpower) {
        this.prosumerSentpower = prosumerSentpower;
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
    
    
}
