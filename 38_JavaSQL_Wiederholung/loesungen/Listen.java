import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class Listen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	DefaultListModel<String> inhalte = new DefaultListModel<String>();
	JList<String> list = new JList<String>(inhalte);
	private JButton btnLoeschen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Listen frame = new Listen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Listen() {
		createGUI();
	}

	private void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 659, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 26, 337, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnHinzufgen = new JButton("Hinzufügen");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hinzufuegen();
			}
		});
		btnHinzufgen.setBounds(495, 25, 138, 23);
		contentPane.add(btnHinzufgen);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 623, 373);
		contentPane.add(scrollPane);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);

		btnLoeschen = new JButton("Löschen");
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loeschen();
			}
		});
		btnLoeschen.setBounds(495, 459, 138, 23);
		contentPane.add(btnLoeschen);
	}

	protected void loeschen() {
		int i = list.getSelectedIndex();
		if (i >= 0) {
			inhalte.remove(i);
		}
	}

	protected void hinzufuegen() {
		if (!textField.getText().isEmpty()) {
			String neuerEintrag = textField.getText();
			inhalte.addElement(neuerEintrag);
		}
	}
}
