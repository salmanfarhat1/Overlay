import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Servers {

    public static void main(String [] args) {
        try{
            int ID;

            ArrayList<String> TOPOLOGY;
            if (args.length < 1) {
                System.out.println("Add Arguments");
                return;
            }
            String host = args[0];
            System.out.println("host " + host );

            Registry registry = LocateRegistry.getRegistry(host);

            Initialize_itf I = (Initialize_itf) registry.lookup("Initialize");

            ServerReg_imp SE = new ServerReg_imp();
            ServerReg_itf SE_stub = (ServerReg_itf) UnicastRemoteObject.exportObject(SE, 0);
            
            VirtualTranslation_imp VI  = new VirtualTranslation_imp();
            VirtualTranslation_itf VI_stub = (VirtualTranslation_itf) UnicastRemoteObject.exportObject(VI,0);
            ID = I.getID(SE);
            SE.setID(ID);

            TOPOLOGY = I.getTopology(SE);
            System.out.println(TOPOLOGY.toString());
            SE.setTOPOLOGY(TOPOLOGY);
            //System.out.println("TOPO is " + TOPOLOGY.toString());

            registry.rebind("serverReg" + ID, SE_stub);
            registry.rebind("VirtualTran" + ID, VI_stub);

            String[] St;
            ServerReg_itf S;
            VirtualTranslation_itf V;
            System.out.println("My id is : " +ID+ " get ID from get id : " + SE.getID() );

            for(int i = 0 ; i < TOPOLOGY.size() ; i++){
                St = TOPOLOGY.get(i).split("-");
                if(Integer.parseInt(St[0]) == ID && Integer.parseInt(St[1]) < ID){ // here topology contain only neighbors no need for first cond
                    //System.out.println("I will look up" + St[1]);
                    S = (ServerReg_itf) registry.lookup("serverReg"+Integer.parseInt(St[1]));
                    V = (VirtualTranslation_itf) registry.lookup("VirtualTran"+Integer.parseInt(St[1]));
                    V.test();
    
                    VI.addVirtualNeighbor(V);
                    V.addVirtualNeighbor(VI);
                    
                    SE.addNeighbors(S);
                    S.addNeighbors(SE);
                    
                }
                if(Integer.parseInt(St[1]) == ID && Integer.parseInt(St[0]) < ID) {
                    //System.out.println("I will look up" + St[0]);
                    S = (ServerReg_itf) registry.lookup("serverReg"+Integer.parseInt(St[0]));
                    V = (VirtualTranslation_itf) registry.lookup("VirtualTran"+Integer.parseInt(St[1]));
    
                    VI.addVirtualNeighbor(V);
                    V.addVirtualNeighbor(VI);
                    
                    SE.addNeighbors(S);
                    S.addNeighbors(SE);
                    
                    //System.out.println("DONE lookup ");
                    
                }
                
    
            }
            
            //fill the matrix for the virtual
            
            ConcurrentHashMap<Integer, ServerReg_itf> Neighbors =SE.getNeighbors() ;

            Scanner sc = new Scanner(System.in);
            int choice = 0 , src =0, dest =0;

            while(1 == 1){
                Neighbors =SE.getNeighbors() ;
                System.out.println("1- send messages to neighbors\n2- print TOPOLOGY\n3- send message to :\n4- print vitual topo\n5-Send using the overlay");
                choice = sc.nextInt();
                if(choice == 1){
                    for ( Map.Entry entry : Neighbors.entrySet()) {
                        int key = (Integer)entry.getKey();
                        ServerReg_itf Neig = (ServerReg_itf)entry.getValue();

                        Neig.TestMessages("Hello from " + SE.getID());
                        System.out.println("key is " + key );
                    }
                }
                if(choice == 2){
                    SE.printTOPOLOGY();
                }
                if(choice == 3){
                    System.out.println("To whom you want to send a message : ");
                    dest = sc.nextInt();
                    SE.sendMessageTo(dest);
                }
                if(choice == 4){
                    SE.printTOPOLOGY();
                    VI.printTOPO();
                    System.out.println("\n\n");
                    VI.increasingOrderVirtualTopoV1();
                }
                if(choice == 5){
                    // send using the overlay
                    VI.setTOPOLOGY(SE.getTOPOLOGY());
                    VI.setDistanceVector();
                    VI.printDistanceVector();
                    
                    System.out.println("To whom you want to send a message : ");
                    dest = sc.nextInt();
                    
                    String SPth = VI.sendCircularTO(ID , dest);
                   
                    St = SPth.split(ID + "-");
                    String St1[] = SPth.split( "-");
                    if(St.length == 1 ) {
                        // sending to him self
                    }else {
                        
                        SE.sendMessageToV(Integer.parseInt(St1[1]) , St[1]); // St[1] contains the other part of the path
                    }
                }
                
            }
        }catch (Exception e)
        {
            System.err.println("Error on client: " + e);
        }

    }
}
