import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.*;

public class MainNode {
    public static void  main(String [] args) {
        try{

            Initialize_impl I = new Initialize_impl();
            Initialize_itf I_stub = (Initialize_itf) UnicastRemoteObject.exportObject(I, 0);

            Registry registry= LocateRegistry.getRegistry();

            registry.bind("Initialize", I_stub);

            System.out.println ("Server ready");
        } catch (Exception e) {
            System.err.println("Error on server :" + e) ;
            e.printStackTrace();
        }

    }
}