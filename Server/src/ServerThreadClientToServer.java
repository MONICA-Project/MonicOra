import java.io.IOException;
import java.net.Socket;

public class ServerThreadClientToServer implements Runnable {

	private Socket clientToServer;
	private ProtocolServer prot; // TODO: Instantiate this.
	private boolean isRunning = true;
	
	public ServerThreadClientToServer(Socket clientToServer, ServerConnection connection) {
		this.clientToServer = clientToServer;
		this.prot = new ProtocolServerServerSide(clientToServer,connection);
	}
	
	@Override
	public void run() {
		
		System.out.println("Serveur pret a écouter: "+clientToServer.toString() );
		while(isRunning) {
			try {
				prot.answerTo();
			} catch (IOException e) {
				System.out.println("Erreur de connection ou connection perdue avec "+this.clientToServer.getInetAddress().toString()+". Fermeture de la socket: " + e.getMessage());
				try {
					this.clientToServer.close();
					this.close();
				} catch (IOException e1) {
					System.out.println("Impossible de fermer la socket");
					this.isRunning = false;
					e1.printStackTrace();
				}
			}
		}
	}

	public void close() {
		this.isRunning = false;
	}
	
}
