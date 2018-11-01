package p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Collection {

	public static void main(String[] args) 
	{
		Set<String> nonRepetations = new HashSet<String>();
		List<String> listOfNames=new ArrayList<String>();
		Map<Integer,String> mapNames=new HashMap<Integer,String>();
		System.out.println("Enter the number of names:");
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();
		
		for(int i=0;i<n;i++)
		{
			listOfNames.add(scan.next());
		}
		System.out.println(listOfNames);
		
		for(int i=0;i<n;i++)
		{
			nonRepetations.addAll(listOfNames);
		}
		System.out.println(nonRepetations);
		for(int i =0;i<nonRepetations.size();i++){
			mapNames.put(i,nonRepetations.iterator().next());
		}
		System.out.println(mapNames.toString());
		
		
		scan.close();
	}
}
