package panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import main.ChatApplication;

public class TextPanel extends JPanel {

	private JTextPane textPanel;

	public TextPanel() {
		this.setFocusable(true);
		this.setBounds(0, 0, 435, 250);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		textPanel = new JTextPane();

		textPanel.setBackground(Color.WHITE);
		textPanel.setBorder(new LineBorder(Color.BLACK));
		textPanel.setCaretColor(Color.BLACK);
		textPanel.setSelectedTextColor(Color.YELLOW);

		setText("           ============****==============");
		insertText("           =  ");
		appendToPane("Chat Client -- Sever     ", Color.BLUE);
		insertText(" =  ");
		appendToPane("\n" + "           =============***==============" + "\n", Color.BLACK);
		textPanel.setAutoscrolls(true);
		textPanel.setCaretPosition(textPanel.getDocument().getLength());
		JScrollPane scrollPane = new JScrollPane(textPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane);
		scrollPane.setAutoscrolls(true);

	}

	public void setText(String string) {
		textPanel.setText("");
		String string2 = string + "\n";
		appendToPane(string2, Color.BLACK);
	}

	public void appendAlert(String string) {
		appendToPane(string + "\n", Color.RED);
	}

	public void insertText(String string) {
		appendToPane(string, Color.BLACK);
	}

	public void appendText(String string) {
		appendToPane(string + "\n", Color.BLACK);
	}

	public void appendYourMessage(String string) {
		appendToPane("You : ", Color.BLUE);
		appendToPane(string + "\n", Color.PINK);
	}

	public void appendYourFriendMessage(String string) {
		appendToPane("Friend : ", Color.BLUE);
		appendToPane(string + "\n", Color.GREEN);
	}

	public void appendToPane(String string, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = textPanel.getDocument().getLength();
		textPanel.setCaretPosition(len);
		textPanel.setCharacterAttributes(aset, false);
		textPanel.replaceSelection(string);
	}

	public void clear() {
		textPanel.setText("");
	}
}
