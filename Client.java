import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message ="";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client(String host) {
		super("Client Person!");
		serverIP = host; 
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
			);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(700,400);
		setVisible(true);
	}


	//connect to sever
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException) {
			showMessage("\n Client terminated connection");
			
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}finally {
		closeCrap();
	}
}
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket (InetAddress.getByName(serverIP), 6789);
		showMessage("Connected to:" + connection.getInetAddress().getHostName());
	}
	
	//set up streams to send and receive messages
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n You are now good to go! \n");
	}
	
	//while chatting with server
	private void whileChatting() throws IOException{
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotfoundException) {
				showMessage("\n I dont know that object type");
			}
		}while(!message.contentEquals("Server - End"));
	}
	
	//close streams and sockets
	private void closeCrap() {
		showMessage("\n Closing connections... \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	//send a message to client
	private void sendMessage(String message) {
		try{
			output.writeObject("Client - "+ message);
			output.flush();
			showMessage("\nClient - "+ message);
		}catch(IOException ioException) {
			chatWindow.append("\n Error: Can't send that message");
		}
	}
	
	//updates chatWindow
	private void showMessage(final String m) {
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run (){
					chatWindow.append(m);
				}
			}	
		);
	}

				
	//let the user type stuff into their box
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run() {
					userText.setEditable(tof);
				}
			}	
		);
	}
}
