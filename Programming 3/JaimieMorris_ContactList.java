/*Jaimie Morris
 * Contact list program uses a map to add and remove email addresses to an alias and has other functions that iterate thru sets and maps.
 */

import java.util.*;

public class JaimieMorris_ContactList {

	private Map<String, Set> contactList;

	public static void main(String[] args) {
		JaimieMorris_ContactList myContactList = new JaimieMorris_ContactList();
		myContactList.add("faculty", "gwmorris@gmail.com");
		myContactList.add("faculty", "jaimiearin@gmail.com");
		myContactList.add("student", "hallievyn@gmail.com");
		myContactList.add("others", "faculty");
		myContactList.print();
		
	}
	
	public JaimieMorris_ContactList() {
		contactList=new TreeMap<String, Set>();
	}
	//Adds member to the group groupName.  Creates the group if groupName is not found.
	public void add(String groupName, String groupMember) {
		Set<String> group;

		//if new key creates a new set
		if(contactList.get(groupName)==null) {
			group = new TreeSet<String>();
		}

		//if existing key set already has an existing set associated with that key
		else {
			group = contactList.get(groupName);
		}

		group.add(groupMember);
		contactList.put(groupName, group);


	}
//Removes the entire group from the ContactList.  This includes removing it as a member of other groups.  Returns false if the group is not found.
	public boolean remove(String groupName) {
		//if the alias doesn't exits 
		if(contactList.remove(groupName) == null)
			return false;

		//loops thru all keys and calls remove to remove certain subsequent alias if found
		for(String key: contactList.keySet()) 
			remove(key, groupName);

		return true;
	}

	//Removes a member from the group.  Returns false if they were not a member to begin with or if the group does not exist.
	public boolean remove(String groupName, String member) {
		boolean hasMember=false;

		Set<String> curSet = contactList.get(groupName);

		//if member was removed then member existed
		if(curSet.remove(member))
			hasMember=true;


		return hasMember;
	}

	//Prints all group names and members in tabular format
	public void print(){  

		Formatter fmt = new Formatter();

		fmt.format("%15s %15s\n", "Alias:", "Members:");

		//loops thru all alias'
		for (String key : contactList.keySet())   { 
			String members = " ";
			Set<String> curSet = contactList.get(key);

			//loops thru alias' set and adds it to the member string which holds all members from the current set
			for(String mems: curSet)
				members+=mems+", ";

			fmt.format("%14s %15s\n", key, members);  

		}  


		System.out.println(fmt);  
	} 

	//A helper method that adds all items of the set to the end of the queue 
	private void appenSetToQueue(Set<String> items, Queue<String> queue) {

		//loops thru all members in the set and adds it to queue
		for(String members: items)
			queue.add(members);

	}

	//Given a starting alias your ultimate goal is to build a set of all email address with all subsequent aliases removed.  
	public Set<String> expandAlias(String alias){
		
		boolean isSubsequent=false;
		Set<String>fullSet = new TreeSet<String>();


		Set<String> curSet = contactList.get(alias);

		//loops thru set and finds if it has any subsequent alias'
		for(String mems: curSet) {

			for (String key : contactList.keySet()) {

				//if so switch to true so it is not added again later and then adds the extended alias' set to fullSet using the set to queue class
				if (mems.equals(key)) {
					isSubsequent=true;
					Queue<String> queue=new LinkedList<String>();

					appenSetToQueue(contactList.get(key), queue);

					while(!queue.isEmpty()) {
						fullSet.add(queue.remove());

					}

				}

			}

			if (!isSubsequent)
				fullSet.add(mems);
		}

		return fullSet;

	}


}


