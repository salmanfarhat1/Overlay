import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ServerReg_imp implements ServerReg_itf{

    private int ID;
   // private List<ServerReg_itf> Neighbors;
    ArrayList<String> TOPOLOGY;
    private ConcurrentHashMap<Integer, ServerReg_itf> Neighbors;



    public ServerReg_imp() {
        this.ID = -1;
        this.TOPOLOGY = TOPOLOGY;
        Neighbors = new ConcurrentHashMap<Integer , ServerReg_itf >();
    }
    public void TestMessages(String msg) throws RemoteException{
        System.out.println(msg);
    }

    public void ServerMessages(String msg) throws RemoteException{
        System.out.println(msg);
    }

    public int getServerID() throws RemoteException{
        return this.ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public void addNeighbors(ServerReg_itf neighbor)  throws RemoteException{ // here I'm adding neighbor for me and also
                                                                        // one can call in another to add himself in his neighbor list
        this.Neighbors.put(neighbor.getServerID() ,neighbor);


        SendMyConnectionsToMyNeighbors();
    }
    public ArrayList<ServerReg_itf> getListOfNeighbors(){

        ArrayList<ServerReg_itf> neigh = new ArrayList<ServerReg_itf>();

        for ( Map.Entry entry : Neighbors.entrySet()) {
            ServerReg_itf Neig = (ServerReg_itf)entry.getValue();
            neigh.add(Neig);
        }

        return neigh;
    }
    public void SendMyConnectionsToMyNeighbors() throws RemoteException{

        ArrayList<ServerReg_itf> neigh = getListOfNeighbors(); // array of my neighbors

       // System.out.println("I have one neighbor which is :" + neigh.size() + " ID " + neigh.get(0).getServerID())  ;

        for(int i = 0 ;i< neigh.size() ; i++){
            neigh.get(i).updateTOPOLOGY(TOPOLOGY);
        }

    }
    public void updateTOPOLOGY(ArrayList<String> TOPO) throws RemoteException{

        String[] St;
        int j;
        int existInMyTOPO=0;
        for(int i = 0 ; i < TOPO.size() ; i++){
            St = TOPO.get(i).split("-");
            if(Integer.parseInt(St[0]) != ID || Integer.parseInt(St[1]) != ID){
                for(j = 0 ; j  < TOPOLOGY.size() ; j++){
                    if(TOPOLOGY.get(j).equals(TOPO.get(i))){
                        existInMyTOPO = 1;
                    }
                }
                if(existInMyTOPO == 0){
                    TOPOLOGY.add(TOPO.get(i));
                }
                existInMyTOPO = 0;
            }
        }

    }
    public void printTOPOLOGY(){
        for(int i = 0; i < TOPOLOGY.size() ; i++){
            System.out.print(TOPOLOGY.get(i) + "***");
        }
    }

    public ConcurrentHashMap<Integer, ServerReg_itf> getNeighbors() {
        return Neighbors;
    }

    public void setNeighbors(ConcurrentHashMap<Integer, ServerReg_itf> neighbors) {
        Neighbors = neighbors;
    }

    public ArrayList<String> getTOPOLOGY() {
        return TOPOLOGY;
    }

    public void setTOPOLOGY(ArrayList<String> TOPOLOGY) {
        this.TOPOLOGY = TOPOLOGY;
    }


}
