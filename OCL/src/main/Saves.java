package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Saves {

	private final static String root = System.getProperty("user.home") + "\\Documents\\OnChapLine\\";

	public static void WriteLogin(String username, String password) {
		File folder = new File(root);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(root + "login.dat");
		try {
			file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bufferedWriter.write(username + "\n" + password);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] ReadLogin() {
		File file = new File(root + "login.dat");
		if (!file.exists()) {
			return null;
		}
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String[] tmp = { bufferedReader.readLine(), bufferedReader.readLine() };
			bufferedReader.close();
			return tmp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void WriteLocation(String location) {
		File folder = new File(root);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(root + "location.dat");
		try {
			file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bufferedWriter.write(location);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String ReadLocation() {
		File file = new File(root + "location.dat");
		if (!file.exists()) {
			return null;
		}
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String tmp = bufferedReader.readLine();
			bufferedReader.close();
			return tmp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteLocation() {
		File file = new File(root + "location.dat");
		if (file.exists()) {
			file.delete();
		}
	}

	public static void deleteLogin() {
		File file = new File(root + "login.dat");
		if (file.exists()) {
			file.delete();
		}
	}

}
