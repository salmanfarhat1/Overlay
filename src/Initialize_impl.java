import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class Initialize_impl implements Initialize_itf{

    private int counterID; // (counter+1) is the number of machines (nodes)
    private List<UserAccount_itf> ClientsList;
    private ConcurrentHashMap<Integer, ArrayList<Integer>> pathMatrix;

    public Initialize_impl()
    {
        this.counterID = -1;
        ClientsList = new CopyOnWriteArrayList<>();
        pathMatrix = new ConcurrentHashMap<Integer , ArrayList<Integer> >(); // every node has it's corresponding vector distance
    }

    public void updatePathMatrix() throws RemoteException{
      UserAccount_itf client = null ;
      int clientID;
      ArrayList<Integer> list;


      for(int i = 0; i < ClientsList.size() ;i++)
      {
        client = ClientsList.get(i);
        clientID = client.getUserID();


        list = pathMatrix.get(clientID);
        list = client.getDistanceVector(ClientsList.size()); // getting the vector distance to all nodes

        for(int j = 0 ; j < ClientsList.size() ; j++) { // array list same size of the clients size
            System.out.print(list.get(j) + "-");
        }
        System.out.println("");

      }
        System.out.println("END ---------------------------------------------- END");
    }
    public void printPathMatrix() throws RemoteException{
        UserAccount_itf client = null ;
        int clientID;
        ArrayList<Integer> list;


        for(int i = 0; i < ClientsList.size() ;i++)
        {
            client = ClientsList.get(i);
            clientID = client.getUserID();


            list = pathMatrix.get(clientID);
            list = client.getDistanceVector(ClientsList.size());

            for(int j = 0 ; j < ClientsList.size() ; j++) { // array list same size of the clients size
                System.out.print(list.get(j) + "-");
            }
            System.out.println("");

        }
        System.out.println("END ---------------------------------------------- END");
    }


    public void relateMeV1(UserAccount_itf user ,  int DestHostID) throws RemoteException{
        user.addRelation(DestHostID);
        UserAccount_itf UDest = getObjectFromID(DestHostID);
        UDest.addRelation(user.getUserID());
        updatePathMatrix();

    }

    public UserAccount_itf getObjectFromID(int ID) throws RemoteException{
        for(int i = 0 ; i < ClientsList.size() ;i++){
            if(ClientsList.get(i).getUserID() == ID)
                return ClientsList.get(i);
        }
        return null;
    }

    public void relateMe(UserAccount_itf user) throws RemoteException{
//        System.out.println("ID host is " + user.getUserID() + " to be related to is")
//        if(user.getUserID() !=count){
//            if(user.relationExist(user.getUserID() + 1) == 0)
//                user.addRelation(user.getUserID() + 1);
//        }


    }

    public String getUsersIDs() throws RemoteException{
        String S = "";
        for(int i =0 ; i < ClientsList.size(); i++){
            S =S + "User :" + ClientsList.get(i).getUserID()+"\n";
        }
        return S;
    }


    public int getID(UserAccount_itf user) throws RemoteException
    {
        this.counterID++;
        ClientsList.add(user);
        pathMatrix.put(counterID , new ArrayList<Integer>() );
        updatePathMatrix();
        return this.counterID;
    }

}
