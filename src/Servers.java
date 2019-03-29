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


            ID = I.getID(SE);
            SE.setID(ID);

            TOPOLOGY = I.getTopology(SE);
            SE.setTOPOLOGY(TOPOLOGY);
            System.out.println("TOPO is " + TOPOLOGY.toString());



            registry.rebind("serverReg" + ID, SE_stub);

//            if(ID == 1){
//                ServerReg_itf S = (ServerReg_itf) registry.look up("serverReg0");
//            }
            String[] St;
            ServerReg_itf S;

            System.out.println("My id is : " +ID+ " get ID from get id : " + SE.getID() );

            for(int i = 0 ; i < TOPOLOGY.size() ; i++){
                St = TOPOLOGY.get(i).split("-");
                if(Integer.parseInt(St[0]) == ID && Integer.parseInt(St[1]) < ID){ // here topology contain only neighbors no need for first cond
                    System.out.println("I will look up" + St[1]);
                    S = (ServerReg_itf) registry.lookup("serverReg"+Integer.parseInt(St[1]));
                    SE.addNeighbors(S);
                    S.addNeighbors(SE);
                }
                if(Integer.parseInt(St[1]) == ID && Integer.parseInt(St[0]) < ID) {
                    System.out.println("I will look up" + St[0]);
                    S = (ServerReg_itf) registry.lookup("serverReg"+Integer.parseInt(St[0]));
                    SE.addNeighbors(S);
                    S.addNeighbors(SE);
                }
            }
//            System.out.println("Size of my neighbros list is "+ SE.getNeighbors().size());
//            for(int i = 0 ; i < SE.getNeighbors().size(); i++){
//                SE.getNeighbors().get(i).TestMessages("Hello from " + SE.getID());
//            }
            ConcurrentHashMap<Integer, ServerReg_itf> Neighbors =SE.getNeighbors() ;


            Scanner sc = new Scanner(System.in);
            int choice = 0;



            while(1 == 1){
                Neighbors =SE.getNeighbors() ;
                System.out.println("1- send messages to neighbors\n2- print TOPOLOGY");
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
            }
        }catch (Exception e)
        {
            System.err.println("Error on client: " + e);
        }

    }
}


//public class Servers {
//    public static void main(String [] args) {
//        try{
//            int ID;
//            if (args.length < 2) {
//                System.out.println("Add Arguments");
//                return;
//            }
//            String host = args[0];
//            //System.out.println("host " + host );
//            ID = Integer.parseInt(args[1]);
//            if(ID == 0){
//              Registry registry= LocateRegistry.getRegistry();
//              reg0_imp r = new reg0_imp(ID);
//              reg0_itf r_stub = (reg0_itf) UnicastRemoteObject.exportObject(r, 0);
//              registry.bind("reg0", r_stub);
//
//
//
//
//            }
//            else if(ID == 1){
//              Registry registry= LocateRegistry.getRegistry(host);
//              reg0_itf R = (reg0_itf) registry.lookup("reg0");
//              R.relateTo(0,1);
//
//              reg0_imp r1 = new reg0_imp(ID);
//              reg0_itf r1_stub = (reg0_itf) UnicastRemoteObject.exportObject(r1, 0);
//              registry.bind("reg1", r1_stub);
//            }
//
//
//            //System.out.println("ID is " + ID);
//
//            System.out.println ("Server ready");
//
//
//          } catch (Exception e) {
//              System.err.println("Error on server :" + e) ;
//              e.printStackTrace();
//          }
//
//    }
//}
