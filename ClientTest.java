import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[] args) {
		Client hamza;
		hamza = new Client("127.0.0.1");
		hamza.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hamza.startRunning();
	}

}
