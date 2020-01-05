import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private final int PORT = 12345;

	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public Client(String IP) throws UnknownHostException, IOException {
		socket = new Socket(IP, PORT);
		dataInputStream = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		dataOutputStream = new DataOutputStream(new BufferedOutputStream(
				socket.getOutputStream()));
	}

	public void write(String text) {
		try {
			dataOutputStream.writeUTF(text);
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String read() {
		try {
			return dataInputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public TopPlayers getTopPleayers() {
		TopPlayers topPlayers = null;
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(dataInputStream);
			topPlayers = (TopPlayers) objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		if (objectInputStream != null)
//			try {
//				objectInputStream.close();
//			} catch (IOException e) {
//			}
		return topPlayers;
	}

	public File getLogo() throws IOException {
		write("#logo#");
		File logo = new File("res\\java_logo.jpg");
		DataOutputStream dataOutputStream = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(logo)));
		byte[] bytes = new byte[1024];
		do {
			dataInputStream.read(bytes);
			dataOutputStream.write(bytes);
		} while (dataInputStream.available() > 0);
		dataOutputStream.flush();
		dataOutputStream.close();
		return logo;
	}

}
