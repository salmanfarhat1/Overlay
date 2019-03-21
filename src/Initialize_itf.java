import java.rmi.*;
public interface Initialize_itf extends Remote{
    public int getID(UserAccount_itf user) throws RemoteException;
    public void relateMe(UserAccount_itf user) throws RemoteException;
    public void relateMeV1(UserAccount_itf user ,  int DestHostID) throws RemoteException;
    public String getUsersIDs() throws RemoteException;
    public void printPathMatrix() throws RemoteException;
    
}
