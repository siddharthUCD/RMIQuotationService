package service;

import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;

public class DodgyDriverUnitTest {
    private static Registry registry;


    @BeforeClass
    public static void setup() {
        QuotationService ddqService = new DDQService();
        try {
            //Initializing registry with a new registry
            registry = LocateRegistry.createRegistry(1099);

            //Exposing Dodgydriver Service object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(ddqService,0);

            //Binding Dodgydriver object to registry
            registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    //Test for connection by looking up Dodgydriver service on the registry
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
        assertNotNull(service);
    }

    //Test for Dodgydriver Quotation generation
    @Test
    public void QuotationGenerationTest() throws Exception {
        ClientInfo TestClientInfo = new ClientInfo();
        DDQService TestDDQService = new DDQService();
        Quotation TestQuotation = TestDDQService.generateQuotation(TestClientInfo);
        assertNotNull(TestQuotation);
    }
}