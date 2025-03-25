/*
 * Jaimie Morris
 * This program uses the previous class' Dictionary and Defintion to create an SAT Review program. Uses GUI to search and list
 */
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.event.*;


// This demonstrates a Single Choice JList.  The choice is displayed in a label
public class View extends JFrame implements ListSelectionListener, ActionListener{

	private JList<String> list;
	private JTextArea actualDef;
	private JLabel def;
	private JLabel search;
	private JTextField searchBar;
	private Dictionary listOfWords=new Dictionary("wordlist.txt");
	private DefaultListModel<String> wordsList; 

	public View(){

		wordsList = new DefaultListModel<String>();

		//this constructor sets up the layout of the pop up and adds all of the widgets inside
		PicPanel mainPanel = new PicPanel("glitter.jpg");
		mainPanel.setBounds(0,0,500,700);
		mainPanel.setLayout(null);

		setTitle("SAT Review");
		setSize(500,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		def = new JLabel();
		def.setBounds(110,510,100,100);

		actualDef = new JTextArea("");
		actualDef.setEditable(false);
		actualDef.setLineWrap(true);
		actualDef.setWrapStyleWord(true);
		actualDef.setOpaque(false);
		actualDef.setBounds(170,550,250,100);

		search = new JLabel("Search: ");
		search.setBounds(100,45,200,25);

		searchBar = new JTextField(30);
		searchBar.addActionListener(this);
		searchBar.setBounds(170,45,200,25);

		list = new JList<String>(wordsList);  
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		list.addListSelectionListener(this);  
		list.setBounds(20,20,200,200);

		JScrollPane jscp = new JScrollPane(list);  
		jscp.setBounds(100,100,300,400);


		mainPanel.add(def);
		mainPanel.add(actualDef);
		mainPanel.add(jscp);
		mainPanel.add(search);
		mainPanel.add(searchBar);
		add(mainPanel);

		setVisible(true);

	}
	//reads in image to set background
	public class PicPanel extends JPanel{

		private BufferedImage image;

		public PicPanel(String fname){

			//reads the image
			try {
				image = ImageIO.read(new File(fname));

			} catch (IOException ioe) {
				System.out.println("Could not read in the pic");
				System.exit(0);
			}

		}

		//this will draw the image
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,0,0,this);
		}
	}

	//when a word is clicked, this function will display Def: with the definition
	public void valueChanged(ListSelectionEvent le){

		int index = list.getSelectedIndex();

		if(index != -1) {

			def.setText("Def: ");
			actualDef.setText((listOfWords.get(index)).getDef());

		}

	}

	//when enter us hit this function calls the function from dictionary to find all of the words with the input inside- the loop goes through the valid words and adds them to the list
	public void actionPerformed(ActionEvent ae) {
		wordsList.removeAllElements();
		for(Definition word: listOfWords.getHits(searchBar.getText())) {
			wordsList.addElement(word.getWord());
		}
	}

	public static void main(String[] args){
		new View();
	}

}

