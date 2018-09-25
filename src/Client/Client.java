package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import TasteProfile.Profiler;
import TasteProfile.ProfilerHelper;
import TasteProfile.TopThree;
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
					int tpbu = profilerRef.getTimesPlayedByUser(splittedInput[1], splittedInput[2]);
					System.out.println("Times played: " + tpbu);
				} else if(method.equals("getTimesPlayed")) {
					int tp = profilerRef.getTimesPlayed(splittedInput[1]);
					System.out.println("Times played: " + tp);
				} else if(method.equals("getTopThreeUsersBySong")) {
					TopThree tt = profilerRef.getTopThreeUsersBySong(splittedInput[1]);
					System.out.println(tt.topThreeUsers[0].user_id + " - " + tt.topThreeUsers[0].songid_play_time);
					System.out.println(tt.topThreeUsers[1].user_id + " - " + tt.topThreeUsers[1].songid_play_time);
					System.out.println(tt.topThreeUsers[2].user_id + " - " + tt.topThreeUsers[2].songid_play_time);
				} else {
					throw new Error("Illegal method name in input");
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Client Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

}
