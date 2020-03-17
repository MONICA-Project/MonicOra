import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Runnable {

	private Socket client = null;
	private Socket server = null;
	private ServerThreadClientToServer clientToServer = null;
	private ServerThreadServerToClient serverToclient = null;
	private int clientPort = 9999;
	
	ServerConnection(Socket client){
		this.client = client;
	}

	private void connect() {
		try {
			this.server = new Socket(client.getInetAddress(), clientPort);
		} catch (IOException e1) {
			System.out.println("Impossible de se connecter avec le client: Serveur -> Client");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connect();
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.connect();
		this.clientToServer = new ServerThreadClientToServer(client, this);
		this.serverToclient = new ServerThreadServerToClient(this.server);
		Thread tcs = new Thread(clientToServer); //Creation des deux threads et lancement
		Thread tsc = new Thread(serverToclient);
		tcs.start();
		tsc.start();
		try {
			tsc.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientToServer.close(); //Si il est terminé, alors on peut tuer l'autre
		serverToclient.close();
	}

	public void doCmd(ProtocolConstants cmd, Object...objects) {
		this.serverToclient.doCmd(cmd, objects);
	}
	
	public void close(){
		this.serverToclient.close();
		this.clientToServer.close();
		Server.getConnections().remove(this);
	}
	
	
	public String toString() {
		return this.client.getInetAddress().getHostAddress() + ":" + this.client.getPort();
	}
	
	
}
