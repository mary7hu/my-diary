import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Login extends JFrame {
	private Container contents;
	private JButton btnLogin;
	private JLabel labelID, labelPassword;
	private JTextField txtID;
	private JPasswordField password;

	public Login() {
		super("Login");
		contents = getContentPane();
		contents.setLayout(new FlowLayout());
		labelID = new JLabel("Enter Username");
		txtID = new JTextField("", 14);

		labelPassword = new JLabel("Enter Password");
		password = new JPasswordField(14);
		password.setEchoChar('*');

		btnLogin = new JButton("Login");

		contents.add(labelID);
		contents.add(txtID);
		contents.add(labelPassword);
		contents.add(password);
		contents.add(btnLogin);

		Action ac = new Action();
		btnLogin.addActionListener(ac);
		txtID.addActionListener(ac);
		password.addActionListener(ac);

		setSize(300, 140);
		this.setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				String userName = txtID.getText();
				String userPassword = password.getText();

				FileIO io = new FileIO();

				ArrayList<User> users = new ArrayList<>();
				
				users = (ArrayList<User>) io.readUserFile("users.dat");
				if (users.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please create an account before login", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int index = 0;
					int count = 0;
					while (index < users.size()) {
						User temp = users.get(index);
						String name = temp.getUserName();
						String password = temp.getPassword();
						if (userName.equals(name) && userPassword.equals(password)) {
							JOptionPane.showMessageDialog(null, "Welcome to MyDiary, " + name + "!", "Welcome",
									JOptionPane.INFORMATION_MESSAGE);
							Calendar calendar = new Calendar(name);
							dispose();
						} else {
							count = count + 1;
						}
						if(count == users.size()) {
							JOptionPane.showMessageDialog(null, "Incorrect Login", "Please try again.",
									JOptionPane.ERROR_MESSAGE);
						}
						index = index + 1;
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Unknown error", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
