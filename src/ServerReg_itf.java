import java.rmi.*;
import java.util.*;
import java.io.*;
public interface ServerReg_itf extends Remote {
    public int getServerID() throws RemoteException;
    public void ServerMessages(String msg) throws RemoteException;
    public void TestMessages(String msg) throws RemoteException;
    public void sendMessageTo(int dest) throws RemoteException;
    public void sendMessageFromTo(int src ,int dest , String Path) throws RemoteException;
    public void addNeighbors(ServerReg_itf neighbor)  throws RemoteException;
    public void updateTOPOLOGY(ArrayList<String> TOPO) throws RemoteException;
    public void sendMessageToV(  int dest ,String navigatingPath ) throws RemoteException;
    public void sendMessageFromToV(int src ,int dest , String Path , String navigatingPath) throws RemoteException;
}
