import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class VirtualTranslation_imp implements VirtualTranslation_itf{
    private ArrayList<String> TOPOLOGY;
    private ArrayList<Integer> nNumbers;
    private ArrayList<VirtualTranslation_itf> neighbors;
    private int distanceVector[][];
    
    public VirtualTranslation_imp(){
        this.TOPOLOGY = new ArrayList<String>();
        this.nNumbers = new ArrayList<Integer>();
        this.neighbors = new ArrayList<VirtualTranslation_itf>();
       
    
    }
    
    public void addVirtualNeighbor(VirtualTranslation_itf V) throws RemoteException{
        neighbors.add(V);
        System.out.println("added");
    }
    
    public void setDistanceVector(){
        RoutingProtocol_imp routing = new RoutingProtocol_imp(TOPOLOGY);
        distanceVector = routing.getMatrix();
    }
    
    public void setTOPOLOGY(ArrayList<String> TOPOLOGY) {
        this.TOPOLOGY = TOPOLOGY;
        increasingOrderVirtualTopoV1();
        TOPOLOGY.add((nNumbers.size()-1)+"-"+nNumbers.get(0));
        printTOPO();
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
    
    public void printTOPO(){
        System.out.println("Size is :" + TOPOLOGY.size());
        for(int i = 0 ; i < TOPOLOGY.size(); i++){
            System.out.print(TOPOLOGY.get(i) + "///" );
    
        }
        System.out.println("");
    }
    public int existInList(ArrayList<Integer> L , int e ){
        for(int i =0  ; i < L.size() ; i++ ){
            if(e == L.get(i))
                return 1;
        }
        return 0;
    }
    public void increasingOrderVirtualTopoV1(){
        String St[];
        for(int i = 0 ; i < TOPOLOGY.size() ; i++ ){
            St = TOPOLOGY.get(i).split("-");
            if(existInList(nNumbers , Integer.parseInt(St[0])) == 0){
                this.nNumbers.add(Integer.parseInt(St[0]));
            }
            if(existInList(nNumbers , Integer.parseInt(St[1])) == 0){
                this.nNumbers.add(Integer.parseInt(St[1]));
            }
            
        }
    
        Collections.sort(nNumbers);
//        System.out.println("Size is  : "+ nNumbers.size());
//        System.out.println(this.nNumbers.toString());
        
    }
    
    public String sendCircularTO(int src ,int  dest ) throws RemoteException{
        ShortestPathGeneration SPG = new ShortestPathGeneration(nNumbers.size());
        String S = SPG.dijkstra(distanceVector, src, dest); // src is 3 dest is 2
        System.out.println("Path is :"+S);
        return S;
    }
    
    
    
    public void test() throws RemoteException{
        System.out.println("Test Okay continue and good luck ");
    }
    
}
