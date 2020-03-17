
public class Main {

	public static void main(String[] args) {
		Server s = new Server("192.168.1.42",9999);
		s.open();
		new Thread(new KeyboardThread(s)).start();
	}
}
