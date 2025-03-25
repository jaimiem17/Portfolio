/*
 * Jaimie Morris
 * This program makes a game of totoro using the arrrow keys and KeyListener with GUI
 */
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class JaimieMorrris_TotoroSlider extends JFrame implements KeyListener{

	private BufferedImage image;

	private PicPanel[][] allPanels;

	private int totRow;		// location of Totoro
	private int totCol;

	public JaimieMorrris_TotoroSlider(){

		setSize(375,375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tile Slider");

		getContentPane().setBackground(Color.black);

		allPanels = new PicPanel[4][4];

		setBackground(Color.black);

		JPanel gridPanel = new JPanel();
		gridPanel.setBackground(Color.black);
		gridPanel.setLayout(new GridLayout(4,4,2,2));
		gridPanel.setBounds(0,0,375,375);
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		this.addKeyListener(this);

		try {
			image = ImageIO.read(new File("totoro.jpg"));


		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Could not read in the pic");
			System.exit(0);
		}	

		//this array is to check and see which numbers have already been added to the board so that there are no repeats.
		boolean numsUsed[]=new boolean[16];

		//creates a new picPanel for each box then adds numbers to all except Totoro when 0 is found
		
		for(int row = 0; row < 4; row++){
			for( int col = 0; col < 4; col++){
				allPanels[row][col]=new PicPanel();

				int r=(int)(Math.random()*16);
				
				if(r==0&&numsUsed[0]!=true) {

					gridPanel.add(allPanels[row][col]);
					numsUsed[0]=true;
					totRow=row;
					totCol=col;

				}
				
				else if (numsUsed[r]==true){

					while(numsUsed[r]==true||r==0) {
						r=(int)(Math.random()*16);
					}
					
					allPanels[row][col].setNumber(r);
					numsUsed[r]=true;
				}
				
				else {

					allPanels[row][col].setNumber(r);
					numsUsed[r]=true;
				}
				
				gridPanel.add(allPanels[row][col]);
				
			}
		}

		add(gridPanel);
		setVisible(true);
	}



	class PicPanel extends JPanel{


		private int width = 76;
		private int height = 80;	//dimensions of the Panel 

		private int number=-1;		// -1 when Totoro is at that position.
		private JLabel text;

		public PicPanel(){

			setBackground(Color.white);
			setLayout(null);

		}		

		//changes the panel to have the given number
		public void setNumber(int num){	

			number = num;
			text = new JLabel(""+number,SwingConstants.CENTER);
			text.setFont(new Font("Calibri",Font.PLAIN,55));
			text.setBounds(0,35,70,50);
			this.add(text);

			repaint();
		}

		//replaces the number with Totoro
		public void removeNumber(){
			this.remove(text);
			number = -1;
			repaint();
		
		}

		public Dimension getPreferredSize() {
			return new Dimension(width,height);
		}

		//this will draw the image or the number
		// called by repaint and when the panel is initially drawn
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			if(number == -1)
				g.drawImage(image,8,0,this);
		}

	}

	//Is called everytime a key is pressed
	public void keyPressed(KeyEvent e) {

		int keyVal= e.getKeyCode();

		checkBounds(keyVal);
		hasWon();

	}
	
	// checks which button was pressed and first checks if it is able to be used and then it swaps toto and the num
	private void checkBounds(int keyVal) {

		if(keyVal == KeyEvent.VK_LEFT) {

			if(totCol != 0) {

				allPanels[totRow][totCol].setNumber(allPanels[totRow][totCol-1].number);
				allPanels[totRow][totCol-1].removeNumber();
				totCol--;
			}
		}

		else if(keyVal == KeyEvent.VK_RIGHT) {
			if(totCol != 3) {

				allPanels[totRow][totCol].setNumber(allPanels[totRow][totCol+1].number);
				allPanels[totRow][totCol+1].removeNumber();
				totCol++;
			}
		}

		else if(keyVal == KeyEvent.VK_UP) {
			if(totRow != 0) {

				allPanels[totRow][totCol].setNumber(allPanels[totRow-1][totCol].number);
				allPanels[totRow-1][totCol].removeNumber();
				totRow--;
			}
		}

		else {
			if(totRow != 3) {

				allPanels[totRow][totCol].setNumber(allPanels[totRow+1][totCol].number);
				allPanels[totRow+1][totCol].removeNumber();
				totRow++;
			}
		}

	}
	
	//goes thru a loop and checks if all of the nums are in the correct order
	private boolean hasWon() {

		boolean win=true;
		int ctr=1;
		for(int row = 0; row < 4; row++){
			for( int col = 0; col < 3; col++){
				if(allPanels[row][col].number!=ctr)
					return false;
				ctr++;
			}
		}

		if(win==true) {
			JOptionPane.showMessageDialog(null, "You Won!");
			return true;
		}

		return false;

	}

	public static void main(String[] args){
		new JaimieMorrris_TotoroSlider();
	}


	//would not allow me to remove these so I just left them here as @Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}




}
