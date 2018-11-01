package p1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Example implements Serializable
{
	private static final long serialVersionUID = -4953489543679428606L;
	int i=7;
	String str="askdja";
	

	public String toString()
	{
		return i+" "+str;
	}
}

public class Ser {
	

	public static void main(String[] args) throws Exception 
	{
		Example e=new Example();
		Example os=new Example();
		
		String f="Exa.txt";
		FileOutputStream fis=new FileOutputStream(f);
		ObjectOutputStream ois=new ObjectOutputStream(fis);
		ois.writeObject(e);
		System.out.println("Successful");
		
		FileInputStream fos=new FileInputStream(f);
		ObjectInputStream oos=new ObjectInputStream(fos);
		os=(Example)oos.readObject();
		System.out.println(e);
	}

}
