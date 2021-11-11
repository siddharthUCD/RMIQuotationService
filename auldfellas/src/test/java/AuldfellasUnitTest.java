import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.*;
import service.*;

import static org.junit.Assert.assertNotNull;

public class AuldfellasUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService afqService = new AFQService();
        try {
            //Initializing registry with a new registry
            registry = LocateRegistry.createRegistry(1099);

            //Exposing Auldfellas Service object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);

            //Binding Auldfellas object to registry
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    //Test for connection by looking up Auldfellas service on the registry
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.AULD_FELLAS_SERVICE);
        assertNotNull(service);
    }

    //Test for Auldfellas Quotation generation
    @Test
    public void QuotationGenerationTest() throws Exception {
        ClientInfo TestClientInfo = new ClientInfo();
        AFQService TestAFQService = new AFQService();

        Quotation TestQuotation = TestAFQService.generateQuotation(TestClientInfo);
        assertNotNull(TestQuotation);
    }
}