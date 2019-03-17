import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;


public class Clients {
    public static void main(String [] args) {
        try{
            int ID;
            if (args.length < 1) {
                System.out.println("Add Arguments");
                return;
            }
            String host = args[0];
            System.out.println("host " + host );

            Registry registry = LocateRegistry.getRegistry(host);

            Initialize_itf I = (Initialize_itf) registry.lookup("Initialize");


            UserAccount_impl UA = new UserAccount_impl();
            UserAccount_itf UA_stub = (UserAccount_itf) UnicastRemoteObject.exportObject(UA, 0);

            ID = I.getID(UA);
            UA.setID(ID);
            System.out.println("My id is : " +ID+ " get ID from get id : " + UA.getID() );

            Scanner sc = new Scanner(System.in);
            int choice = 0;
            String usersIDS;
            int destHostRelate;
            while(true)
            {
                System.out.println("1- Show users list\n2- Relate Me\n3- My relations");
                choice = sc.nextInt();
                if(choice == 1)
                {
                    usersIDS = I.getUsersIDs();
                    System.out.println(usersIDS);
                }
                else if(choice ==2)
                {
                    System.out.println("\nEnter to whom you want to be related: ");
                    destHostRelate = sc.nextInt();
                    I.relateMeV1(UA,destHostRelate);
                }
                else if(choice == 3)
                {
                    UA.printMyRelations();
                }
                else{
                    System.out.println("Choice is : "+choice);
                }
            }


        }catch (Exception e)
        {
            System.err.println("Error on client: " + e);
        }

    }
}
