package programming2;

/*
 * Jaimie Morris
 * This program creates a bookstore that has functions to add
 * and remove books as well as search for them
 * 
 * It works when you add Magazine or TravelGuide to the client because
 * they are both types of books and bookstore holds books.
 * ex: travelguide is a book but a book is not a travelguide!
 */
public class BookStore {
	private Book[] myBooks;

	public BookStore() {
		myBooks=new Book[0];
	}

	public void add(Book toAdd) {
		Book[] arr=new Book[myBooks.length];
		int val=myBooks.length;

		if(val==0){ //adding a book to a previously empty array
			arr=new Book[myBooks.length+1];
			arr[0]=toAdd;
			myBooks=arr;
		}
	
		else if (val>0) { 
			// Cycle through and increment if book is the same
			for(int i=0;i<myBooks.length;i++) {
				int num=myBooks[i].compareTo(toAdd);
				//System.out.println(num);
				// If book is the same, increment the counter of that book
				if(num==0) {
					// Increment quantity and we're done
					myBooks[i].setQuantity(toAdd.getQuantity());


					// short circuit
					return;
				}
			}
			// If we make it to here, it's a new book
			arr=new Book[myBooks.length+1];

			// Cycle through to do an array copy, leaving the last one blank
			for(int i=0;i<arr.length-1;i++) {
				arr[i]=myBooks[i];
			}
			// Add the last book to the end
			arr[arr.length-1]= toAdd; 
		}
		myBooks=arr;

		sort();


	}
	private void sort() {
		for(int i=0;i<myBooks.length;i++) {
			int minInd=i;
			for(int j=i+1;j<myBooks.length;j++) 
				if(myBooks[j].compareTo(myBooks[i])>0) {
					minInd = j;

					Book temp = myBooks[minInd];
					myBooks[minInd] = myBooks[i];
					myBooks[i] = temp;
				}
		}
	}
	public double sell(String title, int q) {
		double amt=-1;
		for(int i=0;i<myBooks.length;i++) {
			if(myBooks[i].getTitle()==title) {
				if(myBooks[i].getQuantity()<q) {//cannot sell so returns nothing
					return 0;
				}
				amt+= 1+(myBooks[i].getPrice()*q);
				myBooks[i].setQuantity(-q);
			}
		}
		return amt;
	}
	public void remove(String title) {
		if(search(title)==true) { //first checks if the book is in the array
			int ctr=0;
			Book[] arr=new Book[myBooks.length-1];
			while(myBooks[ctr].getTitle()!=title) {//adds all of the books prior to the one being removed
				arr[ctr]=myBooks[ctr];
				ctr++;
			}
			if(ctr!=arr.length) {
				for(int i=ctr;i<arr.length;i++)
					arr[i]=myBooks[i+1];//adds all of the books following the one removed

			}
			myBooks=arr;
		}
	}
	public void display() {
		if(myBooks.length>0) {
			for(int i=0;i<myBooks.length;i++) {
				System.out.println(myBooks[i].toString());//uses the toString to print
			}
		}
	}
	public boolean search(String title) {//uses binary search along with the compareTo method to find the title
		int target=0;
		int mid=0;
		int s=0;//s means starting location (starts at 0)
		int e=myBooks.length-1;//e means end Location (starts at array length)

		while(s <= e){
			mid = (s + e)/2;
			if(myBooks[mid].compareTo(myBooks[mid])== target){
				return true;
			}
			else if(myBooks[mid].compareTo(myBooks[mid]) > target){
				e = mid - 1; //checks right subarray
			}
			else{
				s = mid + 1;//checks left subarray
			}
		}
		return false;
	}




}



