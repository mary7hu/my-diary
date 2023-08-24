import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calendar extends JFrame {
	private Container contents;
	private JButton btn[] = new JButton[2];
	private CalendarPanel calendarPanel;

	public Calendar(String userName) {
		super("Calendar");
		calendarPanel = new CalendarPanel(userName);
		contents = getContentPane();
		contents.setLayout(new BorderLayout(15, 15));
		contents.add(makeCenterPane(), BorderLayout.CENTER);
		contents.add(makeBottomPane(), BorderLayout.SOUTH);

		Action ac = new Action();
		for (int i = 0; i < 2; i++) {
			btn[i].addActionListener(ac);
		}
		
		int screenWidth = (int) this.getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) this.getToolkit().getScreenSize().getHeight();
        int x = (screenWidth - 720)/2;
        int y = (screenHeight - 280)/2;
        setLocation(x, y);
		setSize(720, 280);
		setVisible(true);
		JOptionPane.showMessageDialog(null, "Now you have successfully login your personal account.\n"
				+ "Click on the date and start writing the diary!\n"
				+ "You can also read your diary of a specific day by clicking on it.", "Help", JOptionPane.INFORMATION_MESSAGE);
	}

	public JComponent makeCenterPane() {
		JPanel panel = new JPanel();
		panel.add(calendarPanel);
		return panel;
	}

	public JComponent makeBottomPane() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		String num[] = { "<-", "->" };
		for (int i = 0; i < 2; i++) {
			btn[i] = new JButton();
			btn[i].setBackground(Color.WHITE);
			btn[i].setText(num[i]);
			panel.add(btn[i]);
		}
		return panel;
	}

	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int year = calendarPanel.getYear();
			if (e.getSource() == btn[0]) {
				int currentMonth = calendarPanel.getMonth();
				if (currentMonth == 0) {
					calendarPanel.setYear(year);
					year = year - 1;
				}
				calendarPanel.setMonth((currentMonth - 1) % 12);
			} else if (e.getSource() == btn[1]) {
				int currentMonth = calendarPanel.getMonth();
				if (currentMonth == 11) {
					year = year + 1;
					calendarPanel.setYear(year);
				}
				calendarPanel.setMonth((currentMonth + 1) % 12);
			}
		}
	}
}
