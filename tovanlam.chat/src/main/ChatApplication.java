package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.CodeSource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

import panel.PanelSend;
import panel.PanelSetting;
import panel.RadioPanel;
import panel.TextPanel;

public class ChatApplication extends JFrame {

	private PanelSend panelSend;
	private PanelSetting panelSetting;
	private RadioPanel rdPanel;
	private TextPanel textPanel;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private DatagramSocket serverUDP;
	private DatagramSocket clientUDP;
	private PrintWriter pw;
	private BufferedReader bf;
	private InetAddress ipClientUDP;
	private int portClientUDP;
	private boolean uDPWasSend;
	private Socket connectionSocket;

	public ChatApplication() {
		this.setTitle("application.chat");
		this.setSize(430, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		addCompo();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

	private void addCompo() {

		this.textPanel = new TextPanel();
		this.add(textPanel);

		this.panelSetting = new PanelSetting(this);
		this.add(panelSetting);
		this.rdPanel = new RadioPanel();
		this.add(rdPanel);

		this.panelSend = new PanelSend(this);
		this.add(panelSend);

	}

	public static void main(String[] args) {

		ChatApplication app = new ChatApplication();
		Timer timer = new Timer(1000 / 10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!app.rdPanel.isServer()) {
					if (app.serverSocket != null) {
						try {
							app.serverSocket.close();
							app.serverSocket = null;
							app.connectionSocket = null;
							app.textPanel.appendAlert("Da dong server!");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (app.serverUDP != null) {
						app.serverUDP.close();
						app.serverUDP = null;
						app.ipClientUDP = null;
						app.portClientUDP = 0;
						app.textPanel.appendAlert("Da dong server!");
					}
				}
				if (!app.rdPanel.isStart()) {
					if (app.clientSocket != null) {
						try {
							app.clientSocket.close();
							app.clientSocket = null;
							app.textPanel.appendAlert("Da huy client!");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (app.clientUDP != null) {
						app.clientUDP.close();
						app.textPanel.appendAlert("Da huy client!");
						app.clientUDP = null;
					}
				}

			}
		});
		timer.start();
		while (true) {
			if (app.rdPanel.isStart()) {

				if (app.rdPanel.isServer()) {
					if (app.rdPanel.isTCP()) {
						app.tcpStartServer();
					} else if (app.rdPanel.isUDP()) {
						app.udpStartServer();

					}
				} else {
					if (app.rdPanel.isTCP()) {
						app.tcpStartClient();
					} else if (app.rdPanel.isUDP()) {
						app.udpStartClient();

					}

				}
			} else {
				System.out.print(" ");
			}
		}
	}

	public void udpStartServer() {
		if (serverUDP == null) {
			if (this.getPanelSetting().getYourPort() == 0) {
				this.getTextPanel().appendAlert("Please Enter Port !");
				this.getRdPanel().getRadioServer().setSelected(false);
				this.getRdPanel().getRadioStart().setSelected(false);
			} else {
				try {
					serverUDP = new DatagramSocket(this.getPanelSetting().getYourPort());
					this.getTextPanel().appendText("Da khoi tao server ");
				} catch (SocketException e) {
					this.getTextPanel().appendAlert(e.getMessage());
					this.getRdPanel().getRadioServer().setSelected(false);
					this.getRdPanel().getRadioStart().setSelected(false);
				}
			}
		}
		if (serverUDP != null) {
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			if (!uDPWasSend) {
				try {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					this.serverUDP.receive(receivePacket);
					if (ipClientUDP == null) {
						ipClientUDP = receivePacket.getAddress();
					}
					if (portClientUDP == 0) {
						portClientUDP = receivePacket.getPort();
					}
					String sentence = new String(receivePacket.getData());
					this.getTextPanel().appendYourFriendMessage(sentence);
					uDPWasSend = true;
				} catch (IOException e) {
					e.printStackTrace();
					this.getTextPanel().appendAlert(e.getMessage());
				}
			}
			if (this.getPanelSend().isSend()) {
				try {
					this.getTextPanel().appendYourMessage(this.getPanelSend().getText());
					sendData = this.getPanelSend().getText().getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipClientUDP,
							portClientUDP);
					serverUDP.send(sendPacket);
					this.getPanelSend().setSend(false);
					this.getPanelSend().clear();
					uDPWasSend = false;
				} catch (IOException e) {
					e.printStackTrace();
					this.getTextPanel().appendAlert(e.getMessage());
				}

			}
		}
	}

	public void udpStartClient() {
		if (clientUDP == null) {
			try {
				clientUDP = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}

		}
		if (clientUDP != null) {

			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			if (this.getPanelSend().isSend()) {

				try {
					InetAddress ip = InetAddress.getByName(this.getPanelSetting().getFriendIP());
					sendData = this.getPanelSend().getText().getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip,
							this.getPanelSetting().getFriendPort());
					clientUDP.send(sendPacket);
					uDPWasSend = true;

				} catch (UnknownHostException e) {
					e.printStackTrace();
					this.getTextPanel().appendAlert(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					this.getTextPanel().appendAlert(e.getMessage());
				}
				this.getTextPanel().appendYourMessage(this.getPanelSend().getText());
				this.getPanelSend().setSend(false);
				this.getPanelSend().clear();
			}

			if (uDPWasSend == true) {
				try {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientUDP.receive(receivePacket);
					String modifiedSentence = new String(receivePacket.getData());
					this.getTextPanel().appendYourFriendMessage(modifiedSentence);
					uDPWasSend = false;
				} catch (IOException e) {
					this.getTextPanel().appendAlert(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public void tcpStartServer() {
		if (serverSocket == null) {
			System.out.println("NULL");
			if (this.getPanelSetting().getYourPort() == 0) {
				this.getTextPanel().appendAlert("Please Enter Port !");
				this.getRdPanel().getRadioServer().setSelected(false);
				this.getRdPanel().getRadioStart().setSelected(false);
			} else {
				try {
					serverSocket = new ServerSocket(this.getPanelSetting().getYourPort());
					this.getTextPanel().appendText("Da khoi tao server ");
				} catch (IOException e) {
					this.getTextPanel().appendAlert(e.getMessage());
					this.getRdPanel().getRadioServer().setSelected(false);
					this.getRdPanel().getRadioStart().setSelected(false);
				}
			}

		}
		if (serverSocket != null) {
			try {
				if (this.connectionSocket == null) {
					connectionSocket = serverSocket.accept();
					this.getTextPanel().appendText("Da ket noi toi client");
				}
				pw = new PrintWriter(connectionSocket.getOutputStream());
				if (this.getPanelSend().isSend()) {
					pw.println(this.getPanelSend().getText());
					this.getTextPanel().appendYourMessage(this.getPanelSend().getText());
					pw.flush();
					this.getPanelSend().setSend(false);
					this.getPanelSend().clear();
				}
				bf = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				if (bf.ready()) {
					String inputLine;
					while ((inputLine = bf.readLine()) != null) {
						System.out.println("inputLine = " + inputLine);
						this.getTextPanel().appendYourFriendMessage(inputLine);
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				this.getTextPanel().appendAlert(e.getMessage());
			}
		}
	}

	public void tcpStartClient() {
		if (clientSocket == null) {
			try {
				clientSocket = new Socket(this.getPanelSetting().getFriendIP(), this.getPanelSetting().getFriendPort());
				this.getTextPanel().appendText("Da ket noi toi server " + this.getPanelSetting().getFriendPort());
			} catch (IOException e) {
				this.getTextPanel().appendAlert(e.getMessage());
				this.getRdPanel().getRadioStart().setSelected(false);
			}
		}
		if (clientSocket != null) {
			try {
				pw = new PrintWriter(clientSocket.getOutputStream());
				bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				if (this.getPanelSend().isSend()) {
					pw.println(this.getPanelSend().getText());
					this.getTextPanel().appendYourMessage(this.getPanelSend().getText());
					pw.flush();
					this.getPanelSend().setSend(false);
					this.getPanelSend().clear();
				}

				if (bf.ready()) {
					String inputLine;
					while ((inputLine = bf.readLine()) != null) {
						System.out.println("inputLine = " + inputLine);
						this.getTextPanel().appendYourFriendMessage(inputLine);
						break;
					}
				}

			} catch (IOException e) {
				this.getTextPanel().appendAlert(e.getMessage());
			}
		}
	}

	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = ChatApplication.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static String getCurrentDirectory() {
		String path = null;
		CodeSource codeSource = ChatApplication.class.getProtectionDomain().getCodeSource();
		try {
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			path = jarFile.getParentFile().getPath();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return path;
	}

	public PanelSend getPanelSend() {
		return panelSend;
	}

	public PanelSetting getPanelSetting() {
		return panelSetting;
	}

	public RadioPanel getRdPanel() {
		return rdPanel;
	}

	public TextPanel getTextPanel() {
		return textPanel;
	}

}
