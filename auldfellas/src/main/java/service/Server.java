package service;

import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            //Declaring Registry & QuotationService objects
            Registry registry = null;
            QuotationService afqService = new AFQService();

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

            //Binding Auldfellas object to registry
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}