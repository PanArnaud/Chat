import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatServeur extends JFrame implements MouseListener{
	
	//Attributs
	static ServerSocket serveur;
	int serveurPort = 8888;
	static JTextArea textArea;
	JButton exit;
	
	//On instancie le ServerSocket
	public ChatServeur(){
		
		super("Serveur de chat");
		
		this.setSize(400, 380);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setEditable(false);
		container.add(scrollPane, BorderLayout.CENTER);
		
		exit = new JButton("Exit");
		exit.addMouseListener(this);
		container.add(exit, BorderLayout.SOUTH);

		this.setVisible(true);
		
		try{
			serveur = new ServerSocket(serveurPort);
			afficherMessage("ServerSocket: " + serveur);
			afficherMessage("Serveur Actif");
		}catch(Exception e){
			afficherMessage("Erreur de cr�ation du ServerSocket");
			e.printStackTrace();
			System.exit(-1);
		}
		ecouter();
		
	}
	
	private void afficherMessage(String message){
		textArea.append(message + "\n");
	}
	
	private void ecouter(){
		while(true){ //Boucle infini
			try{
				afficherMessage("Serveur en attente de connexion...");
				Socket client = serveur.accept();
				
				//Sorti d'attente
				afficherMessage("Client connect�");
				afficherMessage("Socket:" + client);
			
				//Stream convertissant les bytes en caract�res
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				
				//On utilise un tampon de m�moire
				BufferedReader reader = new BufferedReader(stream);
				
				//On lit la cha�ne de caract�res
				String line = reader.readLine();
			
				//On affiche le message puis on ferme la connexion et on sort de la boucle.
				afficherMessage("Message re�u:" + line);
				client.close();
				break;
			}catch(Exception e){
				afficherMessage("Erreur dans le traitement de la connexion cliente");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new ChatServeur();
		try{
			serveur.close();
		} catch(Exception e){}
		textArea.append("Bye bye...\n");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
			
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == exit){
			System.exit(-1);
		}
	}
}
