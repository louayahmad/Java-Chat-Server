import javax.swing.JFrame;

public class ServerTest {
	public static void main (String [] args) {
		Server louay = new Server();
		louay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		louay.startRunning();
	}
}
