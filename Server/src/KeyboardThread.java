import java.net.ServerSocket;
import java.util.Scanner;

public class KeyboardThread implements Runnable{
	
	Server server;
	
	public KeyboardThread(Server server) {
		this.server = server;
	}
	
	@Override
	public void run() {
	      while(true) {
	    	  Scanner sc = new Scanner(System.in);
	    	  System.out.println("1:MSG,2:IMG,3:AUDIO");
	    	  try {
	    		  int choice = Integer.parseInt(sc.nextLine());
			
	    	  System.out.println("Veuillez saisir l'argument du message :");
	    	  String str = sc.nextLine();
	    	  System.out.println("Pour qui ? Voici les users :"+ Server.getConnections());
	    	  int id = sc.nextInt();
	    	  switch(choice) {
	    	  case(1): server.sendTxt(str, id);
	    	  	break;
	    	  case(2): server.sendImg(str, id);
	    	  	break;
	    	  case(3): server.sendAudio(str, id);
	    	  break;
	    	  }
	    	  } catch (Exception e) {
					System.out.println("Erreur de format");
	    		  continue;
				}
	    	  
	    	  
	    	  }
	}

}
