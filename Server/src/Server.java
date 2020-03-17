import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
	
	private static int port = 9999;
	private static String host = "192.168.1.18";
	private static ServerSocket server = null;
	private static boolean isRunning = true;
	private static ArrayList<ServerConnection> connections = new ArrayList<>();

	public Server(){
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Server(String pHost, int pPort){
		host = pHost;
		port = pPort;
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	   public void open(){
		      System.out.println("Serveur lancé :"+server.toString());
		      //Toujours dans un thread à part vu qu'il est dans une boucle infinie
		      Thread t = new Thread(new Runnable(){
		         public void run(){
		            while(isRunning == true){
		               
		               try {
		                  //On attend une connexion d'un client
		                  Socket client = server.accept();
		                  
		                  //Une fois reçue, on la traite dans un thread séparé
		                  System.out.println("Connexion cliente reçue par l'IP: "+client);
		                  ServerConnection serverCon = new ServerConnection(client);
		                  connections.add(serverCon);
		                  new Thread(serverCon).start();
		                  
		                  
		               } catch (IOException e) {
		                  System.out.print("Impossible d'initialiser le serveur");
		            	   e.printStackTrace();
		               }
		            }
		            
		            try {
		               server.close();
		            } catch (IOException e) {
		               e.printStackTrace();
		               server = null;
		            }
		         }
		      });
		      
		      t.start();
		   }
		   
		   public void close(){
		      isRunning = false;
		   }
	
	public void sendTxt(String str, int id) {
		connections.get(id).doCmd(ProtocolConstants.SEND_TXT, str);
	}
	
	public void sendImg(String path, int id) {
		connections.get(id).doCmd(ProtocolConstants.SEND_IMG, path);
	}
	
	public void sendAudio(String path, int id) {
		connections.get(id).doCmd(ProtocolConstants.SEND_AUDIO, path);
	}
	
	public static ArrayList<ServerConnection> getConnections(){
		return connections;
	}
	
	public void close(int id) {
		getConnections().get(id).close();
	}

}
