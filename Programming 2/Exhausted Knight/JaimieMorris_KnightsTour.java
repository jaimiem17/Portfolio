import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class JaimieMorris_KnightsTour extends JFrame implements ActionListener{

	private enum STATUS  {BLANK,KNIGHT,NUMBER};

	private BufferedImage image;

	private PicPanel[][] allPanels;
	private JLabel stepLabel;		
	private JButton stepButton;

	public  final int[] HORZDISP = {1,2,2,1,-1,-2,-2,-1};
	public  final int[] VERTDISP = {-2,-1,1,2,2,1,-1,-2};

	private int stepNum = 1;
	private int knightRow = 0;
	private int knightCol = 0;

	public JaimieMorris_KnightsTour(){

		try {
			image = ImageIO.read(new File("knight.jpg"));

		} catch (IOException ioe) {
			System.out.println("Could not read in the pic");
			System.exit(0);
		}		

		setSize(1000,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Knight's Tour");
		
		getContentPane().setBackground(new Color(42,122,250));

		JPanel gridPanel = new JPanel();
		gridPanel.setBackground(Color.black);
		gridPanel.setLayout(new GridLayout(8,8,2,2));
		gridPanel.setBounds(50,50,600,700);
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		stepLabel=new JLabel("Total Steps: "+ stepNum);
		stepLabel.setFont(new Font("Calibri", Font.PLAIN, 32));
		stepLabel.setBounds(720,300,500,40);

		stepButton=new JButton("Step");
		stepButton.setBounds(760,400,100,40);

		allPanels = new PicPanel[8][8];

		for(int row = 0; row < 8; row++){
			for( int col = 0; col < 8; col++){

				allPanels[row][col]=new PicPanel(76,100);
				gridPanel.add(allPanels[row][col]);

			}
		}

		allPanels[0][0].setKnight();
		stepButton.addActionListener(this);

		add(stepButton);
		add(stepLabel);
		add(gridPanel);

		setVisible(true);
	}

	//will maintain either a knight or a number
	class PicPanel extends JPanel{

		private int width,height;

		private STATUS stat;

		public PicPanel(int w, int h){
			setBackground(Color.white);
			width = w;
			height = h;
			stat = STATUS.BLANK;
			setLayout(null);


		}

		//changes the panel to show the knight pic
		public void setKnight(){

			stat = STATUS.KNIGHT;
			repaint();
		}


		//changes the panel to show the stepnum in the pic
		public void setNumber() {

			JLabel text = new JLabel(""+stepNum,SwingConstants.CENTER);
			text.setFont(new Font("Calibri",Font.PLAIN,55));
			text.setBounds(0,35,70,50);
			this.add(text);
			stat = STATUS.NUMBER;
			repaint();
		}

		//called by the machine
		public Dimension getPreferredSize() {
			return new Dimension(width,height);
		}

		//called automatically by repaint
		public void paintComponent(Graphics g){

			super.paintComponent(g);

			if(stat == STATUS.KNIGHT)
				g.drawImage(image,0,0,this);
		}
	}

	public static void main(String[] args){
		new JaimieMorris_KnightsTour();
	}


	//this function comes when step is pressed. It starts with the process of finding a series of available moves then doing a random one
	public void actionPerformed(ActionEvent ae) {
		int pastRow=knightRow;
		int pastCol=knightCol;

		if(ae.getActionCommand().equals("Step")) {

			stepLabel.setText("Total Steps: "+ (stepNum+1));

			int[]moves=determineMoves();

			int place=doMove(moves);
			knightRow+=VERTDISP[moves[place]];
			knightCol+=HORZDISP[moves[place]];

		}

		allPanels[pastRow][pastCol].setNumber();
		stepNum++;

		if(determineMoves().length==0) {
			stepButton.setEnabled(false);
		}

	}

	// This function examines all 8 different possible moves and returns an array of the move numbers which are valid. Move numbers correlate to the indices of the horizontal and vertical offset arrays.
	private int[] determineMoves() {

		int nextCol;
		int ctr=0;
		int nextRow;
		boolean valid=false;
		boolean beenThere = false;
		int[]moves1=new int[VERTDISP.length];

		for(int i = 0; i < VERTDISP.length; i++){

			nextRow = knightRow + VERTDISP[i];
			nextCol = knightCol + HORZDISP[i];

			valid=inBounds(nextRow, nextCol);

			if (valid==true)//if its not out of bounds THEN check if been there before
				beenThere=spotTaken(nextRow, nextCol);

			if (valid==true&&beenThere==false) {
				moves1[ctr]=i;
				ctr++;
			}

		}

		int avail=0;
		int ctr2=0;

		for(int i=0;i<moves1.length;i++) {

			if(moves1[i]!=0) 
				avail++;

		}
		//the array is equal to 8 spots but this gets rid of all the spots with 0 and creates a new array without them
		int[]moves=new int[avail];

		if(moves.length>0) {

			for(int i=0;i<moves.length;i++) {

				if(moves1[i]!=0) 
					moves[ctr2]=moves1[i];

				ctr2++;
			}
		}

		return moves;
	}

	//checks if the move is in bounds
	private boolean inBounds(int row, int col) {

		if (row<0||col<0||row>=allPanels.length||col>=allPanels[0].length) {
			return false;
		}
		return true;
	}
	
	//checks if the spot has already been used or if it is available
	private boolean spotTaken(int row, int col) {

		if(allPanels[row][col].stat==STATUS.BLANK)
			return false;

		return true;
	}

	//chooses a random move from available lists then the knight moves there
	private int doMove(int[] moves){

		int len=moves.length-1;
		int move=(int)(Math.random()*len);//randomly picks a spot from the available moves

		allPanels[knightRow+(VERTDISP[moves[move]])][knightCol+(HORZDISP[moves[move]])].setKnight();

		return move;
	}

}



