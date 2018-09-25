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
	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			Servant profilerImpl = new Servant(args[2]);
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(profilerImpl);
			Profiler pref = ProfilerHelper.narrow(ref);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Profiler";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, pref);
			orb.run();
		} catch (Exception e) {
			System.err.println("Server Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

}
