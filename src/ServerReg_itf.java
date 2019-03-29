import java.rmi.*;
import java.util.*;
import java.io.*;
public interface ServerReg_itf extends Remote {
    public int getServerID() throws RemoteException;
    public void ServerMessages(String msg) throws RemoteException;
    public void TestMessages(String msg) throws RemoteException;
    public void addNeighbors(ServerReg_itf neighbor)  throws RemoteException;
    public void updateTOPOLOGY(ArrayList<String> TOPO) throws RemoteException;
}
