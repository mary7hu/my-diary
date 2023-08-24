import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.StyledEditorKit.*;

public class Diary extends JFrame {
	private Container contents;
	private JTextPane field = new JTextPane();
	private JScrollPane scroll = new JScrollPane(field);
	private JToolBar toolBar = new JToolBar();
	private JButton[] functions = new JButton[15];
	private Color color = Color.WHITE;
	private String fileName = new String();
	private StyledDocument styleDoc;

	public Diary(String fileName) {
		super("Diary");

		this.fileName = fileName;

		functions[0] = new JButton(new ImageIcon("../resources/save.png"));
		functions[0].setToolTipText("save");
		functions[1] = new JButton(new ImageIcon("../resources/emoji.jpg"));
		functions[1].setToolTipText("emoji");
		functions[2] = new JButton(new ImageIcon("../resources/insertPic.jpg"));
		functions[2].setToolTipText("insert picture");
		functions[3] = new JButton(new ImageIcon("../resources/copy.jpg"));
		functions[3].setToolTipText("copy");
		functions[4] = new JButton(new ImageIcon("../resources/paste.jpg"));
		functions[4].setToolTipText("paste");
		functions[5] = new JButton(new ImageIcon("../resources/cut.jpg"));
		functions[5].setToolTipText("cut");
		functions[6] = new JButton(new ImageIcon("../resources/backColor.jpg"));
		functions[6].setToolTipText("background color");
		functions[7] = new JButton(new ImageIcon("../resources/fontType.png"));
		functions[7].setToolTipText("font type");
		functions[8] = new JButton(new ImageIcon("../resources/fontColor.jpg"));
		functions[8].setToolTipText("font color");
		functions[9] = new JButton(new ImageIcon("../resources/fontSize.png"));
		functions[9].setToolTipText("font size");
		functions[10] = new JButton(new ImageIcon("../resources/bold.png"));
		functions[10].setToolTipText("bold");
		functions[11] = new JButton(new ImageIcon("../resources/italic.png"));
		functions[11].setToolTipText("italic");
		functions[12] = new JButton(new ImageIcon("../resources/underline.png"));
		functions[12].setToolTipText("underline");
		functions[13] = new JButton(new ImageIcon("../resources/delete.jpg"));
		functions[13].setToolTipText("delete");
		functions[14] = new JButton(new ImageIcon("../resources/help.jpg"));
		functions[14].setToolTipText("help");

		for (int i = 0; i < functions.length; i++) {
			toolBar.add(functions[i]);
		}

		Action ac = new Action();
		for (int i = 0; i < functions.length; i++) {
			functions[i].addActionListener(ac);
		}
		
		contents = getContentPane();
		contents.add(toolBar, BorderLayout.NORTH);
		contents.add(scroll, BorderLayout.CENTER);
		int screenWidth = (int) this.getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) this.getToolkit().getScreenSize().getHeight();
        int x = (screenWidth - 520)/2;
        int y = (screenHeight - 520)/2;
        setLocation(x, y);
		setSize(520, 520);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeFrame();
			}
		});
	}

	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == functions[0]) {
				styleDoc = field.getStyledDocument();
				FileIO file = new FileIO();
				file.writeObjFile(fileName, styleDoc);
				JOptionPane.showMessageDialog(null, "The diary is successfully saved!", "Save",
						JOptionPane.INFORMATION_MESSAGE);
			} else if(e.getSource() == functions[1]) {
				String[] images = {"teach", "work", "meeting", "homework", "read", "exercise", "write", "relax", "sleep", "tick", "A plus", "F minus"};
		        String s = (String) JOptionPane.showInputDialog(null, "Please choose the emoji you want to insert", "Emoji",
		                JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), images, "");
		        field.insertIcon(new ImageIcon("../resources/" + s + ".jpg"));
			} else if (e.getSource() == functions[2]) {
				JFileChooser f = new JFileChooser();
				f.showOpenDialog(null);
				if (f.getSelectedFile() != null) {
					field.insertIcon(new ImageIcon(f.getSelectedFile().getPath()));
				}
			} else if (e.getSource() == functions[3]) {
				field.copy();
			} else if (e.getSource() == functions[4]) {
				field.paste();
			} else if (e.getSource() == functions[5]) {
				field.cut();
			} else if (e.getSource() == functions[6]) {
				color = JColorChooser.showDialog(contents, "Please choose the backgroud color", color);
				if (color == null) {
					color = Color.WHITE;
				}
				field.setBackground(color);
			} else if (e.getSource() == functions[7]) {
				String[] fontType = { "Helvetica", "Calibri", "Garamond", "Times New Roman", "Kunstler Script" };
				String font = (String) JOptionPane.showInputDialog(null, "Please choose the font:\n", "Font",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), fontType, "3");
				FontFamilyAction fontfamilyAction = new StyledEditorKit.FontFamilyAction("", font);
				fontfamilyAction.actionPerformed(e);
			} else if (e.getSource() == functions[8]) {
				color = JColorChooser.showDialog(contents, "Please choose the font color", color);
				if (color == null) {
					color = Color.BLACK;
				}
				ForegroundAction foregroundAction = new StyledEditorKit.ForegroundAction("", color);
				foregroundAction.actionPerformed(e);
			} else if (e.getSource() == functions[9]) {
				Object[] fontSize = new Object[72];
				for (int i = 0; i < 72; i++) {
					fontSize[i] = String.valueOf(i + 1);
				}
				String size = (String) JOptionPane.showInputDialog(null, "Please choose the font size:\n", "Font Size",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), fontSize, "");
				FontSizeAction fontsizeAction = new StyledEditorKit.FontSizeAction("", Integer.valueOf(size));
				fontsizeAction.actionPerformed(e);
			} else if (e.getSource() == functions[10]) {
				BoldAction boldAction = new StyledEditorKit.BoldAction();
				boldAction.actionPerformed(e);
			} else if (e.getSource() == functions[11]) {
				ItalicAction italicAction = new StyledEditorKit.ItalicAction();
				italicAction.actionPerformed(e);
			} else if (e.getSource() == functions[12]) {
				UnderlineAction underlineAction = new StyledEditorKit.UnderlineAction();
				underlineAction.actionPerformed(e);
			} else if(e.getSource() == functions[13]) {
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure to delete this diary?","Delete",JOptionPane.YES_NO_OPTION);
				if(choice == 0) {
					File oldFile = new File(fileName);
					if (oldFile.exists()) {
						oldFile.delete();
					}
					JOptionPane.showMessageDialog(null, "The diary is successfully deleted", "Delete",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			} else if(e.getSource() == functions[14]) {
				JOptionPane.showMessageDialog(null, "This is the place where you input the diary\n"
						+ "The tool in the tool bar can help you format your diary, "
						+ "functions like font size and bold will work on the selected text\n"
						+ "The function of each tool can be found by linger the curser on its icon\n"
						+ "Save is used to save current diary\nDelete is used to delete the diary\n"
						+ "Write anything you like here!", "Help",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public StyledDocument getStyleDoc() {
		styleDoc = field.getStyledDocument();
		return styleDoc;
	}

	public void setStyleDoc(StyledDocument sd) {
		field.setStyledDocument(sd);
	}

	private void closeFrame() {
		int choice = JOptionPane.showConfirmDialog(null,"Do you want to save this diary?\nReminder: Choosing NO will NOT delete the already saved version of diary","Save",JOptionPane.YES_NO_CANCEL_OPTION);
		if(choice == 0) {
			styleDoc = field.getStyledDocument();
			FileIO file = new FileIO();
			file.writeObjFile(fileName, styleDoc);
			JOptionPane.showMessageDialog(null, "The diary is successfully saved!", "Save",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}else if(choice == 1) {
			dispose();
		}else if(choice == 2) {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}
}
