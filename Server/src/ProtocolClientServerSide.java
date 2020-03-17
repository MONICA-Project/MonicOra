import java.awt.SecondaryLoop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ProtocolClientServerSide implements ProtocolClient {

	private BufferedOutputStream writer;
	private BufferedInputStream reader;
	private Socket serverToClient;

	public ProtocolClientServerSide(Socket serverToClient) {
		this.serverToClient = serverToClient;
		try {
			this.writer = new BufferedOutputStream(serverToClient.getOutputStream());
			this.reader = new BufferedInputStream(serverToClient.getInputStream());
		} catch (IOException e) {
			System.out.println("Impossible d'avoir le OutPutStream ou le IntputStream vers le Client");
			e.printStackTrace();
		}
	}

	@Override
	public void execute(ProtocolConstants cmd, Object...objects ) {
		//TODO Tout les cas de commande possibles
		ByteBuffer len = ByteBuffer.allocate(8);
		
		byte  cmdByte = (byte) cmd.ordinal();
		
		try {
			this.writer.write(cmdByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] data = new byte[8096];
		InputStream fis;
		
		switch(cmd) {
		case SEND_TXT:
			
			if(objects.length==0) return; //Le message n'est pas passer en arguments ...
			String str = (String) objects[0];
			System.out.println("Envoi du message: "+str);
			len.putLong((long)str.length()); // On met la taille du string dans len
			
		try {
			this.writer.write(len.array()); //Partie Len du HEADER
			this.writer.write(str.getBytes());
		} catch (IOException e) {
			System.out.println("Impossible d'envoyer du texte :");
			e.printStackTrace();
		}
		break;
		
		case SEND_AUDIO: //Audio and IMG and Video have exactly the same code, the header changes.
		case SEND_IMG:
			
			if(objects.length == 0) return; //C'est le path de l'image en argument.
			
			File f = new File((String) objects[0]); //Assert pour plus tard
			len.putLong(f.length());
			
			System.out.println("Longueur du fichier :" + f.length());
	
			int count = 0;
			
			try {
				fis = new FileInputStream(f);
				this.writer.write(len.array());
				int read = 0;
				for(;count<f.length();) {
					read = fis.read(data);
					writer.write(data,0,read);
					writer.flush();
					count += read;
				}
				fis.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		default:
			System.out.println("Cmd: "+ cmd +" non implémentée");
			break;
	}
	
	try {
		writer.flush();
		System.out.println("Flush done sur: " + serverToClient);
	} catch (IOException e) {
		try {
			this.serverToClient.close();
		} catch (IOException e1) {
			System.out.println("Impossible de fermer la socket Server -> Client");
			e1.printStackTrace();
		}
	}
	
		
		
	}

}
