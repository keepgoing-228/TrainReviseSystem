import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class RefundTicket extends JFrame implements TrainReviseSystem, ActionListener {

	private int startStationID, endStationID,numbersOfTicket,tempPrice;
	private String ID,code,trainNo,startEnName,endEnName;
	private Calendar departureDay;
	private Time departureTime,arriveTime;
	private GridBagConstraints gridBag;
	private GridBagConstraints positionGridBag;
	
	public RefundTicket(String ID, String code) {
		super("Refound");
		this.setSize(TrainReviseSystem.WIDTH, TrainReviseSystem.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		initialize(ID,code);
		catchTicketData(ID,code);
		catchTrainData(trainNo);
		//create a panel of position
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		
		//create a panel to input information 
		JPanel originalInformationPanel = new JPanel();
		originalInformationPanel.setLayout(new GridBagLayout());
		
		JLabel originalDateLabel = new JLabel("Original Date  ");
		originalDateLabel.setOpaque(true);
		originalDateLabel.setBackground(Color.lightGray);
		gridBag = new GridBagConstraints();
		gridBag.ipadx = 10;
//		gridBag.insets = new Insets(0,0,0,10);
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalDateLabel,gridBag);
		gridBag.gridy = 1;
//		originalInformationPanel.add(new JLabel("?/?"),gridBag);
		originalInformationPanel.add(new JLabel(String.format("%s/%s",departureDay.get(Calendar.MONTH)+1 , departureDay.get(Calendar.DATE))),gridBag);

		
		JLabel originalDepartureTimeLabel = new JLabel("Departure Time  ");
		originalDepartureTimeLabel.setOpaque(true);
		originalDepartureTimeLabel.setBackground(Color.lightGray);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalDepartureTimeLabel,gridBag);
		gridBag.gridy = 1;
		originalInformationPanel.add(new JLabel(String.format("%s", departureTime)),gridBag);
		
		JLabel originalArriveTimeLabel = new JLabel("Arrive Time  ");
		originalArriveTimeLabel.setOpaque(true);
		originalArriveTimeLabel.setBackground(Color.lightGray);
		gridBag.gridx = 2;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalArriveTimeLabel,gridBag);
		gridBag.gridy = 1;
		originalInformationPanel.add(new JLabel(String.format("%s", arriveTime)),gridBag);
		
		JLabel originalStartingStationLabel = new JLabel("Starting Station  ");
		originalStartingStationLabel.setOpaque(true);
		originalStartingStationLabel.setBackground(Color.lightGray);
		gridBag.gridx = 3;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalStartingStationLabel,gridBag);
		gridBag.gridy = 1;
		originalInformationPanel.add(new JLabel(String.format("%s", startEnName)),gridBag);

		
		JLabel originalEndingStationLabel = new JLabel("Ending Station  ");
		originalEndingStationLabel.setOpaque(true);
		originalEndingStationLabel.setBackground(Color.lightGray);
		gridBag.gridx = 4;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalEndingStationLabel,gridBag);
		gridBag.gridy = 1;
		originalInformationPanel.add(new JLabel(String.format("%s", endEnName)),gridBag);

		JLabel originalNumberLabel = new JLabel("numbers");
		originalNumberLabel.setOpaque(true);
		originalNumberLabel.setBackground(Color.lightGray);
		gridBag.gridx = 5;
		gridBag.gridy = 0;
		originalInformationPanel.add(originalNumberLabel,gridBag);
		gridBag.gridy = 1;
		originalInformationPanel.add(new JLabel(String.format("%d", numbersOfTicket)),gridBag);

		// create a panel to show refund and price
		JPanel changingPanel = new JPanel();
		changingPanel.setLayout(new GridBagLayout());

		JLabel NumbersOfTicket = new JLabel("Total Numbers Of Refund Ticket:");
		NumbersOfTicket.setOpaque(true);
		NumbersOfTicket.setBackground(Color.lightGray);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		changingPanel.add(NumbersOfTicket, gridBag);
		gridBag.gridx = 1;
		changingPanel.add(new JLabel(String.format("  %d", numbersOfTicket)), gridBag);


		JLabel price = new JLabel("Price:");
		price.setOpaque(true);
		price.setBackground(Color.lightGray);
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		changingPanel.add(price, gridBag);
		gridBag.gridx = 1;
		changingPanel.add(new JLabel(String.format("  %d", tempPrice * numbersOfTicket)), gridBag);

		// create a panel to confirm or cancel
		JPanel confirmPanel = new JPanel();
		confirmPanel.setLayout(new GridBagLayout());

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(this);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		confirmPanel.add(confirmButton, gridBag);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		gridBag.gridx = 1;
		confirmPanel.add(cancelButton, gridBag);

		// Panel position of original Info
		positionGridBag = new GridBagConstraints();
		positionGridBag.gridx = 0;
		positionGridBag.gridy = 0;
		positionPanel.add(originalInformationPanel, positionGridBag);

		// Panel position of changing
		positionGridBag.insets = new Insets(50, 0, 0, 0);
		positionGridBag.anchor = GridBagConstraints.WEST;
		positionGridBag.gridy = 1;
		positionPanel.add(changingPanel, positionGridBag);

		// Panel position of confirm or cancel
		positionGridBag.anchor = GridBagConstraints.CENTER;
		positionGridBag.gridy = 2;
		positionPanel.add(confirmPanel, positionGridBag);

		this.add(positionPanel, BorderLayout.CENTER);

	}

	private void initialize(String iD2, String code2) {
		this.ID=iD2;
		this.code = code2;
	}
	private void catchTicketData(String ID, String code) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();//連結
			ResultSet result = stmt.executeQuery(String.format("select startStation, trainNo, endStation, departureDay, price from ticket where code ='%s';",code));//做查詢，在MySQL做的code
			result.next();
			
			Date Date1 = result.getDate("departureDay");
			departureDay = Calendar.getInstance();
			departureDay.setTime(Date1);
			
			tempPrice = result.getInt("price");
			trainNo = result.getString("trainNo");
			startStationID = result.getInt("startStation");
			endStationID = result.getInt("endStation");
			numbersOfTicket++;
