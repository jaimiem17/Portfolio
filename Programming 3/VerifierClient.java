
import java.net.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//Client GUI to attempt password connections

public class VerifierClient extends JFrame implements ActionListener {

	private JTextField ipAddress;
	private JTextField port;
	private JButton connectButton;

	private Socket theSock;
	private PrintWriter outgoing;
	private Scanner incoming;

	private JLabel ipLabel;
	private JLabel portLabel;
	
	public static final String SERVER_IP = "10.120.1.205";
	public static final int SERVER_PORT = 4242;

	public VerifierClient(){

		setSize(300,200);
		setTitle("Password Verifier");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(null);
		getContentPane().setBackground(Color.white);

		ipLabel = new JLabel("IP Adress: ");
		ipLabel.setBounds(40,30,80,30);

		portLabel = new JLabel("Port #: ");
		portLabel.setBounds(40,55,80,30);

		add(ipLabel);
		add(portLabel);

		ipAddress = new JTextField(20);
		ipAddress.setBounds(130,37,110,17);
		ipAddress.setText(SERVER_IP);

		port = new JTextField(20);
		port.setBounds(130,63,110,17);
		port.setText(SERVER_PORT+"");
		
		add(ipAddress);
		add(port);

		connectButton = new JButton("Connect");
		connectButton.setBounds(40,120,100,15);
		connectButton.addActionListener(this);

		add(connectButton);
		
		JButton logOff = new JButton("Log Off");
		logOff.addActionListener(new LogOffButtonListener());
		logOff.setBounds(160,120,100,15);
		add(logOff);
		
		this.getRootPane().setDefaultButton(connectButton);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){

		if(ae.getActionCommand().equals("Connect")){
			if(ipAddress.getText().equals("")|| port.getText().equals("")){
				JOptionPane.showMessageDialog(null, "You must fill in all fields!","*Sigh*",JOptionPane.ERROR_MESSAGE);
			}
			else{

				//connect out to the server!
				try{


					theSock = new Socket(ipAddress.getText(),Integer.parseInt(port.getText()));
					incoming = new Scanner(theSock.getInputStream());
					outgoing = new PrintWriter(theSock.getOutputStream());

					//if the client is immediately rejected
					//because they were locked out on a previous occassion
					String message = incoming.nextLine();
					if(message.equals("NO")){
						JOptionPane.showMessageDialog(null, "This computer is blocked from using that server");
						connectButton.setEnabled(false);
						this.repaint();
						return;
					}

					//Otherwise reconfigure the GUI to now allow
					//the user to type in a password
					this.remove(ipAddress);
					connectButton.setText("Send");

					portLabel.setText("Password: ");
					ipLabel.setBounds(40,30,250,30);
					this.add(portLabel);
					ipLabel.setText("Enter Password: ");
					port.setText("");
					port.requestFocus();
					this.repaint();

				}catch(IOException e){
					e.printStackTrace();
				}
			}
			this.getRootPane().setDefaultButton(connectButton);
		}
		//When the user attempts to send a password across
		else if(ae.getActionCommand().equals("Send")){

			//Send the potential password

			outgoing.println(port.getText());
			outgoing.flush();
			port.setText("");
			port.requestFocus();

		//	try{
				String message = incoming.nextLine();
				//If the server has rejected the user 3 times
				if(message.equals("NO")){
					JOptionPane.showMessageDialog(null, "You have failed too many times");
					connectButton.setEnabled(false);
					this.repaint();
					return;
				}
			    

				//Otherwise show the result
				JOptionPane.showMessageDialog(null, message);
				
				if(message.contains("ACCEPTED")){
					connectButton.setEnabled(false);
					this.repaint();
					return;
				}

			//}catch(IOException e){
			//	e.printStackTrace();
			//}
		}
	}
	
	public class LogOffButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent ev){

			try{
				if(incoming !=null)
					incoming.close();
				if(outgoing !=null)
					outgoing.close();
				
				if(theSock !=null)
					theSock.close();				

			}catch(IOException e){
				e.printStackTrace();
			}

			System.exit(0);
		}
	}

	public static void main(String[] args){


		new VerifierClient();
	}

}




