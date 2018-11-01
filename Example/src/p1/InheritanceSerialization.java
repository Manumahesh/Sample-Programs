package p1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Parent implements Serializable
{
	int age;
	String name;
	 
	public Parent(int age, String name)
	{
		this.age=age;
		this.name=name;
	}
}

class Child extends Parent
{
	int age1;
	String name1;
	
	public Child(int age,int age1,String name, String name1)
	{
		super(age,name);
		this.age1=age1;
		this.name1=name1;
	}
	
	public String toString()
	{
		return age+" "+name+" "+age1+" "+name1;
	}
	
}

public class InheritanceSerialization {

	public static void main(String[] args) throws Exception 
	{
		Parent p=new Parent(50,"ikjkj");
		Child c=new Child(50,25,"asdk","kdsa");
		Child c1=null;
		
		String filename="Parent.txt";
		
		FileOutputStream file=new FileOutputStream(filename);
		ObjectOutputStream oos=new ObjectOutputStream(file);
		oos.writeObject(c);
		System.out.println("Successfully executed");
		
		FileInputStream fos=new FileInputStream(filename);
		ObjectInputStream ois=new ObjectInputStream(fos);
		c1=(Child)ois.readObject();
		System.out.println(c1);
		
	}

}
