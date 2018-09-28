package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import Cache.UserCache;
import Implementations.UserProfileImpl;
import TasteProfile.Profiler;
import TasteProfile.ProfilerHelper;
import TasteProfile.Song;
import TasteProfile.TopThree;
import TasteProfile._ProfilerStub;

public class Client extends _ProfilerStub {

	public Client() {
		// TODO Auto-generated constructor stub
	}

	// USAGE: args "-ORBInitialPort (Port) <Full path to input file> <Full path to
	// output file (including name of file with extension)>
	// <Integer value to decide if cache is to be used on client or not (0 = cache
	// off. 1 = cache on)>"
	public static void main(String[] args) {
		try {

			// Initiating ORB
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Profiler";
			Profiler profilerRef = ProfilerHelper.narrow(ncRef.resolve_str(name));

			// Initiating Cache
			boolean useCache = Integer.parseInt(args[4]) != 0;
			UserCache uCache = new UserCache();

			// Initiating IO
			String fileLocation = args[2];
			File file = new File(fileLocation);
			BufferedReader br = new BufferedReader(new FileReader(file));

			String outputLocation = args[3];
			PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputLocation, true)));

			long startTime = System.nanoTime();
			String st;

			// Main read loop
			while ((st = br.readLine()) != null) {

				String[] splittedInput = st.split("\\t");
				String method = splittedInput[0];

				if (method.equals("getTimesPlayedByUser")) {
					int tpbu = 0;
					long localTime = System.nanoTime();
					if (useCache) {
						String uId = splittedInput[1];
						UserProfileImpl user = uCache.Get(uId);
						if (user == null) {
							user = (UserProfileImpl) profilerRef.getUserProfile(uId);
							Thread.sleep(80);
							uCache.Put(uId, user);
						}

						Song[] sArr = user.songs;
						for (int i = 0; i < sArr.length; i++) {
							Song s = sArr[i];
							if (s.id.equals(splittedInput[2])) {
								tpbu = s.play_count;
								break;
							}
						}
					} else {
						tpbu = profilerRef.getTimesPlayedByUser(splittedInput[1], splittedInput[2]);
						Thread.sleep(80);
					}
					long elapsedTime = System.nanoTime() - localTime;
					Math.floor(elapsedTime);
					double elapsedMs = elapsedTime / 1000000.0;
					System.out.println("Song " + splittedInput[2] + " played " + tpbu + " times by user "
							+ splittedInput[1] + ". (" + (int) Math.ceil(elapsedMs) + " ms)");
					outputWriter.println("Song " + splittedInput[2] + " played " + tpbu + " times by user "
							+ splittedInput[1] + ". (" + (int) Math.ceil(elapsedMs) + " ms)");
				} else if (method.equals("getTimesPlayed")) {
					long localTime = System.nanoTime();
					int tp = profilerRef.getTimesPlayed(splittedInput[1]);
					Thread.sleep(80);
					long elapsedTime = System.nanoTime() - localTime;
					double elapsedMs = elapsedTime / 1000000.0;
					System.out.println("Song " + splittedInput[1] + " played " + tp + " times. ("
							+ (int) Math.ceil(elapsedMs) + " ms)");
					outputWriter.println("Song " + splittedInput[1] + " played " + tp + " times. ("
							+ (int) Math.ceil(elapsedMs) + " ms)");
				} else if (method.equals("getTopThreeUsersBySong")) {
					long localTime = System.nanoTime();
					TopThree tt = profilerRef.getTopThreeUsersBySong(splittedInput[1]);
					Thread.sleep(80);
					long elapsedTime = System.nanoTime() - localTime;
					double elapsedMs = elapsedTime / 1000000.0;
					if (tt.topThreeUsers[0] == null)
						System.out.println("tt = null");
					System.out.println("Top three users that played this song the most: "+splittedInput[1]);
					System.out.println(
							tt.topThreeUsers[0].user_id + " - " + tt.topThreeUsers[0].songid_play_time + " times.");
					System.out.println(
							tt.topThreeUsers[1].user_id + " - " + tt.topThreeUsers[1].songid_play_time + " times.");
					System.out.println(
							tt.topThreeUsers[2].user_id + " - " + tt.topThreeUsers[2].songid_play_time + " times.");
					System.out.println("Elapsed time: " + (int) Math.ceil(elapsedMs) + " ms");
					outputWriter.println(splittedInput[1]);
					outputWriter.println(
							tt.topThreeUsers[0].user_id + " - " + tt.topThreeUsers[0].songid_play_time + " times.");
					outputWriter.println(
							tt.topThreeUsers[1].user_id + " - " + tt.topThreeUsers[1].songid_play_time + " times.");
					outputWriter.println(
							tt.topThreeUsers[2].user_id + " - " + tt.topThreeUsers[2].songid_play_time + " times.");
					outputWriter.println("Elapsed time: " + (int) Math.ceil(elapsedMs) + " ms");
				} else {
					br.close();
					outputWriter.close();
					throw new Error("Illegal method name in input");
				}
			}
			long elapsedTime = System.nanoTime() - startTime;
			double elapsedMs = elapsedTime / 1000000.0;
			System.out.println("Total elapsed time: " + elapsedMs + " ms");
			outputWriter.println("Total elapsed time: " + elapsedMs + " ms");
			br.close();
			outputWriter.close();
		} catch (Exception e) {
			System.out.println("Client Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}
}
