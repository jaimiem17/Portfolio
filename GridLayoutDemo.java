import java.awt.*;
public class GridLayoutDemo extends JFrame {

	public GridLayoutDemo(){
		
		setSize(300,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(Color.blue);
		
		//seprate the frame into 6 evenly divisible segments with 3 rows and 2 columns
		//provide 5 pixels of horizontal and vert space
		setLayout(new GridLayout(3,2,5,5));
		
		
		int counter = 1;
		for(int row = 0; row < 3; row++){
			for( int col = 0; col < 2; col++){
				//buttons are added from left to right one row at a time (top to bottom)
				add(new JButton(counter+""));
				counter++;
			}
		}
	
		
		setVisible(true);
	}
	
	public static void main(String[] args){
		new GridLayoutDemo();
	}
}

