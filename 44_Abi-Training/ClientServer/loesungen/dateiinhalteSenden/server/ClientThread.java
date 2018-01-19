import java.net.*;
import java.io.*;

public class ClientThread extends Thread {

	private Socket socket;
	private boolean geheim = false;
	private static Object monitor = new Object();

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.println("Client-Thread gestartet");
		int x;

		try (InputStreamReader netIn = new InputStreamReader(socket.getInputStream(), "UTF-8");
				OutputStreamWriter netOut = new OutputStreamWriter(socket.getOutputStream(), "UTF-8")) {
			while ((x = netIn.read()) != -1) {
				char c = (char) x;
				System.out.println("Empfangen: " + c);
				if (c >= 'a' && c <= 'z') {
					dateiSenden(netOut, c);
				} else {
					if (c == '$') {
						passwortLesen(netIn, netOut);
					} else {
						if (c == '%') {
							geheim = false;
							netOut.write("Geheimmodus ausgeschaltet" + System.lineSeparator());
							netOut.flush();
						} else {
							if (c == '#') {
								passwortAendern(netIn, netOut);
							} else {
								netOut.write("Falsche Eingabe" + System.lineSeparator());
								netOut.flush();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ClientThread: " + e.getMessage());
		}
	}

	public void dateiSenden(OutputStreamWriter netOut, char buchstabe) throws IOException {
		int x;
		String name = "" + buchstabe + ".txt";
		System.out.println("Datei senden: " + name);
		URL url = getClass().getResource(name);
		try (InputStream is = new FileInputStream(url.getFile());
				InputStreamReader fileIn = new InputStreamReader(is, "UTF-8")) {
			if (geheim == true) {
				String text = "";
				while ((x = fileIn.read()) != -1) {
					char c = (char) x;
					if ((c >= 'a' && c <= 'z') || c >= 'A' && c <= 'Z') {
						text += c;
					} else {
						if (text.length() >= 1) {
							netOut.write(text.charAt(1)); // Nur den jeweils zweiten Buchstaben senden!
						}
						text = "";
					}
				}

			} else {
				while ((x = fileIn.read()) != -1) {
					netOut.write(x);
				}
			}
			netOut.write(System.lineSeparator());
			netOut.flush();
		} catch (Exception e) {
			netOut.write("Die Datei existiert nicht." + System.lineSeparator());
			netOut.flush();
		}
	}

	public void passwortLesen(InputStreamReader netIn, OutputStreamWriter netOut) throws Exception {
		String userPassword = "";
		int x;
		while ((char) (x = netIn.read()) != '$') {
			userPassword += (char) x;
		}
		String storedPassword = "";
		synchronized (monitor) {
			URL url = getClass().getResource("passwort.txt");
			try (InputStream is = new FileInputStream(url.getFile());
					InputStreamReader fileIn = new InputStreamReader(is, "UTF-8")) {
				while ((x = fileIn.read()) != -1) {
					storedPassword += (char) x;
				}
			} catch (Exception e) {
				System.out.println("passwortLesen(): Fehler: " + e.getMessage());
			}
		}
		if (storedPassword.equals(userPassword)) {
			geheim = true;
			netOut.write("Geheimmodus aktiviert" + System.lineSeparator());
		} else {
			netOut.write("Falsches Passwort" + System.lineSeparator());
		}
		netOut.flush();
	}

	public void passwortAendern(InputStreamReader netIn, OutputStreamWriter netOut) throws Exception {
		String userPassword = "";
		int x;
		while ((char) (x = netIn.read()) != '#') {
			userPassword += (char) x;
		}
		if (geheim == true) {
			synchronized (monitor) {
				URL url = getClass().getResource("passwort.txt");
				try (OutputStream os = new FileOutputStream(url.getFile());
						OutputStreamWriter fileOut = new OutputStreamWriter(os, "UTF-8")) {
					fileOut.write(userPassword);
					fileOut.flush();
				} catch (Exception e) {
					System.out.println("passwortAendern(): Fehler: " + e.getMessage());
				}
			}
			netOut.write("Password geändert" + System.lineSeparator());
		} else {
			netOut.write("Geheimmodus ist nicht aktiviert" + System.lineSeparator());
		}
		netOut.flush();
	}
}
