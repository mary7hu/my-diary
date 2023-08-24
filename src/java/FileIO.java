import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.text.StyledDocument;

public class FileIO implements Serializable {
	public FileIO() {

	}
	//write the diary
	public void writeObjFile(String fileName, StyledDocument styleDoc) {
		try {
			File save = new File(fileName);
			if (!save.getParentFile().exists()) {
				save.getParentFile().mkdirs();	//Create the folder if the address doesn't exist
			}
			if (!save.exists()) {
				save.createNewFile();	//Create the file if the file doesn't exist
			}
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(save));	//Save the styledDocument
			outputStream.writeObject(styleDoc);
			outputStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "Error writing to file " + fileName, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	//read the diary
	public StyledDocument readObjFile(String fileName) {
		StyledDocument styleDoc = null;
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
			styleDoc = (StyledDocument) inputStream.readObject();	//Read the styledDocument
			inputStream.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error", "Error reading the file " + fileName,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return styleDoc;	//Return the styledDocument (the diary)
	}

	public void writeUserFile(String fileName, ArrayList<User> users) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			outputStream.writeObject(users);
			outputStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "Error writing to file " + fileName, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	public ArrayList<User> readUserFile(String fileName){
		ArrayList<User> users = new ArrayList<>();
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
			try {
				while (true) {
					users = (ArrayList<User>) inputStream.readObject();
				}
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Error", "Error reading the file " + fileName,
						JOptionPane.ERROR_MESSAGE);
			}
			inputStream.close();
		} catch (IOException e) {
			
		}
		return users;
	}
}
