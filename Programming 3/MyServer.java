import java.net.*;
import java.util.*;
import java.io.*;

public class MyServer {

	public static void main(String[]args){

		try{
			System.out.println("Server: ");
			ServerSocket server = new ServerSocket(4242);
			System.out.println(server.getLocalPort());
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			//accept a client socket that attempts to connect
			Socket theSock = server.accept();

			//read from the client's socket
			Scanner in = new Scanner(theSock.getInputStream());

			//as long as the client's socket is open
			//keep trying to read
			while(in.hasNextLine()){
				String message = in.nextLine();
				System.out.println("Received: "+message);

				if(message.equals("end"))
					return;				
			}

			in.close();
			theSock.close();

		}catch(IOException e){
			e.printStackTrace();

		}
	}

}
