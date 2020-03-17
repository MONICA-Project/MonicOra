import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicReference;

public class ServerThreadServerToClient implements Runnable {

	private final Object pauseLock = new Object(); //In order to wait for new cmd
	private Socket client;
	private ProtocolClient prot; // TODO: Instantiate this.
	private AtomicReference<ProtocolConstants> cmd = new AtomicReference<ProtocolConstants>(ProtocolConstants.CMD_DONE); //Utilisation dans un thread !
	private AtomicReference<Object[]> cmdArgs = new AtomicReference<Object[]>(null);
	private boolean isRunning = true;

	public ServerThreadServerToClient(Socket client) {

		//On retient la connection client -> server
		this.client = client;
		//On se connect au client maintenant
		//TODO:On creer met le protocole serveur
		this.prot = new ProtocolClientServerSide(this.client);

	}

	@Override
	public void run() {
		System.out.println("Connection avec Serveur -> Client OK sur :"+this.client);
		while(isRunning) {
			synchronized (pauseLock) {
				try {
					pauseLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				prot.execute(this.cmd.get(), this.cmdArgs.get());
				System.out.println("Envoi de CMD:"+cmd.get());
			}
		}
	}

	public void close() {
		synchronized (pauseLock) {
			this.isRunning = false;
			this.pauseLock.notify();
		}
	}

	public synchronized void doCmd(ProtocolConstants cmd, Object...objects) {
		synchronized (pauseLock) {
			this.cmd.set(cmd);
			this.cmdArgs.set(objects);
			pauseLock.notify();
		}
	}

}
