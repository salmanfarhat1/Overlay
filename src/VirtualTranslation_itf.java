import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public interface VirtualTranslation_itf extends Remote{
    public void test() throws RemoteException;
    public void addVirtualNeighbor(VirtualTranslation_itf V) throws RemoteException;
    public String sendCircularTO(int src ,int  dest ) throws RemoteException;
    
    
    
}
