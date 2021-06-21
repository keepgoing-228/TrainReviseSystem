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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class SuccessfulRevise extends JFrame implements TrainReviseSystem,ActionListener{
	public SuccessfulRevise() {
		super("Success");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JLabel NoInformationLabel = new JLabel("Congratulations To Change Your Ticket!!!");
		this.add(NoInformationLabel, BorderLayout.CENTER);
		
		JButton backTrainReviseTicketMenuButton = new JButton("back");
		backTrainReviseTicketMenuButton.addActionListener(this);
		this.add(backTrainReviseTicketMenuButton, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		test.ReviseMenuPage.setVisible(true);
		this.dispose();		
	}
	
}
