package marvin.apoteket16.utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AsqlFileSaver {

	public static void saveObject(Serializable objToSave, String name) throws IOException{
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOutput = null;
		try {
			fileOut = new FileOutputStream(name);
			objectOutput = new ObjectOutputStream(fileOut);
			objectOutput.writeObject(objToSave);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			fileOut.close();
			objectOutput.close();
		}

	}
}