//			System.out.printf("%d",startStationID);
			while(result.next()) {
				numbersOfTicket++;
			}
			result.close();
			
//			System.out.printf("%s %s",departureDay.get(Calendar.MONTH)+1 , departureDay.get(Calendar.DATE));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void catchTrainData(String trainNo) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select * from stop where trainNo ='%s' and ID = %d;",trainNo,startStationID));
			result.next();
			departureTime = result.getTime("departureTime");
			result.close();
			
			Statement stmt4 = conn.createStatement();
			ResultSet result4 = stmt4.executeQuery(String.format("select * from stop where trainNo ='%s' and ID = %d;",trainNo,endStationID));
			result4.next();
			arriveTime = result4.getTime("departureTime");
			result4.close();
			
			
			Statement stmt2 = conn.createStatement();
			ResultSet result2 = stmt2.executeQuery(String.format("select ID,enName from station where ID = %d;",startStationID));
			result2.next();
			this.startEnName = result2.getString("enName");
			result2.close();

			Statement stmt3 = conn.createStatement();
			ResultSet result3 = stmt3.executeQuery(String.format("select ID,enName from station where ID = %d;",endStationID));
			result3.next();
			this.endEnName = result3.getString("enName");
			result3.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Cancel": {
			this.setVisible(false);
			this.dispose();
			test.ReviseMenuPage.setVisible(true);
			break;
		}
		case"Confirm":{
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
						test.PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String.format("delete from ticket "
													+ "where code = '%s' "
													+ "limit %d;", code,numbersOfTicket));
				
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.setVisible(false);
			this.dispose();
//			test.ReviseMenuPage.setVisible(true);
			test.SuccessfulRevisePage = new SuccessfulRevise();
			test.SuccessfulRevisePage.setVisible(true);
			break;
		}
		}
	}
	

}
