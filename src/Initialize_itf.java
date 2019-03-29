import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public interface Initialize_itf extends Remote{
    public int getID(ServerReg_itf user) throws RemoteException;
    public ArrayList<String> getTopology(ServerReg_itf server) throws RemoteException;

}
