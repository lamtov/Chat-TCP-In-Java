package panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioPanel extends JPanel {

	private JRadioButton radioTCP, radioUDP, radioEncryption, radioStart, radioServer;

	public RadioPanel() {
		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(55, 260, 342, 30);
		this.setLayout(new GridLayout(1, 3));
		this.setFocusable(true);
		//this.setBackground(Color.CYAN);
		addRadioButton();
	}

	public void addRadioButton() {
		radioTCP = new JRadioButton("TCP");
		radioTCP.setSelected(true);

		radioUDP = new JRadioButton("UDP");
		//radioUDP.setSelected(true);
		radioEncryption = new JRadioButton("Encrypt");

		radioStart = new JRadioButton("Start ");
		radioServer = new JRadioButton("Server ");
		radioServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!radioServer.isSelected()){
					radioStart.setSelected(false);
				}
				
			}
		});
		this.add(radioServer);
		this.add(radioStart);
		this.add(radioTCP);
		this.add(radioUDP);
		//this.add(radioEncryption);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioTCP);
		buttonGroup.add(radioUDP);
	}

	public JRadioButton getRadioTCP() {
		return radioTCP;
	}

	public JRadioButton getRadioUDP() {
		return radioUDP;
	}

	public JRadioButton getRadioEncryption() {
		return radioEncryption;
	}

	public JRadioButton getRadioStart() {
		return radioStart;
	}

	public JRadioButton getRadioServer() {
		return radioServer;
	}

	public boolean isStart() {
		return radioStart.isSelected();
	}

	public boolean isServer() {
		return radioServer.isSelected();
	}

	public boolean isTCP() {
		return radioTCP.isSelected();
	}

	public boolean isUDP() {
		return radioUDP.isSelected();
	}

	public boolean isEncryp() {
		return radioEncryption.isSelected();
	}


}
