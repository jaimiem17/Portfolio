import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Jaimie Morris
 * This functions makes an encrypter and decrypter using GUI in Java
 */
public class JaimieMorris_Decoder extends JFrame implements Cryptable, ActionListener{

	private JButton encrypt;
	private JButton decrypt;
	private JButton reset;
	private JTextField jtf;
	private JTextField jtf2;
	PigLatin cryp=new PigLatin();

	//creates GUI pop-up w/ size, location, layout, background image, and adds the widgets
	public JaimieMorris_Decoder() {

		setTitle("Cryptography");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(340,140);
		setLocationRelativeTo(null);



		encrypt=new JButton("Encrypt");
		decrypt=new JButton("Decrypt");
		reset=new JButton("Reset");
		reset.setEnabled(false);
		jtf = new JTextField(20);
		jtf2 = new JTextField(20);


		JLabel jlab = new JLabel();
		jlab.setFont(new Font("Comic Sans", Font.PLAIN,18));


		JLabel display=new JLabel("Plaintext");
		JLabel display2=new JLabel("Ciphertext");


		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		reset.addActionListener(this);

		PicPanel mainPanel = new PicPanel("glitter.jpg");


		mainPanel.setLayout(new FlowLayout());

		mainPanel.add(display);
		mainPanel.add(jtf); 
		mainPanel.add(display2);
		mainPanel.add(jtf2); 
		mainPanel.add(jlab);
		mainPanel.add(encrypt);
		mainPanel.add(decrypt);
		mainPanel.add(reset);

		add(mainPanel);


		setVisible(true);
	}
	//once it recieves a click it tests which button was pressed then proceeds based on the following code
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("Encrypt")) {
			jtf2.setText(encrypt(jtf.getText()));
			jtf.setText("");
			reset.setEnabled(true);
		}
		else if(ae.getActionCommand().equals("Decrypt")) {
			jtf.setText(decrypt(jtf2.getText()));
			jtf2.setText("");
			reset.setEnabled(true);
		}
		else {
			jtf.setText("");
			jtf2.setText("");
			reset.setEnabled(false);
		}


	}
	//adds the photo as background throws exception if cannot find image
	public class PicPanel extends JPanel{
		private BufferedImage image;

		public PicPanel(String fname){

			try {
				image = ImageIO.read(new File(fname));
			} catch (IOException ioe) {
				System.out.println("Could not read in the pic");
				System.exit(0);
			}
		}

		//draws image!
		public void paintComponent(Graphics g){

			super.paintComponent(g);
			g.drawImage(image,0,0,this);

		}
	}
	public static void main(String[] args){

		JaimieMorris_Decoder sw=new JaimieMorris_Decoder();

	}//Encrypt and Decrypt functions call PigLatin class and send the text tp be altered then sends it back to the Action function
	public String encrypt(String text) {
		
		String ciphered= cryp.encrypt(text);
				return ciphered;
	}

	public String decrypt(String text) {
		String unciphered= cryp.decrypt(text);
		return unciphered;
	}
}
