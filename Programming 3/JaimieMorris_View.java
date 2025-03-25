/*
 * Jaimie Morris
 * Web scraper program looks at the page source of a school directory and prints staff corresponding with the school to a gui using threads and string methods
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.imageio.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.event.*;

//gives the user a look and feel to navigate all categories

public class JaimieMorris_View extends JFrame implements ListSelectionListener{

	private JList<String> schools;
	private DefaultListModel<String> schoolModel;

	private JList<String> staff;
	private DefaultListModel<String> staffModel;

	private Map<String,ArrayList<String>> directory;

	public JaimieMorris_View(){

		setSize(600,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Wootton Cluster Schools and Staff");
		setLayout(null);

		PicPanel mainPanel = new PicPanel("diploma.jpg");
		mainPanel.setBackground(Color.white);
		mainPanel.setBounds(0,0,600,300);
		mainPanel.setLayout(null);

		//create the list for school names
		schoolModel = new DefaultListModel<String>();
		schools = new JList<String>(schoolModel);
		schools.addListSelectionListener(this);
		JScrollPane schoolJSP = new JScrollPane(schools);
		schoolJSP.setBounds(50,40,200,200);
		schoolJSP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Schools"));
		schoolJSP.setOpaque(false);
		mainPanel.add(schoolJSP);

		//create the list for staff in the categories
		staffModel = new DefaultListModel<String>();
		staff = new JList<String>(staffModel);
		staff.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane staffJSP = new JScrollPane(staff);
		staffJSP.setBounds(300,40,250,200);
		staffJSP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Staff"));
		staffJSP.setOpaque(false);
		mainPanel.add(staffJSP);

		//create the labels to display the amount of
		//people in each school

		add(mainPanel);

		//stores the staff at each school
		directory = new HashMap<String,ArrayList<String>>();

		loadDir();

		setVisible(true);
	}

	private void loadDir(){

		Scanner schoolIn = null;
		Scanner staffIn = null;
		//reading school names and urls
		//from two separate files
		try{
			schoolIn = new Scanner(new File("schools"));
			staffIn = new Scanner(new File("sites"));
		}catch(FileNotFoundException e){
			System.out.println("At least one text file not found");
			System.exit(-1);
		}


		ArrayList<Thread> allthreads = new ArrayList<Thread>();

		//adds the threads to an arraylist 		
		while(staffIn.hasNextLine() && schoolIn.hasNextLine()) {
			
			Scrape first = new Scrape(schoolIn.nextLine(), staffIn.nextLine());
			Thread one = new Thread(first);
			
			allthreads.add(one);
			one.start();
		}


		//the arraylist is used to loop thru for join
		for(Thread nextThread: allthreads){

			try{
				nextThread.join();

			}catch(InterruptedException e){
				e.printStackTrace();
			}

		}


	}

	public void valueChanged(ListSelectionEvent le){

		//clear out all staff each time the user selects
		//a new school
		staffModel.removeAllElements();

		int location = schools.getSelectedIndex();
		if(location == -1)
			return;

		//update the staff list based on selected categories
		String selectedSchool = schoolModel.get(location);
		ArrayList<String> allStaff = directory.get(selectedSchool);
		if(allStaff == null)
			return;
		for(String school: allStaff){
			staffModel.addElement(school);
		}
	}

	public class Scrape implements Runnable{

		private String site;
		private String schoolName;

		public Scrape(String n,String s){
			schoolName = n;
			site = s;

		}

		public void run(){


			Scanner reader = getConnection(site);

			ArrayList<String> staff = new ArrayList<>();

			String html = "";


			//adds the site to one giant string
			while(reader.hasNextLine()) {
				html+=reader.nextLine().trim();
			}

			String name = "";
			int index = 0;

			//while employeename is found in the string it finds the index of the name and cuts down the html string
			while(index>=0) {

				index = html.indexOf("employeename");
				html = html.substring(index+18);

				int afterName = html.indexOf("<");

				//if a < is found then that is where the name ends and adds it
				if (afterName>=0) {
					name = html.substring(0, afterName);
					staff.add(name);
				}
				//else it ends the loop
				else
					index = -1;

			}
			
			synchronized(directory) {
				directory.put(schoolName, staff);
			}

			//adds elements to the gui
			schoolModel.addElement(schoolName);

			for(String s: staff)
				staffModel.addElement(s);


		}


		//disables certificate validation for https connection
		private Scanner getConnection(String site) {

			TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() {
						public X509Certificate[] getAcceptedIssuers() { 
							return new X509Certificate[0]; 
						}
						public void checkClientTrusted(X509Certificate[] certs, String authType) {}
						public void checkServerTrusted(X509Certificate[] certs, String authType) {}
					}
			};

			// Ignore differences between given hostname and certificate hostname
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) { return true; }
			};

			try {
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
			} catch (Exception e) {
				System.exit(-2);
			}

			try {
				URL url = new URL(site);
				HttpsURLConnection  connection = (HttpsURLConnection)url.openConnection();
				if(connection == null){
					System.out.println("No connection found");
					return null;
				}
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
				connection.connect();

				return new Scanner(connection.getInputStream());

			}catch(IOException e) {
				System.exit(-1);
			}

			return null;
		}



	}

	class PicPanel extends JPanel{

		private BufferedImage image;
		private int w,h;
		public PicPanel(String fname){

			//reads the image
			try {
				image = ImageIO.read(new File(fname));
				w = image.getWidth();
				h = image.getHeight();

			} catch (IOException ioe) {
				System.out.println("Could not read in the pic");
				System.exit(0);
			}

		}

		public Dimension getPreferredSize() {
			return new Dimension(w,h);
		}
		//this will draw the image
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,0,0,this);
		}
	}

	public static void main(String[] args){
		JaimieMorris_View first = new JaimieMorris_View();



	}
}
