import java.net.ServerSocket;
import java.net.Socket;

public class HauptThread extends Thread {

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(6666)) {
			while (true) {
				Socket socket = serverSocket.accept();
				ClientThread clientThread = new ClientThread(socket);
				clientThread.start();
			}
		} catch (Exception e) {
			System.out.println("Server Fehler: " + e.getMessage());
		}
	}
}
