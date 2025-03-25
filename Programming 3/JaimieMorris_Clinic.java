/*
 * Jaimie Morris
 * Clinic program simulates patients going into a clinic to get treated by a doctor using threads, wait and notify, and synchronization
 */
import java.lang.Math.*;
import java.util.*;

public class JaimieMorris_Clinic implements Runnable{

	private int allIDs;
	private JaimieMorris_LimitedPQ waitingRoom;
	private int chairs;

	public JaimieMorris_Clinic(int n){
		chairs = n;
		allIDs = 0;
		waitingRoom = new JaimieMorris_LimitedPQ(chairs);


		Doctor doc = new Doctor();
		Thread dr = new Thread(doc);

		dr.start();

		Thread patients = new Thread(this);
		patients.start();

	}

	public void run() {
		
		
		//counts patients in the room
		int psInRoom = 1;

		//runs forever
		while(true) {
			
			Patient pat = new Patient();
			Patient added = (Patient) waitingRoom.add(pat);

			System.out.println("Patient enters waiting room");
			
			//if the patient isnt staying sends to hospital else waits and notifies the doctor
			if(!stay(added, pat)) {

				System.out.println("Patient is going to the hospital");
				psInRoom--;

				pat.waitStatus = false;
			}
			else {

				try{
					System.out.println("Patient #"+ pat.ID +" is waiting");

					Thread.sleep(500);
					synchronized(waitingRoom) {
						waitingRoom.notify();
					}

				}catch(InterruptedException ex){
					ex.printStackTrace();
				}


			}

		}

	}

	//helper method to figure out if patient is higher priority and is staying
	private boolean stay(Patient other, Patient in) {

		if (waitingRoom.getNumElements()>=chairs) {
			if(in.compareTo(other)==0)
				return false;
			else {
				return true;
			}
		}

		return true;

	}

	public class Patient implements Comparable<Patient> {

		private int ID;
		private int priority;
		private boolean waitStatus;

		public Patient() {
			allIDs++;
			ID = allIDs;
			priority = (int) (Math.random()*20) + 1;

			waitStatus = true;
		}




		public int compareTo(Patient o) {

			//returns difference between the priorities
			return this.priority = o.priority;

		}




	}

	public class Doctor implements Runnable{

		public void run() {


			while(true) {

				//if empty doctor sleeps and stays on wait until is notified
				if(waitingRoom.isEmpty()) {

					synchronized (waitingRoom) {
						
						try {
							System.out.println("doctor is going to sleep");
							waitingRoom.wait();
							
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
					
				}

				//when it wakes up it sleeps to simulate random time to treat patient
				else {
					
					try {
						System.out.println("doctor is waking up");
						Thread.sleep((int)(Math.random()));
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					


				}
			}

		}


	}

	public static void main(String []args) {
		JaimieMorris_Clinic clinic = new JaimieMorris_Clinic(6);
	}




}



