
public class test {
	public static final String PASSWORD = "2853a22281111Tim";
	public static TrainReviseTicketMenu ReviseMenuPage;
	public static ModifyTicket ModifyPage;
	public static NoInformation NoInformationdPage;
	public static RefundTicket RefoundTicketPage;
	public static SuccessfulRevise SuccessfulRevisePage;

	public static void main(String[] args) {
		ReviseMenuPage = new TrainReviseTicketMenu();//­ì¥»¬O©I¥sModify
		ReviseMenuPage.setVisible(true);
	}

}
