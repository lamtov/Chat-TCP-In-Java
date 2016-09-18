package panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.ChatApplication;

public class PanelSend extends JPanel {
	private JTextField textField;
	private JButton btnSend;
	private ChatApplication app;
	private boolean send;

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public PanelSend(ChatApplication app) {
		this.app = app;
		this.setBounds(0, 390, 434, 40);
		this.setFocusable(true);
		add();
	}

	private void add() {
		this.textField = new JTextField(31);
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				send = true;
			}
		});
		this.add(btnSend);
		this.textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				send = true;
			}
		});
		this.add(textField);

	}

	public String getText() {
		return textField.getText();
	}

	public void clear() {
		textField.setText("");
	}

}
