import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;

public class JaimieMorris_NimTower extends JFrame{

	private BufferedImage stoneCheckedImage;
	private BufferedImage stoneUnCheckedImage;

	private enum STATUS  {EMPTY,CHECKED,UNCHECKED};

	private ArrayList<PicPanel> selected;					
	private String[] playerNames;						
	private int turn;							
	private int stonesLeft = 16;

	private final int BOARD_DIM = 7;

	public JaimieMorris_NimTower(){


		//grabs the photos and displays error if not found
		try {
			stoneCheckedImage = ImageIO.read(new File("stone_checked.jpg"));
			stoneUnCheckedImage = ImageIO.read(new File("stone_unchecked.jpg"));
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Could not read in the pic");
			System.exit(0);
		}

		selected = new ArrayList<PicPanel>();
		playerNames = new String[2];

		setSize(715,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.black);

	
		setLayout(new GridLayout(BOARD_DIM ,BOARD_DIM ,2,2));

		PicPanel[][] allPanels = new PicPanel[BOARD_DIM ][BOARD_DIM ];
		for(int row = 0; row < allPanels.length; row++){
			for(int col = 0; col < allPanels[0].length; col++){
				allPanels[row][col] = new PicPanel(row,col);
				add(allPanels[row][col]);
			}
		}
	
		int dim=BOARD_DIM;
		int col=0;
		
		//adds stones to the grid
		for (int r=4;r>0;r--) {
			for(int c=col;c<dim;c++){
				allPanels[r][c].addStone();
			}
			col++;
			dim--;
		}

		//prompts the users for their name and randomly determines who goes first then sets the title to their name

		for(int i =0;i<2;i++) {
			playerNames[i] = JOptionPane.showInputDialog("Enter player " + (i+1) + " name: ");
		}
		int turn=(int)(Math.random()*2);
		setTitle("Nim - "+ playerNames[turn]+ " turn");



		setVisible(true);
	}




	//sorts the panels in the ArrayList instance var
	//by rows (same rows are then sorted by col)
	//based off the compareTo method in the PicPanel class
	private void sortSelectedPanels(){
		Collections.sort(selected);
	}


	public class PicPanel extends JPanel implements MouseListener, Comparable<PicPanel>{

		private int row;
		private int col;
		private STATUS status;

		public PicPanel(int r, int c){
			row = r;
			col = c;
			status = STATUS.EMPTY;

		}		

		//draws the cell have a white backround or one of the two images
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			if(status == STATUS.EMPTY)
				setBackground(Color.white);
			else if(status == STATUS.CHECKED)
				g.drawImage(stoneCheckedImage, 0,0,this);
			else
				g.drawImage(stoneUnCheckedImage, 0, 0, this);
		}

		//makes this panel contain a stone
		public void addStone(){
			status = STATUS.UNCHECKED;
			this.addMouseListener(this);
			this.repaint();
		}

		//highlights a stone in green to indicate it has been selected
		//adds it to the selected AL
		public void selectStone(){
			if(status == STATUS.UNCHECKED){
				status = STATUS.CHECKED;
				selected.add(this);
				this.repaint();
			}
		}

		//turns a stone's background back to white
		//removes it from the selected AL
		public void unCheckStone(){
			if(status == STATUS.CHECKED){
				status = STATUS.UNCHECKED;
				selected.remove(this);
				this.repaint();
			}
		}

		//called when a stone has been removed by a player
		//prevents this cell from ever being selected again.
		public void removeStone(){
			if(status != STATUS.EMPTY){
				status = STATUS.EMPTY;
				this.removeMouseListener(this);
				this.repaint();
			}
		}

		public boolean equals(Object o){
			if(!(o instanceof PicPanel))
				return false;

			PicPanel other = (PicPanel)o;
			return row == other.row && col == other.col;
		}

		//for debugging purposes
		public String toString(){
			return "("+row+","+col+")";
		}

		public int compareTo(PicPanel other){

			int rowDiff = this.row - other.row;


			if(rowDiff == 0) 
				return this.col - other.col;

			
			return rowDiff;
		}


		//reacts to the user either clicking the left or right mouse button
		public void mouseClicked(MouseEvent me) {
			//if statement checks when the left side is clicked if the person is clicking an already checked stone to unselect or if they want to select a new stone
			if(me.getButton()== MouseEvent.BUTTON1) {

				if(status == STATUS.CHECKED) 
					unCheckStone();
				
				else 
					selectStone();

			}
			//checks if user right clicks it first sorts then checks if their selection is valid then changes turn
			else if(me.getButton()== MouseEvent.BUTTON3) {
				sortSelectedPanels();

				//checks if no stones are selected and pops up an error message in JOptionPane
				if(selected.size()==0)
					JOptionPane.showMessageDialog(null, "Error: You must select a stone");

				//calls valid method to see if stones are able to be removed and if not it prints error message
				else if(!valid())
					JOptionPane.showMessageDialog(null, "All selected stones must be adjacent in the same row");

				//removes all selected stones in a for each loop then removes the stones from the stonesLeft number and finally it removes all stones from the selected ArrayList to prepare for next turn
				else {
					for(PicPanel greens: selected) 
						greens.removeStone();

					stonesLeft-=selected.size();
					selected.clear();


					//first checks if the player has lost and if not it will switch the turn to the opposite player and edit the title
					if(hasLost())
						JOptionPane.showMessageDialog(null, (playerNames[turn]+ " has lost the game"));
					
					else if(turn==1) {
						setTitle("Nim - "+ playerNames[0]+ " turn");
						turn=0;
					}
					else {
						setTitle("Nim - "+ playerNames[1]+ " turn");
						turn=1;
					}

				}
			}

		}

		//checks if the stones are valid or not
		private boolean valid() {
			boolean isVal=true;
			
			//two steps: (1)goes through all of the stones and checks if theyre in the same row (2) if so, checks if they stone in front of it is next to it by checking the column difference. The final if statement will only change to true if everything was true prior.
			for(int i=0;i<selected.size()-1;i++) {

				if((selected.get(i)).row - (selected.get(i+1)).row==0) {
					isVal= (isVal && ((selected.get(i)).col - (selected.get(i+1)).col==-1||(selected.get(i)).col - (selected.get(i+1)).col==0));
				}
				
				else
					return false;
			}

			return isVal;
		}

		//helper method that just checks if there are no stones are left to show the user has lost
		private boolean hasLost() {
			if (stonesLeft==0)
				return true;

			return false;
		}

		//DO NOT IMPLEMENTS THESE FOUR METHODS
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args){
		new JaimieMorris_NimTower();
	}
}



