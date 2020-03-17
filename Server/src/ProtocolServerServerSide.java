import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Timestamp;

public class ProtocolServerServerSide implements ProtocolServer {

	
	private static String PATH = "C:\\Users\\sismi\\Desktop\\MonicaServerRes";
	private BufferedOutputStream writer;
	private BufferedInputStream reader;
	private Socket clientToServer;
	private ServerConnection connection;
	private File f;
	BufferedOutputStream bos;
	
	public ProtocolServerServerSide(Socket sock, ServerConnection connection) {
		this.clientToServer = sock;
		try {
			this.writer = new BufferedOutputStream(sock.getOutputStream());
			this.reader = new BufferedInputStream(sock.getInputStream());
			this.connection = connection;
		} catch (IOException e) {
			System.out.println("Impossible d'avoir le OutPutStream ou InputStream vers le Client");
			e.printStackTrace();
		}
	}
	
	@Override
	public void answerTo() throws IOException {
		
		byte[] header = new byte[9];
		reader.read(header);
		ProtocolConstants cmd = getCmd(header[0]);
		long len = getLen(header);
		byte[] data = new byte[8096]; //Buffer
		long count=0; //Compteur d'octets lu
		
		System.out.println("CMD: "+cmd+", Len: "+len+ ", par "+this.clientToServer);
		//TODO: To be completed to handle each CMD ! Maybe send ack each time cmd is computed
		
		switch(cmd) {
		case SEND_TXT:	
			for(;count<len;) {
				count += reader.read(data);
				System.out.println(new String(data));
			}
			break;
		case SEND_POS:	
			for(;count<len;) {
				count += reader.read(data);
				System.out.println(new String(data));
			}
			break;
		case ERROR:
			throw new IOException("Commande ERROR Reçu");
			
		case CLOSE:
			this.clientToServer.close();
			Server.getConnections().remove(this.connection);
			break;
			
		case SEND_IMG:
			f = new File(PATH + "\\img\\"+this.clientToServer.getInetAddress().getHostAddress()+"_"+(new Timestamp(System.currentTimeMillis()).toString())); //Creating file with Ip and date.
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
			for(;count<len;) { //Ecriture de l'image dans le fichier
				count += reader.read(data);
				bos.write(data);
			}
			bos.close();
			break;
		
		case SEND_AUDIO:
			f = new File(PATH + "\\audio\\"+this.clientToServer.getInetAddress().getHostAddress()+"_"+(new Timestamp(System.currentTimeMillis()).toString())); //Creating file with Ip and date.
			bos = new BufferedOutputStream(new FileOutputStream(f));
			for(;count<len;) { //Ecriture de l'image dans le fichier
				count += reader.read(data);
				bos.write(data);
			}
			bos.close();
			break;		
			
		default:
			System.out.println("Requete: " + cmd + " pas pris en charge par le serveur");
			break;
		}
		
		
		
	}
	
	
	private ProtocolConstants getCmd(byte b) {
		int ord = (int) b;
		if(ord>ProtocolConstants.values().length) {
			throw new IllegalArgumentException("Header pas bon");
			//TODO: Handle errors of this type
		}
		return ProtocolConstants.values()[(int) b];
	}
	
	private long getLen(byte[] b) {
		return ByteBuffer.allocate(8).wrap(b, 1, 8).getLong();
	}

}
