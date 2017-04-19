/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.PowerManagement;

//import Business.PowerManagement.PowerManagement;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class ProsumerDirectory {
      private ArrayList<PowerManagement> prosumerList;

    public ProsumerDirectory() {
        prosumerList = new ArrayList<>();
    }

    /**
     * @return the prosumerList
     */
    public ArrayList<PowerManagement> getProsumerList() {
        return prosumerList;
    }

    /**
     * @param prosumerList the prosumerList to set
     */
    public void setProsumerList(ArrayList<PowerManagement> prosumerList) {
        this.prosumerList = prosumerList;
    }

    public PowerManagement addProsumer() {
        PowerManagement prosumer = new PowerManagement();
        prosumerList.add(prosumer);
        return prosumer;
    }

    public void deleteProsumer(PowerManagement prosumer) {
        prosumerList.remove(prosumer);
    }

    public PowerManagement searchProsumer(String personNumber) {
        for (PowerManagement p : prosumerList) {
            if (p.getProsumerId().equalsIgnoreCase(personNumber)) {

                return p;
            }
        }
        return null;
    }

}
