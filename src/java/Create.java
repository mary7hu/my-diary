import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create extends JFrame {
	private Container contents;
	private JButton btnCreate;
	private JLabel labelID, labelPassword;
	private JTextField txtID;
	private JTextField password;
	private JTextField reminder1;
	private JTextField reminder2;

	public Create() {
		super("Create New Account");
		contents = getContentPane();
		contents.setLayout(new FlowLayout());
		reminder1 = new JTextField();
		reminder2 = new JTextField();
		reminder1.setText("The password must have at least 10 characters");
		reminder2.setText("Letter and digit should be included, and letters are case sensitive");
		reminder1.setEditable(false);
		reminder2.setEditable(false);
		reminder1.setOpaque(false);
		reminder2.setOpaque(false);
		labelID = new JLabel("Enter Username");
		txtID = new JTextField("", 20);

		labelPassword = new JLabel("Enter Password");
		password = new JTextField("", 20);

		btnCreate = new JButton("Create");

		contents.add(reminder1);
		contents.add(reminder2);
		contents.add(labelID);
		contents.add(txtID);
		contents.add(labelPassword);
		contents.add(password);
		contents.add(btnCreate);

		Action ac = new Action();
		btnCreate.addActionListener(ac);
		txtID.addActionListener(ac);
		password.addActionListener(ac);

		setSize(400, 200);
		this.setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				String userName = txtID.getText();
				String userPassword = password.getText();

				if (userName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a user name!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Pattern Password_Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{10,})$");
					Matcher matcher = Password_Pattern.matcher(userPassword);
					if (matcher.matches()) {
						User user = new User(userName, userPassword);
						ArrayList<User> users = new ArrayList<>();

						FileIO io = new FileIO();
						File file = new File("users.dat");
						if (file.exists()) {
							users = io.readUserFile("users.dat");
						}
						boolean check = true;
						for (int i = 0; i < users.size(); i++) {
							String existName = users.get(i).getUserName();
							if (existName.equals(userName)) {
								JOptionPane.showMessageDialog(null, "This user name has already existed", "Error",
										JOptionPane.ERROR_MESSAGE);
								check = false;
								break;
							}
						}
						if (check) {
							users.add(user);
							io.writeUserFile("users.dat", users);
							JOptionPane.showMessageDialog(null,
									"Account successfully created!\nNow you can login your account to start your journy!",
									"Create", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"The password must have at least 10 characters, including letter and digit", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Unknown error", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
