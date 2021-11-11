package service;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.*;

import static org.junit.Assert.assertNotNull;
public class BrokerUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService lbqService = new QuotationService() {
            @Override
            public Quotation generateQuotation(ClientInfo info) throws RemoteException {
                return null;
            }
        };

        try {
            //Create a test registry
            registry = LocateRegistry.createRegistry(1099);

            //Exposing Broker Service object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(lbqService,0);

            //Binding Broker object to registry
            registry.bind(Constants.BROKER_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    //Test for connection by looking up Broker service on the registry
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.BROKER_SERVICE);

        assertNotNull(service);
    }

    //Test for Broker Quotation generation
    @Test
    public void QuotationGenerationTest() throws Exception {
        ClientInfo TestClientInfo = new ClientInfo();
        AFQService TestAFQService = new AFQService();

        Quotation TestQuotation = TestAFQService.generateQuotation(TestClientInfo);
        assertNotNull(TestQuotation);
    }
}