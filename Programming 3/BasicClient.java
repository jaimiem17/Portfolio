/*
 * Jaimie Morris
 * Client connects to server and uses a GUI to recieve names then sends them to the server
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BasicClient extends JFrame implements ActionListener{

	private JTextField ipAddress;
	private JTextField port;
	private JTextField playerName;
	private JButton connectButton;

	public static final String SERVER_IP = "10.104.116.27";
	public static final int SERVER_PORT = 4242;


	public BasicClient(){

		setSize(300,200);
		setTitle("Warmup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		getContentPane().setBackground(Color.white);

		JLabel ipLabel = new JLabel("IP Adress: ");
		ipLabel.setBounds(40,30,80,30);

		JLabel portLabel = new JLabel("Port #: ");
		portLabel.setBounds(40,55,80,30);

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(40,80,80,30);

		add(ipLabel);
		add(portLabel);
		add(nameLabel);

		ipAddress = new JTextField(20);
		ipAddress.setBounds(130,37,110,17);
		ipAddress.setText(SERVER_IP);

		port = new JTextField(20);
		port.setBounds(130,63,110,17);
		port.setText(SERVER_PORT+"");

		playerName = new JTextField(20);
		playerName.setBounds(130,88,110,17);

		add(ipAddress);
		add(port);
		add(playerName);

		connectButton = new JButton("Connect");
		connectButton.setBounds(90,120,100,15);
		connectButton.addActionListener(this);

		add(connectButton);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){

		if(ipAddress.getText().equals("")|| port.getText().equals("")||playerName.getText().equals("")){
			JOptionPane.showMessageDialog(null, "You must fill in all fields!","*Sigh*",JOptionPane.ERROR_MESSAGE);
		}
		else{

			//code here

			try {
				Socket sock = new Socket(SERVER_IP, SERVER_PORT);

				//allows us to send data to the server through the socket
				PrintWriter out = new PrintWriter(sock.getOutputStream());

				String message = playerName.getText();
				
				//flush forces the data across the stream
				out.println(message);
				out.flush();



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

	public static void main(String[] args){
		new BasicClient();
	}

}
