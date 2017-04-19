/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.PowerManagement;

import Business.Person.Person;

/**
 *
 * @author Pratik
 */
public class PowerManagement {

   
    
    private String prosumerId;
    private int currentPowerRequired; // currentPowerUtilized
    private int currentPowerInput;
    
   
    //private int currentFalingShortPower; //Check**** if this is required****
    private int iD;
    private static int counter = 1;
    
    private int currentUtilizedPower;
    private int currentPowerGenerated; // through renewable sources
    private int currentExcessPower;
    private int currentPowerFallingShort;
    
    private String automatePowerDistribution;
    private int setThresholdForPowerSupply;
    
    private int energyConserveAvailablePower;
    
    private int cityNetworkAvailablePower;
    
    
    
    public PowerManagement() {
        iD = counter;
        prosumerId = "Prosumer " + iD;
        counter++;
    }

    /**
     * @return the prosumerId
     */
    public String getProsumerId() {
        return prosumerId;
    }

    /**
     * @param prosumerId the prosumerId to set
     */
    public void setProsumerId(String prosumerId) {
        this.prosumerId = prosumerId;
    }

    /**
     * @return the currentPowerRequired
     */
    public int getCurrentPowerRequired() {
        return currentPowerRequired;
    }

    /**
     * @param currentPowerRequired the currentPowerRequired to set
     */
    public void setCurrentPowerRequired(int currentPowerRequired) {
        this.currentPowerRequired = currentPowerRequired;
    }

    /**
     * @return the currentPowerInput
     */
    public int getCurrentPowerInput() {
        return currentPowerInput;
    }

    /**
     * @param currentPowerInput the currentPowerInput to set
     */
    public void setCurrentPowerInput(int currentPowerInput) {
        this.currentPowerInput = currentPowerInput;
    }

    /**
     * @return the currentPowerGenerated
     */
    public int getCurrentPowerGenerated() {
        return currentPowerGenerated;
    }

    /**
     * @param currentPowerGenerated the currentPowerGenerated to set
     */
    public void setCurrentPowerGenerated(int currentPowerGenerated) {
        this.currentPowerGenerated = currentPowerGenerated;
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
//    public int getCurrentFalingShortPower() {
//        return currentFalingShortPower;
//    }

    /**
     * @param currentFalingShortPower the currentFalingShortPower to set
     */
//    public void setCurrentFalingShortPower(int currentFalingShortPower) {
//        this.currentFalingShortPower = currentFalingShortPower;
//    }

    /**
     * @return the iD
     */
    public int getiD() {
        return iD;
    }

    /**
     * @param iD the iD to set
     */
    public void setiD(int iD) {
        this.iD = iD;
    }

    /**
     * @return the currentUtilizedPower
     */
    public int getCurrentUtilizedPower() {
        return currentUtilizedPower;
    }

    /**
     * @param currentUtilizedPower the currentUtilizedPower to set
     */
    public void setCurrentUtilizedPower(int currentUtilizedPower) {
        this.currentUtilizedPower = currentUtilizedPower;
    }

    /**
     * @return the currentPowerFallingShort
     */
    public int getCurrentPowerFallingShort() {
        return currentPowerFallingShort;
    }

    /**
     * @param currentPowerFallingShort the currentPowerFallingShort to set
     */
    public void setCurrentPowerFallingShort(int currentPowerFallingShort) {
        this.currentPowerFallingShort = currentPowerFallingShort;
    }

    /**
     * @return the automatePowerDistribution
     */
    public String getAutomatePowerDistribution() {
        return automatePowerDistribution;
    }

    /**
     * @param automatePowerDistribution the automatePowerDistribution to set
     */
    public void setAutomatePowerDistribution(String automatePowerDistribution) {
        this.automatePowerDistribution = automatePowerDistribution;
    }

    /**
     * @return the setThresholdForPowerSupply
     */
    public int getSetThresholdForPowerSupply() {
        return setThresholdForPowerSupply;
    }

    /**
     * @param setThresholdForPowerSupply the setThresholdForPowerSupply to set
     */
    public void setSetThresholdForPowerSupply(int setThresholdForPowerSupply) {
        this.setThresholdForPowerSupply = setThresholdForPowerSupply;
    }

    /**
     * @return the energyConserveAvailablePower
     */
    public int getEnergyConserveAvailablePower() {
        return energyConserveAvailablePower;
    }

    /**
     * @param energyConserveAvailablePower the energyConserveAvailablePower to set
     */
    public void setEnergyConserveAvailablePower(int energyConserveAvailablePower) {
        this.energyConserveAvailablePower = energyConserveAvailablePower;
    }

    /**
     * @return the cityNetworkAvailablePower
     */
    public int getCityNetworkAvailablePower() {
        return cityNetworkAvailablePower;
    }

    /**
     * @param cityNetworkAvailablePower the cityNetworkAvailablePower to set
     */
    public void setCityNetworkAvailablePower(int cityNetworkAvailablePower) {
        this.cityNetworkAvailablePower = cityNetworkAvailablePower;
    }
    
    
    
}
