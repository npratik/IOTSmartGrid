/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueueEnterprise;

import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class WorkQueueEnterprise {
     private ArrayList<PowerManagementWorkRequestEnterprise> workRequestList;

    public WorkQueueEnterprise() {
        workRequestList = new ArrayList<>();
    }

    public ArrayList<PowerManagementWorkRequestEnterprise> getWorkRequestList() {
        return workRequestList;
    }
}
