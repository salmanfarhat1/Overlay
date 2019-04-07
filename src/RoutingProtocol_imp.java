import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class RoutingProtocol_imp{
    private ArrayList<String> TOPOLOGY;
    private int distanceVector[][];
    private int nodesNumber;

    public RoutingProtocol_imp(ArrayList<String > TOPOLOGY )
    {
        this.TOPOLOGY = TOPOLOGY;
        this.nodesNumber = calculateNodesNumberUsingTopology();
        this.distanceVector = new int[nodesNumber][nodesNumber];

        fillMatrix();
    }
    public void fillMatrix(){
        for(int i = 0 ; i < nodesNumber ; i++){
            for(int j = 0 ; j < nodesNumber ; j++){
                distanceVector[i][j] = 0;
            }
        }
        ArrayList<Integer> nodes  = getNodesIDS();
        //ArrayList<Integer> virtualNotIDsNode = new ArrayList<Integer>();
        ConcurrentHashMap<Integer, Integer> translationNodesID;
        translationNodesID = new ConcurrentHashMap<Integer , Integer >();

        Collections.sort(nodes);

        String[] St;
        int first = -1 ,second = -1 ;
        for(int k = 0; k < nodes.size() ; k++){
            translationNodesID.put(nodes.get(k) , k);
        }
        /*0 --> 0
          1 --> 1
          2 --> 2
          4 --> 3
         */
        // to avoid error when the size of matrix is 3x3 and we have node with value 4

        for(int i = 0; i < nodes.size() ; i++){
            for(int j = 0 ; j < TOPOLOGY.size() ; j++){
                St = TOPOLOGY.get(j).split("-");
                first = Integer.parseInt(St[0]);
                second = Integer.parseInt(St[1]);
                if( i == first){
                    distanceVector[i][translationNodesID.get(second)] = 1;
                }
                if(i == second){
                    distanceVector[i][translationNodesID.get(first)] = 1;
                }
            }
        }
    }
    public int[][] getMatrix(){
        for(int i = 0 ; i < nodesNumber ; i++){
            for(int j = 0 ; j < nodesNumber ; j++){
                distanceVector[i][j] = 0;
            }
        }
        ArrayList<Integer> nodes  = getNodesIDS();
        //ArrayList<Integer> virtualNotIDsNode = new ArrayList<Integer>();
        ConcurrentHashMap<Integer, Integer> translationNodesID;
        translationNodesID = new ConcurrentHashMap<Integer , Integer >();

        Collections.sort(nodes);

        String[] St;
        int first = -1 ,second = -1 ;
        for(int k = 0; k < nodes.size() ; k++){
            translationNodesID.put(nodes.get(k) , k);
        }
        /*0 --> 0
          1 --> 1
          2 --> 2
          4 --> 3
         */
        // to avoid error when the size of matrix is 3x3 and we have node with value 4

        for(int i = 0; i < nodes.size() ; i++){
            for(int j = 0 ; j < TOPOLOGY.size() ; j++){
                St = TOPOLOGY.get(j).split("-");
                first = Integer.parseInt(St[0]);
                second = Integer.parseInt(St[1]);
                if( i == first){
                    distanceVector[i][translationNodesID.get(second)] = 1;
                }
                if(i == second){
                    distanceVector[i][translationNodesID.get(first)] = 1;
                }
            }
        }
        return distanceVector;
    }

    public int checkIfExistInList(ArrayList<Integer> arr , int e){
        int Exist = 0;
        for(int  i = 0 ; i < arr.size() ; i++){
            if(arr.get(i) == e){
                Exist = 1;
            }
        }
        return Exist;
    }

    public void updateDimensionsAccourdingToTheModifiedTopology(ArrayList<String> TOPOLOGY){
        this.TOPOLOGY = TOPOLOGY;
        this.nodesNumber = calculateNodesNumberUsingTopology();
        distanceVector = new int[nodesNumber][nodesNumber];
        for(int i = 0 ; i < nodesNumber ; i++){
            for(int j = 0 ; j < nodesNumber ; j++){
                distanceVector[i][j] = 0;
            }
        }
    }


    public int calculateNodesNumberUsingTopology(){
        String[] St;
        ArrayList<Integer> Nodes = new ArrayList<>();
        int first =0 , second =0 ;
        for(int i =  0 ; i  < TOPOLOGY.size() ; i++ ){
            St = TOPOLOGY.get(i).split("-");
            first = Integer.parseInt(St[0]);
            second = Integer.parseInt(St[1]);
            if(checkIfExistInList(Nodes , first) == 0)
                Nodes.add(first);
            if(checkIfExistInList(Nodes , second) == 0)
                Nodes.add(second);

        }
        return Nodes.size();
    }

    public ArrayList<Integer> getNodesIDS(){
        String[] St;
        ArrayList<Integer> Nodes = new ArrayList<>();
        int first =0 , second =0 ;
        for(int i =  0 ; i  < TOPOLOGY.size() ; i++ ){
            St = TOPOLOGY.get(i).split("-");
            first = Integer.parseInt(St[0]);
            second = Integer.parseInt(St[1]);
            if(checkIfExistInList(Nodes , first) == 0)
                Nodes.add(first);
            if(checkIfExistInList(Nodes , second) == 0)
                Nodes.add(second);

        }
        return Nodes;
    }


    public void printDistanceVector(){
        System.out.println("Distance vector");
        for(int i = 0 ; i < nodesNumber ; i++)
        {
            for(int j = 0 ; j < nodesNumber ; j++)
            {
                System.out.print(distanceVector[i][j] + " ");
            }
            System.out.println("");

        }
    }

    public void TestMessages(String msg)
    {
        System.out.println(msg);
    }
}