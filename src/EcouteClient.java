import java.io.BufferedReader;

public class EcouteClient extends Thread{

	//Attributs
	ChatServeur serveur;
	BufferedReader reader;
	String nom;
	
	public EcouteClient(ChatServeur serveur, BufferedReader reader, String nom){
		this.serveur = serveur;
		this.reader = reader;
		this.nom = nom;
	}
	
	@Override
	public void run() {
		try{
			while(true){
				//On lit la chaîne de caractères
				String line = reader.readLine();
				//On affiche le message
				serveur.afficherMessage(nom + ": " + line);
				serveur.envoyerATous(nom, line);
			}
		}catch(Exception e){
			
		}
	}
}