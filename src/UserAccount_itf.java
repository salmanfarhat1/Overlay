import java.rmi.*;
import java.util.*;
import java.io.*;
public interface UserAccount_itf extends Remote {
    public int getUserID() throws RemoteException;
    public void ServerMessages(String msg) throws RemoteException;
    public void addRelation(int hostID) throws RemoteException;
    public int relationExist(int hostID) throws RemoteException;
    public ArrayList<Integer> getDistanceVector(int clientsNumber) throws RemoteException;
}
