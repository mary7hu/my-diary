import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.StyledDocument;

public class CheckDiary extends JFrame {
	private File file;

	public CheckDiary(String userName, int year, int month, String date) {
		super("Check Diary");
		file = new File(userName + "/" + year + "/" + month + "/" + date);
		String[] stringList = file.list();
		if (stringList == null || stringList.length == 0) {
			JOptionPane.showMessageDialog(null, "There's no diary exist yet. Try create a new one!", "Error",
					JOptionPane.ERROR_MESSAGE);
			dispose();
		} else {
			String[] options = new String[stringList.length];
			for (int i = 0; i < stringList.length; i++) {
				options[i] = "Diary " + String.valueOf(i + 1);
			}
			String option = (String) JOptionPane.showInputDialog(null,
					"Please choose the diary you want to open\n(the diaries are ordered according to the time saved)",
					"Open", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), options, "Diary");
			String fileName = stringList[Integer.valueOf(option.substring(6)) - 1];
			FileIO fio = new FileIO();
			StyledDocument styleDoc = fio
					.readObjFile(userName + "/" + year + "/" + month + "/" + date + "/" + fileName);
			Diary diary = new Diary(userName + "/" + year + "/" + month + "/" + date + "/" + fileName);
			if (styleDoc != null) {
				diary.setStyleDoc(styleDoc);
			}
		}
		setVisible(false);
	}
}
