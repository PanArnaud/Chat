import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChatServeur extends JFrame {
	
	//Attributs
	static ServerSocket serveur;
	int serveurPort = 8888;
	JTextArea textArea;
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
		container.add(textArea, BorderLayout.CENTER);
		exit = new JButton("Exit");
		container.add(exit, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
		try{
			serveur = new ServerSocket(serveurPort);
			System.out.println("ServerSocket: " + serveur);
			System.out.println("Serveur Actif");
		}catch(Exception e){
			System.out.println("Erreur de création du ServerSocket");
			e.printStackTrace();
			System.exit(-1);
		}
		ecouter();
	}
	
	private void ecouter(){
		while(true){ //Boucle infini
			try{
				System.out.println("Serveur en attente de connexion...");
				Socket client = serveur.accept();
				
				//Sorti d'attente
				System.out.println("Client connecté");
				System.out.println("Socket:" + client);
			
				//Stream convertissant les bytes en caractères
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				
				//On utilise un tampon de mémoire
				BufferedReader reader = new BufferedReader(stream);
				
				//On lit la chaîne de caractères
				String line = reader.readLine();
			
				//On affiche le message puis on ferme la connexion et on sort de la boucle.
				System.out.println("Message reçu:" + line);
				client.close();
				break;
			}catch(Exception e){
				System.out.println("Erreur dans le traitement de la connexion cliente");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new ChatServeur();
		try{
			serveur.close();
		} catch(Exception e){}
		System.out.println("Bye bye...");
	}
}
