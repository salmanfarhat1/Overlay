import java.rmi.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class Initialize_impl implements Initialize_itf{

    private int counterID; // (counter+1) is the number of machines (nodes)
    private String[] TOPOLOGY;
    private List<ServerReg_itf> ServersList;

    public Initialize_impl(String[] TOPOLOGY)
    {
        this.counterID = -1;
        this.TOPOLOGY = TOPOLOGY;
        ServersList = new CopyOnWriteArrayList<>();

    }

    public int getID(ServerReg_itf server) throws RemoteException
    {
        this.counterID++;
        ServersList.add(server);
        return this.counterID;
    }

    public ArrayList<String> getTopology(ServerReg_itf server) throws RemoteException
    {


        ArrayList<String> serverNeighbors = new ArrayList<>();
        String[] St;
        for(int i = 0 ; i < TOPOLOGY.length ; i++){
            St = TOPOLOGY[i].split("-");

            if(Integer.parseInt(St[0]) == server.getServerID() || Integer.parseInt(St[1]) == server.getServerID()){
                serverNeighbors.add(TOPOLOGY[i]);
            }
        }

        return serverNeighbors;
    }

}
