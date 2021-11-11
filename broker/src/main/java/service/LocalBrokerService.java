package service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the broker service that uses the RMI Registry.
 * 
 * @author Rem
 * @modified Siddharth
 *
 */
public class LocalBrokerService implements BrokerService, QuotationService {
	//Method to retrieve quotation from the services
	public List<Quotation> getQuotations(ClientInfo info) {
		try {
			//Declaring & Initializing Registry and quotation's list
			List<Quotation> quotations = new LinkedList<Quotation>();
			Registry registry = LocateRegistry.getRegistry();

			//Iterate through registered services
			for (String name : registry.list()) {
				//Check for service's naming conventions
				if (name.startsWith("qs-")) {
					try {
						//Retrieve Quotations from each active service
						QuotationService service = (QuotationService) registry.lookup(name);
						quotations.add(service.generateQuotation(info));
					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				}
			}
			return quotations;
		}

		catch (RemoteException e)
		{
			e.printStackTrace();
			return new LinkedList<Quotation>();
		}

		catch (Exception e)
		{
			e.printStackTrace();
			return new LinkedList<Quotation>();
		}
	}

	@Override
	public Quotation generateQuotation(ClientInfo info) throws RemoteException {
		return null;
	}
}