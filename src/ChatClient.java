import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements MouseListener {

	//Attributs
	static String serveurAddr = "localhost";
	static Socket client;
	PrintWriter writer;
	int serveurPort = 8888;
	static JTextField textField;
	static JTextArea textArea;
	static JPanel bottomSide;
	JButton envoyer;
	
	public ChatClient(){
		
		super("Client de chat");
		
		this.setSize(400, 380);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		textField = new JTextField();
		bottomSide = new JPanel();
		bottomSide.setLayout(new GridLayout(1, 2));
		
		textArea = new JTextArea();
		
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setEditable(false);
		
		bottomSide.add(textField, BorderLayout.CENTER);
		container.add(bottomSide, BorderLayout.SOUTH);
		envoyer = new JButton("Envoyer");
		envoyer.addMouseListener(this);
		bottomSide.add(envoyer);
		
		container.add(scrollPane, BorderLayout.CENTER);

		this.setVisible(true);
		
		try{
			client = new Socket(serveurAddr, serveurPort);
			afficherMessage("Client: " + client);
			writer = new PrintWriter(client.getOutputStream());
		}catch(Exception e){
			afficherMessage("Erreur connexion au serveur");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static void afficherMessage(String message){
		textArea.append(message + "\n");
	}
	
	private void emettre(String message){
		//On envoie le message
		try{
			writer.println(message);
			writer.flush();
			textField.setText("");
		}catch(Exception e){
			System.out.println("Erreur dans l'envoi du message");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatClient();
		//try{
			//client.close();
		//} catch(Exception e){}
		//afficherMessage("Bye bye...");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == envoyer){
			String message = textField.getText();
			emettre(message);
		}
	}

}
