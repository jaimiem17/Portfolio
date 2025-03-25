import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	public static final String SERVER_IP = "";
	public static final int SERVER_PORT = 4242;

	public static void main(String[] args){

		Scanner keyboard = new Scanner(System.in);


		//Creates a socket for the client
		//that will connect to the server
		try {
			Socket sock = new Socket(SERVER_IP, SERVER_PORT);

			//allows us to send data to the server through the socket
			PrintWriter out = new PrintWriter(sock.getOutputStream());

			String message;
			do{
				System.out.println("Enter a message:(end to quit) ");
				message = keyboard.nextLine();

				//send the message to the server
				//flush forces the data across the stream
				if(!message.equals("end")){
					out.println(message+"");
					out.flush();
				}

			}while(!message.equals("end"));

			//close the socket to signal to the server
			//that they don't have to listen anymore
			//for data from this client
			out.close();
			sock.close();
		}catch(IOException e){
			e.printStackTrace();
		}





	}
}
