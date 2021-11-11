import org.junit.BeforeClass;
import service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;
import org.junit.*;

public class GirlpowerUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService gpqService = new GPQService();
        try {
            //Create a test registry
            registry = LocateRegistry.createRegistry(1099);

            //Exposing Quotation Service object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gpqService,0);

            //Binding Girlpower object to registry
            registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    //Test for connection by looking up Girlpower service on the registry
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.GIRL_POWER_SERVICE);
        assertNotNull(service);
    }

    //Test for Girlpower Quotation generation
    @Test
    public void QuotationGenerationTest() throws Exception {
        ClientInfo TestClientInfo = new ClientInfo();
        GPQService TestGPQService = new GPQService();
        Quotation TestQuotation = TestGPQService.generateQuotation(TestClientInfo);
        assertNotNull(TestQuotation);
    }
}