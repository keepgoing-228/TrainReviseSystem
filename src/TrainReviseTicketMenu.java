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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;



public class TrainReviseTicketMenu extends JFrame implements TrainReviseSystem,ActionListener{
	
	private GridBagConstraints gridBag;
	private GridBagConstraints positionGridBag; 
	private JTextField userID, userCode;
	private String ID,code;
	private int startStationID, endStationID;
	
	public TrainReviseTicketMenu() {//直接把原本的Modify class寫在這
		super("Modify and refund Ticket");//因為有extends，所以以下都不用寫(JFrame class.)
//		setTitle("Modify and refund Ticket");//super就可以設定
		this.setSize(TrainReviseSystem.WIDTH, TrainReviseSystem.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		//Header on the top of the page. Maybe a menu bar is better.
		this.add(new Header(), BorderLayout.NORTH);
		
		
		//create a panel of position
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		positionGridBag = new GridBagConstraints();
		
		//create a panel to input information 
		JPanel InformationPanel = new JPanel();
		InformationPanel.setLayout(new GridBagLayout());
		JPanel chooseButtonPanel = new JPanel();
		chooseButtonPanel.setLayout(new GridBagLayout());
		
		
		
		//information of booker
		//input ID
		JLabel IDLabel = new JLabel("ID:");
		IDLabel.setOpaque(true);
		IDLabel.setBackground(Color.lightGray);
		gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		InformationPanel.add(IDLabel,gridBag);
		
		userID = new JTextField(15);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		InformationPanel.add(userID,gridBag);
		
		//input code
		JLabel codeLabel = new JLabel("Ticket Code:");
		codeLabel.setOpaque(true);
		codeLabel.setBackground(Color.lightGray);
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		InformationPanel.add(codeLabel,gridBag);
		
		userCode = new JTextField(15);
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		InformationPanel.add(userCode,gridBag);
		
		
		positionGridBag.anchor = GridBagConstraints.WEST;
		positionGridBag.fill = GridBagConstraints.BOTH;
		positionGridBag.gridx = 0;
		positionGridBag.gridy = 0;
		positionPanel.add(InformationPanel,positionGridBag);

		
		//choose revise or refund
		JButton reviseButton = new JButton("Modify");
		reviseButton.addActionListener(this);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		chooseButtonPanel.add(reviseButton,gridBag);
		
		JButton refoundButton = new JButton("Refund");
		refoundButton.addActionListener(this);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		chooseButtonPanel.add(refoundButton,gridBag);
		
		

		positionGridBag.gridx = 0;
		positionGridBag.gridy = 1;
		positionPanel.add(chooseButtonPanel,positionGridBag);

		
		//Panel position
		this.add(positionPanel, BorderLayout.CENTER);
		
		
		//exit program
//		JButton endButton = new JButton("Click to end program.");
//		endButton.addActionListener(new EndingListener());
//		endButton.addActionListener(this);
//		this.add(endButton, BorderLayout.SOUTH);
		
	}
	
	private int checkInfo() {
		try {
			ID = userID.getText();
			code = userCode.getText();
//			code = Integer.parseInt(userCode.getText());
			
			
			
			//connect to MySQL
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);//進入
			Statement stmt = conn.createStatement();//連結
			ResultSet result = stmt.executeQuery(String.format("select code from booking where code = '%s' and payerID = '%s';",code,ID));//做查詢，在MySQL做的code
			
			if(result.next()) {
//				System.out.println(result);
				return 1;
			}
//			result.getInt("code");
//			result.getInt(1);
			result.close();				
			
			return 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	


	
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case"Modify":{
			if(checkInfo() == 1) {
				test.ModifyPage = new ModifyTicket(ID,code);
				test.ModifyPage.setVisible(true);
				this.setVisible(false);
			}else {
				test.NoInformationdPage = new NoInformation();
				test.NoInformationdPage.setVisible(true);
				this.setVisible(false);
			}
			break;
		}
		case"Refund":{
			if(checkInfo() == 1) {
				test.RefoundTicketPage = new RefundTicket(ID,code);
				test.RefoundTicketPage.setVisible(true);
				this.setVisible(false);
			}else {
				test.NoInformationdPage = new NoInformation();
				test.NoInformationdPage.setVisible(true);
				this.setVisible(false);
			}
			break;
		}
		} 
	}		
}
