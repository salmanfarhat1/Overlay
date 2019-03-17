import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class Initialize_impl implements Initialize_itf{

    private int counterID;
    private List<UserAccount_itf> ClientsList;

    public Initialize_impl()
    {
        this.counterID = 0;
        ClientsList = new CopyOnWriteArrayList<>();
    }

    public void relateMe(UserAccount_itf user) throws RemoteException{
//        System.out.println("ID host is " + user.getUserID() + " to be related to is")
//        if(user.getUserID() !=count){
//            if(user.relationExist(user.getUserID() + 1) == 0)
//                user.addRelation(user.getUserID() + 1);
//        }


    }
    public UserAccount_itf getObjectFromID(int ID) throws RemoteException{
        for(int i = 0 ; i < ClientsList.size() ;i++){
            if(ClientsList.get(i).getUserID() == ID)
                return ClientsList.get(i);
        }
        return null;
    }

    public void relateMeV1(UserAccount_itf user ,  int DestHostID) throws RemoteException{
        user.addRelation(DestHostID);
        UserAccount_itf UDest = getObjectFromID(DestHostID);
        UDest.addRelation(user.getUserID());

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
        counterID++;
        ClientsList.add(user);
        return counterID;
    }
}