import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class JMorris_PVServer {

	public static final int SERVER_PORT = 4242;

	public static final String VALID_PASSWORD = "OPENSESAME";
	private Map<String, Integer> addresses = new TreeMap<String, Integer>();
	private String ip;


	public JMorris_PVServer() {

		Socket theSock = null;

		try{

			System.out.println("Server: ");

			ServerSocket server = new ServerSocket(SERVER_PORT);
			System.out.println(server.getLocalPort());
			System.out.println(InetAddress.getLocalHost().getHostAddress());


			while(true) {

				//accepts socket from client and attempts to connect
				theSock = server.accept();

				//grabs IP and splits it to correct format
				ip = theSock.getRemoteSocketAddress().toString();
				String[] ipSplit = ip.split(":");
				ip = ipSplit[0];


				//if this ip address has never been used before adds it to the map
				if(addresses.get(ip)==null) {
					addresses.put(ip, 1);
				}

				//if ip has been used before it adds another try
				else {
					int newTry = addresses.remove(ip) +1;
					addresses.put(ip, newTry);
				}

				//creates threads and sends the message and amount of tries the current ip has done
				Requests threads = new Requests(theSock, addresses.get(ip));
				Thread newThread = new Thread(threads);

				newThread.start();

			}


		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}


	public class Requests implements Runnable {

		private Socket socketMessage;
		private int timesAccepted;


		//constructor accepts message and amount of times the client has already been accepted
		public Requests (Socket m, int ta) {
			socketMessage = m;
			timesAccepted= ta;

		}


		public void run() {

			//read from the client's socket
			Scanner in;
			boolean stillGuessing = true;

			try {

				//sends data through the socket
				PrintWriter out = new PrintWriter(socketMessage.getOutputStream());

				//begins to ask client for password if client has attempts left and continues based off of # of attempts left
				while(stillGuessing) {
					in = new Scanner(socketMessage.getInputStream());

					//if the server has already attempted too many times send back no and change boolean to false
					if(timesAccepted>=3) {
						out.println("NO");
						out.flush();
						stillGuessing = false;
					}	
					else {
						out.println("continue");
						out.flush();
					}


					String message = "";


					//recieves message
					do{
						if(in.hasNextLine())
							message = in.nextLine();


						//send the message to the server
						//flush forces the data across the stream
						if(!message.equals(VALID_PASSWORD)){

							if(timesAccepted>=3) {
								out.println("NO");
								out.flush();
							}

							//if incorrect sends incorrect message and then adds another try onto their # of tries
							else {
								out.println("thats incorrect! you have "+ (3-timesAccepted) + " more tries!");
								timesAccepted++;

								synchronized(addresses) {
									addresses.remove(ip);
									addresses.put(ip, timesAccepted);
								}


								stillGuessing = true;
							}
							out.flush();


						}
						else {
							out.println("ACCEPTED");
							out.flush();
							stillGuessing = false;
						}



					}while(in.hasNextLine());
				}


				//close the socket to signal to the server
				//that they don't have to listen anymore
				//for data from this client
				out.close();
				socketMessage.close();


			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public static void main(String[]args) {
		new JMorris_PVServer();
	}


}


