import java.util.*;


public class MapDemo {

	public static void main(String[] args){
		
		Map<Integer, String> students = new TreeMap<Integer,String>();
		
		students.put(770991, "Melissa");
		students.put(321178, "Paul");
		students.put(345991, "Cynthia");
		students.put(588123, "Chris");
		
		//traversal example.  must get keys first
		Set<Integer> allIDs = students.keySet();
		Iterator<Integer> iter = allIDs.iterator();
		
		//iterate across the keys
		while(iter.hasNext()){
			
			Integer nextID = iter.next();
			
			System.out.println(nextID + "  " + students.get(nextID));
		}
		
		//this is the same as the loop above.
		//for-each loop is more concise form of traversal
		for(Integer id: students.keySet())
			System.out.println(id + "  :   " + students.get(id));
		
		
		Map<String,List<String>> fbID = new TreeMap<String,List<String>>();
		
		List<String> friends = new LinkedList<String>();
		fbID.put("Bubba", friends);
		friends.add("Tacoma");	//since LL is mutable, I don't need to re-insert 
								//it back into the map
	
				
		
		
		
		
		
		
		
		
		
		
	}
}
