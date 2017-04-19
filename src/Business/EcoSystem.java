/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Network.Network;
import Business.Network.NetworkDirectory;
import Business.Organization.Organization;
import Business.Role.Role;
import Business.Role.SystemAdminRole;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class EcoSystem extends Organization {

    private static EcoSystem business;
    //private ArrayList<Network> networkList;
    private NetworkDirectory networkDirectory;

    public static EcoSystem getInstance() {
        if (business == null) {
            business = new EcoSystem();
        }
        return business;
    }

    private EcoSystem() {
        super(null); // Check and confirm*****
        //networkList = new ArrayList<>();
         networkDirectory = new NetworkDirectory();
    }
    
    

//    public ArrayList<Network> getNetworkList() {
//        return networkList;
//    }
//
//    public Network createAndAddNetwork() {
//        Network network = new Network();
//        networkList.add(network);
//        return network;
//    }

    @Override                     //Check**********
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new SystemAdminRole());
        return roleList;
    }

    // this is already handled in user account and network list is 
    // craeted and handled in network directory
    // hence removing Or commenting some code******
//    public boolean checkIfUsernameIsUnique(String username) {
//
//        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(username)) {
//            return false;
//        }
//
//        for (Network network : networkList) {
//        }
//
//        return true;
//    }

    /**
     * @return the networkDirectory
     */
    public NetworkDirectory getNetworkDirectory() {
        return networkDirectory;
    }
    
}
