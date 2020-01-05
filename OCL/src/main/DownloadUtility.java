package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBox;

public class DownloadUtility extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JTextField txtDirectory;

	private String link;
	private int size;
	private String name;
	private HttpURLConnection conn;

	private JLabel lblName;
	private JLabel lblSize;
	private JButton btnStart;
	private JLabel lblState;
	private JButton btnBrowse;
	private JPanel panel_2;
	private JPanel panel_3;
	private JCheckBox chckbxRememberLocation;
	private boolean flag = false;

	public static void main(String[] args) {
		// new DownloadUtility(
		// "http://tinyz.co/dl/m/Mad.Max.Fury.Road.2015.Dubbed.Audio.TinyMoviez_co.mp3?hash=562b0f936723d2095a0e8e580c2e27b3_74214_30162_3");
		new DownloadUtility(
				"http://onchapline.ir/files/getFile.php?username=reza&password=testPass&sub_Id=31&orderp_Id=27");
	}

	public DownloadUtility(String link) {
		setResizable(false);
		setTitle("Save File");
		this.link = link;
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(500, 150));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 10, 10));

		lblName = new JLabel("Name: ???");
		panel.add(lblName);

		panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		txtDirectory = new JTextField();
		panel_3.add(txtDirectory);
		txtDirectory.setEnabled(false);
		txtDirectory.setColumns(10);

		JLabel lblLocation = new JLabel("Location : ");
		panel_3.add(lblLocation, BorderLayout.WEST);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		panel.add(progressBar);

		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		lblState = new JLabel("Please wait ...");
		panel_2.add(lblState, BorderLayout.EAST);
		lblState.setForeground(Color.RED);

		chckbxRememberLocation = new JCheckBox("Remember Location ?");
		panel_2.add(chckbxRememberLocation, BorderLayout.WEST);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(120, 10));
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(4, 1, 10, 10));

		lblSize = new JLabel("Size: ???");
		panel_1.add(lblSize);

		btnBrowse = new JButton("Browse");
		btnBrowse.setEnabled(false);
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(new JFrame(), "Save", FileDialog.SAVE);
				fd.setFile(name);
				fd.setVisible(true);
				String directory = fd.getDirectory();
				if (directory != null) {
					txtDirectory.setText(directory + name);
				}
			}

		});
		panel_1.add(btnBrowse);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}

		});
		btnStart.setEnabled(false);
		panel_1.add(btnStart);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});
		panel_1.add(btnCancel);

		String location = Saves.ReadLocation();
		if (location != null) {
			txtDirectory.setText(location);
			chckbxRememberLocation.setSelected(true);
			flag = true;
		}

		prepare();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void prepare() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(link);
					conn = (HttpURLConnection) url.openConnection();
					int response = conn.getResponseCode();
					if (response == HttpURLConnection.HTTP_OK) {
						String disposition = conn.getHeaderField("Content-Disposition");

						if (disposition == null) {
							throw new IOException();
						}

						size = conn.getContentLength();
						name = disposition.substring(disposition.indexOf("filename=") + 9, disposition.length());

						if (flag) {
							txtDirectory.setText(txtDirectory.getText() + name);
						}

						lblName.setText("Name: " + name);
						lblSize.setText("Size: " + String.format("%.1f", (float) size / 1024) + " KB");

						progressBar.setStringPainted(true);
						progressBar.setMaximum(size);
						lblState.setText("Ready");

						txtDirectory.setEnabled(true);
						btnBrowse.setEnabled(true);
						btnStart.setEnabled(true);
					} else {
						lblState.setText("File not found !");
					}
				} catch (MalformedURLException e) {
					lblState.setText("File not found !");
				} catch (IOException e) {
					lblState.setText("File not found !");
				}
				progressBar.setIndeterminate(false);
			}
		});
		thread.start();
	}

	private void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (txtDirectory.getText().equals("")) {
					lblState.setText("Please enter location ...");
				} else {
					btnStart.setEnabled(false);
					File file = new File(txtDirectory.getText());
					try {
						file.createNewFile();
						if (chckbxRememberLocation.isSelected()) {
							String location = file.getAbsolutePath().substring(0,
									file.getAbsolutePath().lastIndexOf("\\") + 1);
							Saves.WriteLocation(location);
						} else {
							Saves.deleteLocation();
						}
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						InputStream inputStream = conn.getInputStream();
						int bytesRead = -1;
						byte[] buffer = new byte[1024];
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							fileOutputStream.write(buffer);
							fileOutputStream.flush();
							progressBar.setValue(progressBar.getValue() + bytesRead);
						}
						fileOutputStream.close();
						dispose();
					} catch (IOException e1) {
						lblState.setText("Error !");
					}
					btnStart.setEnabled(true);
				}
			}
		}).start();
	}
}
