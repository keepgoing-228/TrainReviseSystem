import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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


public class ModifyTicket extends JFrame implements TrainReviseSystem,ActionListener{
	
	private int startStationID, endStationID,numbersOfTicket,newNumbers;
	private String ID,code,trainNo,startEnName,endEnName,newEndStation;
	private Calendar departureDay;
	private Time departureTime,arriveTime;
	private GridBagConstraints gridBag;
	private GridBagConstraints positionGridBag;
	JComboBox<String> endStation;
	JComboBox<String> numbers;

	public ModifyTicket(String ID, String code) {
		super("Modify Ticket");//因為有extends，所以以下都不用寫(JFrame class.)
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

		
		//create a panel to change ending station or reduce numbers
		JPanel changingPanel = new JPanel();
		changingPanel.setLayout(new GridBagLayout());
		
		JLabel changeEndingStationLabel = new JLabel("Change Ending Station:");
		changeEndingStationLabel.setOpaque(true);
		changeEndingStationLabel.setBackground(Color.lightGray);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		changingPanel.add(changeEndingStationLabel,gridBag);
		
		JLabel changeNumbersOfTicket = new JLabel("Change Numbers Of Ticket:");
		changeNumbersOfTicket.setOpaque(true);
		changeNumbersOfTicket.setBackground(Color.lightGray);
		gridBag.gridy = 1;
		changingPanel.add(changeNumbersOfTicket,gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		endStation = stationComboBoxFactory();
		changingPanel.add(endStation,gridBag);
		
		gridBag.gridy = 1;
		numbers = numbersComboBoxFactory();
		changingPanel.add(numbers,gridBag);

		
		
		//create a panel to confirm or cancel
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
		
		
		//Panel position of original Info
		positionGridBag = new GridBagConstraints();
		positionGridBag.gridx = 0;
		positionGridBag.gridy = 0;
		positionPanel.add(originalInformationPanel,positionGridBag);
		
		//Panel position of changing
		positionGridBag.insets = new Insets(50,0,0,0);
		positionGridBag.anchor = GridBagConstraints.WEST;
		positionGridBag.gridy = 1;
		positionPanel.add(changingPanel,positionGridBag);
		
		//Panel position of confirm or cancel
		positionGridBag.anchor = GridBagConstraints.CENTER;
		positionGridBag.gridy = 2;
		positionPanel.add(confirmPanel,positionGridBag);
		
		this.add(positionPanel, BorderLayout.CENTER);

		
	}

	private void catchTicketData(String ID, String code) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();//連結
			ResultSet result = stmt.executeQuery(String.format("select startStation, trainNo, endStation, departureDay from ticket where code ='%s';",code));//做查詢，在MySQL做的code
			result.next();
			
			Date Date1 = result.getDate("departureDay");
			departureDay = Calendar.getInstance();
			departureDay.setTime(Date1);
			
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

	private void initialize(String iD2, String code2) {
		this.ID=iD2;
		this.code = code2;
	}




	private JComboBox<String> stationComboBoxFactory(){
		JComboBox<String> comboBox = new JComboBox<String>();
		int i = 0;
		String[] chooseEndStationID = new String[11];
//		System.out.println("hi");
		if((startStationID - endStationID) < 0) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
						test.PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(String.format("select * from stop where trainNo = '%s' and ID < %d and ID > %d;",trainNo,endStationID,startStationID));
				
				while(result.next()) {
					String test = IDTransToEnName(result.getInt("ID"));
					chooseEndStationID[i] = test;
//					System.out.println(test);
//					chooseEndStationID[i] = result.getString(ID);
//					System.out.printf("%d",chooseEndStationID[i]);
					i++;
				}
				result.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else if((startStationID - endStationID) > 0) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
						test.PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(String.format("select * from stop where trainNo = '%s' and ID < %d and ID > %d;",trainNo,startStationID,endStationID));
				
				while(result.next()) {
					String test = IDTransToEnName(result.getInt("ID"));
					chooseEndStationID[i] = test;
					i++;
				}
				result.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for(int j = 0; j < i; j++) {
			comboBox.addItem(chooseEndStationID[j]);
		}
		return comboBox;
	}
	
	
	private JComboBox<String> numbersComboBoxFactory() {
		JComboBox<String> comboBox = new JComboBox<String>();
		String[] chooseFinalNumbers = new String[numbersOfTicket];
		for(int i = 0; i < numbersOfTicket; i++) {
			chooseFinalNumbers[i] = String.valueOf(i+1);
		}
		for(int j = 0; j < chooseFinalNumbers.length; j++) {
			comboBox.addItem(chooseFinalNumbers[j]);
		}
		return comboBox;
	}
	
	private String IDTransToEnName(int stationID) {
		String enName = null;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select * from station where ID = %d;",stationID));
			result.next();
			enName = result.getString("enName");
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enName;
	}
	private int enNameTransToID(String enName) {
		int ID = 0 ;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select * from station where enName = '%s';",enName));
			result.next();
			ID = result.getInt("ID");
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case"Cancel":{
			this.setVisible(false);
			this.dispose();
			test.ReviseMenuPage.setVisible(true);
			break;
		}
		case"Confirm":{
			newNumbers = (this.numbers.getSelectedIndex())+1;
			newEndStation =  this.endStation.getSelectedItem().toString();
			int newStation = enNameTransToID(newEndStation);
//			System.out.println(numbersOfTicket+" "+ newNumbers);
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
						test.PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String.format("update ticket set endStation = %d where code = '%s';",newStation, code));
				if(numbersOfTicket != newNumbers) {
					stmt.executeUpdate(String.format("delete from ticket "
													+ "where code = '%s' "
													+ "limit %d;", code,newNumbers));
				}
				
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
