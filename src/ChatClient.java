import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	//Attributs
	static String serveurAddr = "localhost";
	static Socket client;
	PrintWriter writer;
	int serveurPort = 8888;
	
	public ChatClient(){
		try{
			client = new Socket(serveurAddr, serveurPort);
			System.out.println("Client: " + client);
			writer = new PrintWriter(client.getOutputStream());
		}catch(Exception e){
			System.out.println("Erreur connexion au serveur");
			e.printStackTrace();
			System.exit(-1);
		}
		emettre();
	}
	
	private void emettre(){
		//On envoie le message
		try{
			System.out.println("Envoi du message");
			writer.println("Valar Morghulis");
			writer.flush();
		}catch(Exception e){
			System.out.println("Erreur dans l'envoi du message");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatClient();
		try{
			client.close();
		} catch(Exception e){}
		System.out.println("Bye bye...");
	}

}
