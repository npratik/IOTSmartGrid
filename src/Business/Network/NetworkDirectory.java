/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Network;

//import Business.Enterprise.Enterprise;
import java.util.ArrayList;

/**
 *
 * @author Pratik
 */
public class NetworkDirectory {

    private ArrayList<Network> networkList;

    public NetworkDirectory() {
        networkList = new ArrayList<>();
    }

    /**
     * @return the networkList
     */
    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    
    public Network createNetwork(String name, Network.NetworkType type){
        
        Network network = null;
        if (type == Network.NetworkType.PowerRegulationAuthority){
            network = new PowerAuthorityNetwork(name); // Check if this works correctly***
            networkList.add(network);
        }
//        Network network = new PowerAuthorityNetwork();
//        networkList.add(network);
        return network;
    }

    public Network searchNetwork(String name){
        for(Network net : networkList){
             if(name.equalsIgnoreCase(net.getName())){
                return net;
            }
        }
        return null;
    }
    
     public boolean checkIfNetworkNameIsUnique(String name) {
        for (Network net : networkList) {
            if (name.equalsIgnoreCase(net.getName())) {
                return false;
            }
        }
        return true;
    }
    public void deleteNetwork(Network network) {
        networkList.remove(network);
    }
    
    
}
