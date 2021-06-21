import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Header extends JPanel {

	public Header() {
		super();
		this.setLayout(new FlowLayout());
		JButton simpleInquiry = new JButton("Simple Inquiry");
		this.add(simpleInquiry);
		JButton conditionalInquiry = new JButton("Conditional Inquiry");
		this.add(conditionalInquiry);
		JButton checkTicket = new JButton("Check Ticket");
		this.add(checkTicket);
		JButton changeTicket = new JButton("Change or Cancel Ticket");
		this.add(changeTicket);
	}
	
}
