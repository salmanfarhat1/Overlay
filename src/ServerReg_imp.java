import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ServerReg_imp implements ServerReg_itf{
    
    private int ID;
    private ArrayList<String> TOPOLOGY;
    private int distanceVector[][];
    private ConcurrentHashMap<Integer, ServerReg_itf> Neighbors;
    private int nodesNumber;
    
    
    
    public ServerReg_imp() {
        this.ID = -1;
        this.TOPOLOGY = new ArrayList<String> ();
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
        int Modified = 0;
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
                    Modified = 1;
                }
                existInMyTOPO = 0;
            }
        }
        //System.out.println("\n\nHEY\n\n");
        RoutingProtocol_imp routing = new RoutingProtocol_imp(TOPOLOGY ); // to calculate the matrix once it ready we will rock
        //routing.TestMessages(" Hello ");
        nodesNumber  = routing.calculateNodesNumberUsingTopology();
        //System.out.println("Size of the Topology is : " + nodesNumber);
        //routing.printDistanceVector();
        distanceVector = routing.getMatrix();
        
        //System.out.println("FROM MAIN SERVER");
        //printDistanceVector();
        if(Modified == 1)
            SendMyConnectionsToMyNeighbors();
    }
    public void printDistanceVector(){
        System.out.println("Distance vector");
        for(int i = 0 ; i < distanceVector.length ; i++)
        {
            for(int j = 0 ; j < distanceVector[i].length ; j++)
            {
                System.out.print(distanceVector[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public void sendMessageTo(  int dest) throws RemoteException{
        ShortestPathGeneration SPG = new ShortestPathGeneration(nodesNumber);
        String S = SPG.dijkstra(distanceVector, this.ID, dest); // src is 3 dest is 2
        System.out.println("Path is :"+S);
        String St[] = S.split(ID+"-"); // split to have the path without the sender
        String St1[]= St[1].split("-"); // to take next node Id split the path it is in cell 0
        int nextNodeID = Integer.parseInt(St1[0]);
        Neighbors.get(nextNodeID).sendMessageFromTo(ID , dest , St[1] ); // rest of the path 1->3 :2-3
    }
    public void sendMessageFromTo(int src ,int dest , String Path) throws RemoteException{
        if(ID == dest ){
            System.out.println("Hello message from "+ src);
        }
        else{
            String St[] = Path.split(ID+"-");
            String St1[] = St[1].split("-");
            int nextNodeID = Integer.parseInt(St1[0]);
            
            Neighbors.get(nextNodeID).sendMessageFromTo(src , dest , St[1] ); // rest of the path 1->3 :2-3
        }
    }
    public void sendMessageToV(  int dest ,String navigatingPath ) throws RemoteException{
        ShortestPathGeneration SPG = new ShortestPathGeneration(nodesNumber);
        String S = SPG.dijkstra(distanceVector, this.ID, dest); // src is 3 dest is 2
        System.out.println("Path is :"+S);
        String St[] = S.split(ID+"-"); // split to have the path without the sender
        String St1[]= St[1].split("-"); // to take next node Id split the path it is in cell 0
        int nextNodeID = Integer.parseInt(St1[0]);
        Neighbors.get(nextNodeID).sendMessageFromToV(ID , dest , St[1] , navigatingPath ); // rest of the path 1->3 :2-3
    }
    public void sendMessageFromToV(int src ,int dest , String Path , String navigatingPath) throws RemoteException{
        if(ID == dest ){
            System.out.println("Hello message from "+ src);
            String NavRest[] = navigatingPath.split(ID+"-");
            String NavRest1[] = navigatingPath.split("-");
            if(NavRest.length > 1 )
                sendMessageToV(Integer.parseInt(NavRest1[1]) , NavRest[1]);
        }
        else{
            String St[] = Path.split(ID+"-");
            String St1[] = St[1].split("-");
            int nextNodeID = Integer.parseInt(St1[0]);
            
            String NavRest[];
            
            
            Neighbors.get(nextNodeID).sendMessageFromToV(src , dest , St[1] ,navigatingPath  ); // rest of the path 1->3 :2-3
        }
    }
    
    
    public void printTOPOLOGY(){
        for(int i = 0; i < TOPOLOGY.size() ; i++){
            System.out.print(TOPOLOGY.get(i) + "//");
        }
        System.out.println();
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
