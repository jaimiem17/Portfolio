package GroupPaint;

//Eli Bull and Jaimie Morris
//3/28/22
//accepts all connections from clients
//reads in messages from clients and broadcasts those messages to all other clients

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;


public class PaintServer {
	private ArrayList<ClientHandler> allClients; // used to broadcast messages to all connected clients

	// creates the serverSocket on port 4242.
	// continously attempts to listen for new clients
	// builds a clientHandler thread off the socket and starts that thread.
	// this constructor never ends.

	public PaintServer() {
		allClients = new ArrayList<ClientHandler>();
		try {
			ServerSocket server = new ServerSocket(4242);
			System.out.println(server.getLocalPort());
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			while (true) {
				Socket sock = server.accept();

				new Thread(new ClientHandler(sock)).start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// writes the message to every socket in the ArrayList instance variable.
	public void tellEveryone(String message) {
		
	}

	// interacts with a specific client
	public class ClientHandler implements Runnable {

		private Scanner reader;
		private Socket sock;
		private PrintWriter theWriter;
		private String name; // client's name
		private Color color; // client color draws with

		// initializes all instance variables
		public ClientHandler(Socket clientSocket) {

			sock = clientSocket;
			try {

				reader = new Scanner(sock.getInputStream());
				theWriter = new PrintWriter(sock.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			}
			name = "placeholder";
			color = new Color(0, 0, 0);
			while(reader.hasNextLine()) {
				String sentIn = reader.nextLine();
				tellEveryone(sentIn);
				
				if(sentIn.indexOf("joined") != -1) {
					String [] nameCol = sentIn.split(":");
					name = nameCol[1];
				}
				
				
			}
		}

		public String toString() {
			return name + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
		}

		public boolean equals(Object o) {
			ClientHandler other = (ClientHandler) o;
			return name.equals(other.name);
		}

		// continuously checks to see if there is an available message from the client
		// if so broadcasts received message to all other clients
		// via the outer helper method tellEveryone.
		public void run() {

	
			closeConnections();
		}

		private void closeConnections() {

			try {

				synchronized (allClients) {

					reader.close();
					theWriter.close();
					sock.close();
					allClients.remove(this);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new PaintServer();
	}
}
