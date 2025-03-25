/*
 * Jaimie Morris
 * Server created to connect to the client- Recieves two names and prints them
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class JMorris_BasicServer {

	public static final int NUMBER_OF_CLIENTS = 2;
	
	public JMorris_BasicServer() {
		
		try{
			System.out.println("Server: ");
			ServerSocket server = new ServerSocket(4242);
			System.out.println(server.getLocalPort());
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			String[] playerNames = new String[NUMBER_OF_CLIENTS];
			
			//accept a client socket that attempts to connect
			Socket theSock;
			
			// Loop through for each connection and capture each message in an array 
			for (int i=0; i <NUMBER_OF_CLIENTS; i++) {
				theSock = server.accept();
			
				//read from the client's socket
				Scanner in = new Scanner(theSock.getInputStream());

				//as long as the client's socket is open
				//keep trying to read
				while(in.hasNextLine()){
					String message = in.nextLine();
					playerNames[i] = message;
				}

				in.close();
				theSock.close();
			}
			
			// Print Messages
			for (int i=0; i<playerNames.length; i++) {
				System.out.println(playerNames[i]);
			}

		}catch(IOException e){
			e.printStackTrace();

		}
	}
	
	public static void main(String[]args) {
		new JMorris_BasicServer();
	}


}
