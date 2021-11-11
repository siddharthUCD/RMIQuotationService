package service;

import service.DDQService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            //Declaring registry and Dodgydriver Quotationservice object
            Registry registry = null;
            QuotationService ddqService = new DDQService();

            //To handle when called without arguments
            if (args.length == 0) {
                registry = LocateRegistry.getRegistry(1099);
            } else {
                String host = "localhost";
                registry = LocateRegistry.getRegistry(host, 1099);
            }

            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(ddqService,0);

            // Register the object with the RMI Registry
            registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}