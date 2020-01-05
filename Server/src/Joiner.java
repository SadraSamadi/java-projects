import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Joiner extends Thread {

	private final String username;
	private int score;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private boolean hasScore = false;

	public Joiner(Socket socket, String username,
			DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
		this.username = username;
		this.dataInputStream = dataInputStream;
		this.dataOutputStream = dataOutputStream;
		start();
	}

	public int getScore() {
		return score;
	}

	public boolean hasScore() {
		return hasScore;
	}

	public String getUsername() {
		return username;
	}

	public String read() {
		try {
			return dataInputStream.readUTF();
		} catch (IOException e) {
			Server.log("Error: Server couldn't read from \"" + username
					+ "\" !!!");
			return "";
		}
	}

	public void write(String text) {
		try {
			dataOutputStream.writeUTF(text);
			dataOutputStream.flush();
		} catch (IOException e) {
			Server.log("Error: Server couldn't write to \"" + username
					+ "\" !!!");
		}
	}

	public void sendLogo() {
		try {
			Server.log("Sending logo to \"" + username + "\" ...");
			byte[] bytes = new byte[1024];
			DataInputStream dataInputStream = new DataInputStream(
					new BufferedInputStream(new FileInputStream(Server.logo)));
			while (dataInputStream.read(bytes) != -1)
				dataOutputStream.write(bytes);
			dataOutputStream.flush();
			dataInputStream.close();
			Server.log("Logo sent to \"" + username + "\" ...");
		} catch (IOException e) {
			Server.log("Error: Server couldn't send logo to \"" + username
					+ "\" !!!");
		}
	}

	private void list() {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(dataOutputStream);
			objectOutputStream.writeObject(Server.getTopPlayers());
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		label: while (true) {
			String input = read();
			switch (input) {
			case "#logo#":
				sendLogo();
				break;
			case "#list#":
				score = Integer.parseInt(read());
				hasScore = true;
				list();
				break;
			case "#exit#":
				Server.remove(username);
				break label;
			default:
				break;
			}
		}
	}

}
