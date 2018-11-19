package classes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LicenseCreator {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		File delete=new File("License.ser");
		delete.delete();
		License l=new License();
		FileOutputStream fos=new FileOutputStream("License.ser",true);
		ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream (fos));
		
		oos.writeObject(l);
		oos.close();
		fos.close();
		
		FileInputStream fis=new FileInputStream("License.ser");
		ObjectInputStream ois=new ObjectInputStream(fis);
		License l1=(License)ois.readObject();
		ois.close();
		fis.close();
		

	}

}
