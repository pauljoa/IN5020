package Server;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import TasteProfile.Profiler;
import TasteProfile.ProfilerHelper;

public class Server {

	public Server() {
		// TODO Auto-generated constructor stub
		
	}
	//USAGE: args -ORBInitialPort (Port) <Full path to input file (the "database file")> 
	//<Integer value to decide if cache is to be used on server or not (0 = cache off. 1 = cache on)>
	public static void main(String args[]) {
		try {
			
			//Initiating ORB
			ORB orb = ORB.init(args, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			//Initiating servant with/without Cache
			boolean useCache = Integer.parseInt(args[3]) != 0;
			Servant profilerImpl = new Servant(args[2], useCache);
			
			//Setting up servant references
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(profilerImpl);
			Profiler pref = ProfilerHelper.narrow(ref);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Profiler";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, pref);
			
			//ORB Start
			orb.run();
		} catch (Exception e) {
			System.err.println("Server Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

}
