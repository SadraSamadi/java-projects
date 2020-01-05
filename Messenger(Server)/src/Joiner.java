import java.io.*;
import java.net.*;

public class Joiner extends Thread {

	private String username;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	public Joiner(Socket socket, String username) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		printWriter = new PrintWriter(socket.getOutputStream());
		this.username = username;
		start();
	}

	public void send(String text) {
		printWriter.println(text);
		printWriter.flush();
	}

	public String receive() throws IOException {
		return bufferedReader.readLine();
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String temp = receive();
				if (temp.equals("exit")) {
					Server.removeJoiner(username);
					break;
				} else {
					Server.sentTo(username + ": " + temp, username);
				}
			} catch (IOException e) {
				Server.textArea.append(e.toString());
			}
		}
	}

}