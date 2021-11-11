package service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        QuotationService afqService = new GPQService();
        try {
            //Declaring registry
            Registry registry;

            //To handle when called without arguments
            if (args.length == 0) {
                registry = LocateRegistry.getRegistry(1099);
            } else {
                String host = "localhost";
                registry = LocateRegistry.getRegistry(host, 1099);
            }

            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);

            // Register the object with the RMI Registry
            registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

}