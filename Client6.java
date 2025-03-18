package programming2;

public class Client6 {

	public static void main(String[] args) {
		MyArrayList num = new MyArrayList2(10);
		MyArrayList2 nums = new MyArrayList2(10);
		for (int i =0;i<=10;i++) {
			nums.add(i);
		}System.out.println(nums);
		nums.add(2,9);
		System.out.println(nums);
		nums.removeIndex(2);
		System.out.println(nums);
		System.out.println(nums.removeElement(5));
		System.out.println(nums);
		System.out.println(nums.set(1,7));
		System.out.println(nums);	
		System.out.println("size: "+nums.size());	
		MyArrayList2 myList = new MyArrayList2();
		myList.add(new Book("One of Us", 10, 7));
			
		Object result = myList.removeIndex(0);

	}

}
