import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class UserAccount_impl implements UserAccount_itf{

    private int ID;
    private ArrayList<Integer> relatedTo;


    public UserAccount_impl() {
        this.ID = -1;
        this.relatedTo = new ArrayList<Integer>();
    }

    public int relationExist(int hostID) throws RemoteException{
        for(int i =0 ; i < relatedTo.size(); i++){
            if(relatedTo.get(i) == hostID)
                return 1;
        }
        return 0;

    }

    public void ServerMessages(String msg) throws RemoteException{
        System.out.println(msg);
    }
    public int getUserID() throws RemoteException{
        return this.ID;
    }

    public ArrayList<Integer> getDistanceVector(int clientsNumber) throws RemoteException{
       ArrayList<Integer> distances = new ArrayList<Integer>();
        int exist = 0;

      for(int i = 0 ; i < clientsNumber ; i++ )
      {
          for(int j = 0 ; j < relatedTo.size() ; j++ )
          {
              if(i == relatedTo.get(j)) {

                  //distances.get(i).add(1); // int this version we suppose that it is unweighted
                  exist = 1;

              }
          }
          if(exist == 1){
              exist = 0;
              distances.add(1);
          }
          else
              distances.add(0);

      }

        /*for(int i = 0;  i<distances.length ; i++ )
            System.out.print("-" + distances[i]);
        System.out.println("");*/
        //System.out.println("size is "+clientsNumber);


        return distances;
    }

    public void addRelation(int hostID) throws RemoteException{
        this.relatedTo.add(hostID);
    }

    public void printMyRelations(){
        String S = "Related to : ";
        for(int i = 0 ; i < relatedTo.size() ; i++){
            S = S +"-"+relatedTo.get(i);
        }
        System.out.println(S);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
