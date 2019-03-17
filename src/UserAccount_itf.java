import java.rmi.*;
public interface UserAccount_itf extends Remote {
    //public void relate(int hostID) throws RemoteException;
    public int getUserID() throws RemoteException;
    public void ServerMessages(String msg) throws RemoteException;
    public void addRelation(int hostID) throws RemoteException;
    public int relationExist(int hostID) throws RemoteException;

}