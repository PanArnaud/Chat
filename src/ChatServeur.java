import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatServeur extends JFrame implements MouseListener{
	
	//Attributs
	static ServerSocket serveur;
	Vector <PrintWriter> listeClients;
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
			
			listeClients = new Vector<PrintWriter>();
			
			afficherMessage("Serveur Actif");
		}catch(Exception e){
			afficherMessage("Erreur de création du ServerSocket");
			e.printStackTrace();
			System.exit(-1);
		}
		
		ecouter();
		
	}
	
	public void afficherMessage(String message){
		textArea.append(message + "\n");
	}
	
	private void ecouter(){
		while(true){ //Boucle infini
			try{
				afficherMessage("Serveur en attente de connexion...");
				Socket client = serveur.accept();
				
				//Sorti d'attente
				String line = "";
				//Stream convertissant les bytes en caractères
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				//On utilise un tampon de mémoire
				BufferedReader reader = new BufferedReader(stream);
				
				String nom = reader.readLine();
				
				afficherMessage("Utilisateur: " + nom);
				//afficherMessage("Socket:" + client);
				
				//Créer Thread d'écoute
				EcouteClient ecouteclient = new EcouteClient(this, reader, nom);
				ecouteclient.start();
				
				//Initialise Writer
				PrintWriter writer = new PrintWriter(client.getOutputStream());
				
				//Enregistrement dans le vecteur
				listeClients.add(writer);

				//Accueil utilisateur
				envoyerATous(null, nom + " à rejoint le salon !");
			}catch(Exception e){
				afficherMessage("Erreur dans le traitement de la connexion cliente");
				e.printStackTrace();
			}
		}
	}
	
	public void envoyerATous(String nom, String message){
		if(nom != null){
			for(int i=0; i<listeClients.size(); i++)
	        {
				listeClients.get(i).println(nom + ": " + message);
				listeClients.get(i).flush();
	        }
		}else{
			for(int i=0; i<listeClients.size(); i++)
	        {
				listeClients.get(i).println(message);
				listeClients.get(i).flush();
	        }
		}
		
	}
	
	public static void main(String[] args){
		new ChatServeur();
		/*try{
			serveur.close();
		} catch(Exception e){}
		afficherMessage("Bye bye...\n");*/
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
