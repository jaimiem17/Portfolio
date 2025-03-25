/*
 * Jaimie Morris
 * This program practices GUI layout by making a non-functional calculator
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class JaimieMorris_Calculator extends JFrame {

	public JaimieMorris_Calculator(){

		
		setTitle("Calculator");
		setSize(330,410);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		//makes the main panel the background image and sets the bounds of the panel to be the size and location of the pop-up
		PicPanel mainPanel = new PicPanel("math.jpg");
		mainPanel.setBounds(0,0,330,410);

		mainPanel.setLayout(null);
		
		//creates the box that would hold numbers and sets its location and size
		JTextField numberBox = new JTextField(20);
		numberBox.setBounds(60,50,200,30);

		//creates the grid that holds buttons and sets the layout and then its bounds (its location and size)
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(4,4,5,5));
		gridPanel.setOpaque(false);
		
		gridPanel.setBounds(50,130,220,220);
		
		
		
		//adds buttons to each panel in the grid
		int ctr=0;
		String calcButtons="789/456*123+.0=-";
		for(int row = 0; row < 4; row++){
			for( int col = 0; col < 4; col++){

				gridPanel.add(new JButton(calcButtons.substring(ctr,ctr+1)+""));
				ctr++;
			}
		}

		
		//adds the text field that would hold the numbers and then adds the grid with the buttons
		mainPanel.add(numberBox);
		mainPanel.add(gridPanel);
		add(mainPanel);
		
		
		
		//presents pop-up
		setVisible(true);
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
		JaimieMorris_Calculator calc=new JaimieMorris_Calculator();
	}



}

