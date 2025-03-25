import java.awt.event.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;

/*
 * Jaimie Morris
 * Creates a stopwatch using GUI.
 */
public class JaimieMorris_StopWatch extends JFrame implements ActionListener {

	private JButton start;
	private JButton stop;
	private JLabel display;
	private int startTime;

	//creates GUI pop-up w/ size, location, layout, color, and adds the widgets
	public JaimieMorris_StopWatch() {

		setTitle("Stop Watch");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(230,120);
		setLocationRelativeTo(null);

		getContentPane().setBackground(new Color(250,140,200));


		start=new JButton("Start");
		stop=new JButton("Stop");
		stop.setEnabled(false);

		display=new JLabel("Press start to begin timing");

		start.addActionListener(this);
		stop.addActionListener(this);

		setLayout(new FlowLayout());

		add(start);
		add(stop);
		add(display);

		setVisible(true);
	}
//once it recieves a click it tests if it is start or stop then proceeds based on the following code
	public void actionPerformed(ActionEvent ae) {

		int stopTime=0;
		display.setText("hello ");

		if(ae.getActionCommand().equals("Start")) {
			startTime = LocalDateTime.now().getSecond();
			display.setText("running");
			start.setEnabled(false);
			stop.setEnabled(true);

		}
		else {
			stopTime=LocalDateTime.now().getSecond();

			start.setEnabled(true);
			stop.setEnabled(false);
			
			if(startTime>stopTime)
				stopTime+=60;
			
			display.setText("Time: "+(stopTime-startTime));
		}

	}
	public static void main(String[]args) {
		JaimieMorris_StopWatch sw=new JaimieMorris_StopWatch();
	}
}
