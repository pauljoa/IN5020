package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import TasteProfile.Profiler;
import TasteProfile.ProfilerHelper;
import TasteProfile._ProfilerStub;

public class Client extends _ProfilerStub {

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Profiler";
			Profiler profilerRef = ProfilerHelper.narrow(ncRef.resolve_str(name));
			
			String fileLocation = args[2];
			File file = new File(fileLocation);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String st;
			while((st = br.readLine()) != null) {
				System.out.println(st);
				String[] splittedInput = st.split("\\t");
				String method = splittedInput[0];
				
				if(method.equals("getTimesPlayedByUser")) {
					profilerRef.getTimesPlayedByUser(splittedInput[1], splittedInput[2]);
				} else if(method.equals("getTimesPlayed")) {
					profilerRef.getTimesPlayed(splittedInput[1]);
				} else if(method.equals("getTopThreeUsersBySong")) {
					profilerRef.getTopThreeUsersBySong(splittedInput[1]);
				} else {
					throw new Error("Illegal method name in input");
				}
			}
		} catch (Exception e) {
			System.out.println("Client Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

}
