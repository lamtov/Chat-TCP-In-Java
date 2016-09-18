package panel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.ChatApplication;

public class PanelSetting extends JPanel {

	private ChatApplication app;
	private JTextField tfYourIp, tfYourPort;
	private JTextField tfFriendIP, tfFriendPort;

	public PanelSetting(ChatApplication app) {
		this.app = app;
		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(0, 300, 420, 80);
		this.setFocusable(true);
		// this.setBackground(Color.GREEN);
		this.tfYourIp = new JTextField(10);
		this.tfYourPort = new JTextField(10);
		this.tfFriendIP = new JTextField(10);
		tfFriendIP.setText("localhost");
		this.tfFriendPort = new JTextField(10);
		this.setLayout(new GridLayout(2, 2, 0, 10));
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(new JLabel("     Your IP : "));
		p1.add(tfYourIp);
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(new JLabel("     Your Port : "));
		p2.add(tfYourPort);
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.add(new JLabel("  Friend IP : "));
		p3.add(tfFriendIP);
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.add(new JLabel("  Friend Port : "));
		p4.add(tfFriendPort);
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
	}

	public String geYourIp() {
		return tfYourIp.getText();
	}

	public int getYourPort() {
		if (!tfYourPort.getText().equals("")) {
			return Integer.valueOf(tfYourPort.getText());
		} else {
			return 0;
		}
	}

	public String getFriendIP() {
		return tfFriendIP.getText();
	}

	public int getFriendPort() {
		if (!tfFriendPort.getText().equals("")) {
			return Integer.valueOf(tfFriendPort.getText());
		} else {
			return 0;
		}
	}
}
