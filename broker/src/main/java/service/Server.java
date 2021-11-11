package service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
            BrokerService LBSService = new LocalBrokerService();

            //Create a new registry
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create the Remote Object
            BrokerService quotationService = (BrokerService)
                    UnicastRemoteObject.exportObject(LBSService,0);

            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
            e.printStackTrace();
        }
    }
}