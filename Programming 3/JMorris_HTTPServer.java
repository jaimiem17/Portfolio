/*
 * Jaimie Morris
 * 
 */
import java.io.*;
import java.net.*;
import java.util.*;

import JaimieMorris_Admissions.College;
import JaimieMorris_Admissions.Prospect;
//This server is supposed to interact with web browsers
public class JMorris_HTTPServer {

	public JMorris_HTTPServer(){


		ServerSocket serverSocket=null;

		//create a new server socket like usual
		try{

			serverSocket = new ServerSocket(0);

		}catch(IOException e){
			System.out.println("server not set up properly");
			System.exit(-1);
		}

		try{	
			System.out.println("The address "+InetAddress.getLocalHost().getHostAddress());
			System.out.println("Port: "+ serverSocket.getLocalPort());
		}catch(IOException e){
			System.out.println("Error retrieving host name");
			System.exit(-1);
		}

		while(true){
			Socket clientSock = null;
			try{
				clientSock = serverSocket.accept();
			}catch(IOException e){
				System.out.println("accept issue");
				System.exit(-1);
			}

			Thread clientThread = new Thread(new ClientHandler(clientSock));
			clientThread.start();
		}
	}

	public class ClientHandler implements Runnable{

		private Socket sock;
		private Scanner clientIn;
		private PrintWriter clientOut;
		private DataOutputStream out;

		public ClientHandler(Socket theSock){

			sock = theSock;
			try{
				clientIn = new Scanner(theSock.getInputStream());
				clientOut = new PrintWriter(theSock.getOutputStream());
				out = new DataOutputStream(sock.getOutputStream());
			}catch(IOException e){
				System.out.println("Error retrieving input/output socket streams");
				System.exit(-1);
			}
		}

		//run method
		public void run(){

			boolean error = false;
			String connect="close";
			int conLen=0;
			String conTyp="";
			String finalLine="bytes";

			//reads all lines in from the client browser
			//the clients last line is always empty
			String line;

			line =clientIn.nextLine();


			String split[] = line.split(" ");

			//if not split or head as header
			if(!split[0].contains("HEAD")||!split[0].contains("POST")) {

				//check for error
				if(!split[0].contains("GET")) {
					
					//error 400
					error=true;
				}

				//if it contains get
				else {
					byte[] bytes = new byte[1024];
					FileInputStream in = null;


					//Load file
					try {
						in = new FileInputStream(new File(split[1].substring(1)));
					}catch(FileNotFoundException e) {

						//error 404
						error = true;

					}
					
					//if error then sends error message to client
					if(error) {

						finalLine = "<html><body> File Not Found! </body></html>";
						conLen = finalLine.length();
						conTyp = type(split[1]);
						line = "HTTP/1.1 404 Not Found";

						sendMessage(connect, conLen, conTyp, line);

						System.out.println(finalLine);
						clientOut.println(finalLine);
						clientOut.flush();
					}
					
					//else sends correct outcome
					else {

						line = "HTTP/1.1 200 OK";
						connect = "close";
						conLen = bytes.length;
						conTyp = type(split[1]);
						
						try {
							conLen =in.available();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						sendMessage(connect, conLen, conTyp, line);
						
						//now send bytes	
						try {
							int b = in.read(bytes);

							while(b != -1) {
								
								out.write(bytes, 0, b);
								// Reads next byte from the file
								b = in.read(bytes);
								
							}
							
							out.flush();
							
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				}

			}

			clientIn.close();
			clientOut.close();

			try {
				sock.close();
			} catch (IOException e) {
				System.out.println("Error closing socket");
				System.exit(-1);
			}
		}

		//private function that sends the info
		private void sendMessage(String con, int cLen, String type, String line) {

			String message = line + "\nConnection: " + con
					+ "\nContent-Length: "+ cLen
					+ "\nContent-Type: "+ type
					+"\n";

			clientOut.println(message);
			clientOut.flush();


			clientOut.println(" ");
			clientOut.flush();
		}

		//private function that finds the content type from what was sent
		private String type(String t) {

			if(t.contains("jpg")) 
				return "image/jpeg";

			return "text/html";

		}


	}

	public static void main(String[] args){

		new JMorris_HTTPServer();

	}
}
