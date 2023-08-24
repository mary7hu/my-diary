import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {
	private Container contents;
	
    private JButton login = new JButton("Login");
    private ImageIcon diaryImage = new ImageIcon("../resources/Diary.png");
    private JLabel picture = new JLabel();
    private JButton create = new JButton("Create new account");

	public Home() {
		super("MyDiary");
        contents = getContentPane();
        contents.setLayout(new FlowLayout());
        contents.setBackground(Color.GREEN);
        picture.setIcon(diaryImage);
        //setting the size, color and font of the button
        login.setBackground(Color.YELLOW);
        login.setFont(new Font("",Font.BOLD,20));
        login.setPreferredSize(new Dimension(200, 30));
        create.setBackground(Color.YELLOW);
        create.setFont(new Font("",Font.BOLD,17));
        create.setPreferredSize(new Dimension(200, 30));

        //adding actions to the buttons
        Action ac = new Action();
        login.addActionListener(ac);
        create.addActionListener(ac);
        
        contents.add(picture);
        contents.add(login);
        contents.add(create);
        
        int screenWidth = (int) this.getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) this.getToolkit().getScreenSize().getHeight();
        int x = (screenWidth - 400)/2;
        int y = (screenHeight - 400)/2;
        
        setLocation(x, y);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(null, "Welcome to MyDiary!\nThis is a program that help people take diary easily"
        		+ "\nStart your journy now by create and login your account!");
	}

	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource() == login) {
				Login login = new Login();
			}else if(ae.getSource() == create) {
				Create create = new Create();
			}
		}
	}
}
