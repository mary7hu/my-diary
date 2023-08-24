import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class CalendarPanel extends JPanel {
	private JLabel header = new JLabel(" ", JLabel.CENTER);
	private JButton[] daysNum = new JButton[49];
	private Calendar calendar = new GregorianCalendar();
	private int year, month;
	private int day0 = calendar.get(Calendar.DATE);
	private int year0 = calendar.get(Calendar.YEAR);
	private int month0 = calendar.get(Calendar.MONTH);
	private String clickDate = "";
	private JPanel daysLayout = new JPanel(new GridLayout(0, 7));
	private String userName;

	public CalendarPanel(String userName) {
		this.userName = userName;
		header.setBackground(Color.WHITE);
		daysLayout.setBackground(Color.WHITE);

		Action ac = new Action();
		for (int i = 0; i < 49; i++) {
			daysNum[i] = new JButton();
			daysNum[i].setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
			daysNum[i].setBackground(Color.WHITE);
			daysNum[i].setHorizontalAlignment(JLabel.RIGHT);
			daysNum[i].setVerticalAlignment(JLabel.TOP);
			daysNum[i].addActionListener(ac);
		}

		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);

		updateCalendar();
		showHeader();
		showDays();

		this.setLayout(new BorderLayout());
		this.add(header, BorderLayout.NORTH);
		this.add(daysLayout, BorderLayout.CENTER);
		setSize(700, 500);
		setVisible(true);
	}

	private void showHeader() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
		String headerText = sdf.format(calendar.getTime());
		header.setText(headerText);
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Calibri", Font.BOLD, 25));
	}

	public void showDays() {
		daysLayout.removeAll();    //remove days from previous month

		//set weekday names on first line
		DateFormatSymbols dfs = new DateFormatSymbols();
		String dayNames[] = dfs.getWeekdays();
		for (int i = 0; i < 7; i++) {
			daysNum[i].setText(dayNames[i + 1]);
			daysNum[i].setForeground(Color.BLACK);
			daysNum[i].setHorizontalAlignment(JLabel.CENTER);
			daysNum[i].setFont(new Font("Calibri", Font.BOLD, 20));
			daysLayout.add(daysNum[i]);
		}
		
		int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, -1);	//since we set date to 1 (line 127), so -1 brings us to the previous month
		int daysInPrecedingMonth = calendar.getActualMaximum(Calendar.DATE);
		//set the days from previous month
		for (int i = 0; i < startingDayOfMonth - 1; i++) {
			daysNum[i + 7].setForeground(Color.LIGHT_GRAY);
			daysNum[i + 7].setHorizontalAlignment(JLabel.CENTER);
			daysNum[i + 7].setText(daysInPrecedingMonth - startingDayOfMonth + 2 + i + "");
			daysNum[i + 7].setFont(new Font("Calibri", Font.ITALIC, 15));
			daysLayout.add(daysNum[i + 7]);
		}

		calendar.add(Calendar.DATE, 1);
		int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DATE);
		//set the days in current month
		for (int i = 1; i <= daysInCurrentMonth; i++) {
			if (i == day0 && year == year0 && month == month0) {
				daysNum[i - 2 + startingDayOfMonth + 7].setForeground(Color.red);
				daysNum[i - 2 + startingDayOfMonth + 7].setHorizontalAlignment(JLabel.CENTER);
				daysNum[i - 2 + startingDayOfMonth + 7].setText(i + "");
				daysNum[i - 2 + startingDayOfMonth + 7].setFont(new Font("Calibri", Font.BOLD, 20));
				daysLayout.add(daysNum[i - 2 + startingDayOfMonth + 7]);
			} else {
				daysNum[i - 2 + startingDayOfMonth + 7].setForeground(Color.darkGray);
				daysNum[i - 2 + startingDayOfMonth + 7].setHorizontalAlignment(JLabel.CENTER);
				daysNum[i - 2 + startingDayOfMonth + 7].setText(i + "");
				daysNum[i - 2 + startingDayOfMonth + 7].setFont(new Font("Calibri", Font.ITALIC, 15));
				daysLayout.add(daysNum[i - 2 + startingDayOfMonth + 7]);
			}
		}

		//set the days for next month
		int trailingDays = 1;
		for (int i = daysInCurrentMonth - 1 + startingDayOfMonth + 7; i % 7 != 0; i++) {
			daysNum[i].setForeground(Color.LIGHT_GRAY);
			daysNum[i].setHorizontalAlignment(JLabel.CENTER);
			daysNum[i].setText(trailingDays + "");
			daysNum[i].setFont(new Font("Calibri", Font.ITALIC, 15));
			trailingDays = trailingDays + 1;
			daysLayout.add(daysNum[i]);
		}

		daysLayout.repaint();
	}

	public void updateCalendar() {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public void setMonth(int month) {
		this.month = month;
		updateCalendar();
		showHeader();
		showDays();
	}

	public void setYear(int year) {
		this.year = year;
		updateCalendar();
		showHeader();
		showDays();
	}
	
	//Access the diary for a specific day
	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i = 7; i < 49; i++) {
				if (e.getSource() == daysNum[i]) {
					//The date that the user choose is recorded
					clickDate = daysNum[i].getText();
					Object[] options = {"Write","Read"};
					int trueMonth = Integer.valueOf(month) + 1;
					//Message asking whether user want to read or write diary
					int choice = JOptionPane.showOptionDialog(null,"You want to write or read the diary for the day "
					+ year + "/" + trueMonth + "/" + clickDate + "?","Message",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
	                if(choice == 0){
	                	//Diary class invoked
	                    Diary diary = new Diary(userName + "/" + year
	                    		+ "/" + trueMonth + "/" + clickDate + "/" + Calendar.getInstance().getTimeInMillis());
	                }else if(choice == 1){
	                	//CheckDiary class invoked
	                	CheckDiary checkDiary = new CheckDiary(userName, year, trueMonth, clickDate);
	                }
				}
			}
		}
	}
}
