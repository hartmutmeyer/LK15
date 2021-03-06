import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.sql.*;

public class TermineHeute extends JFrame {

	private JPanel contentPane;
	private List terminListe;

	private String heute;
	public TermineHeute(final String title) {
		super(title);
		createGUI();
		

		// heute = "2011-11-21";
		java.sql.Date datumHeute = new java.sql.Date(System.currentTimeMillis());
		heute = datumHeute.toString();

		datenbankAlteTermineLoeschen();
		datenbankTermineHeuteAbfragen();
	}

	public void datenbankAlteTermineLoeschen() {
		int ergebnis = 0;
		String cmdSQL;

		cmdSQL = "DELETE FROM termin WHERE datum<'" + heute + "'";

		try (
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/terminplaner", "root", "root");
			Statement stmt = conn.createStatement();
		) {
			System.out.println(cmdSQL);
			ergebnis = stmt.executeUpdate(cmdSQL);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Fehler beim Löschen der alten Termine",
					JOptionPane.ERROR_MESSAGE);
		}
		if (ergebnis > 0) {
			System.out.println("Es wurden " + ergebnis
					+ " alte Termine gelöscht.");
		} else {
			System.out.println("Es wurden keine alten Termine gelöscht.");
		}
	}

	public void datenbankTermineHeuteAbfragen() {
		ResultSet rs = null;
		String cmdSQL = "SELECT * FROM termin WHERE datum='" + heute
				+ "' ORDER BY zeit ASC";

		try (
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/terminplaner", "root", "root");
			Statement stmt = conn.createStatement();
		) {
			System.out.println(cmdSQL);
			rs = stmt.executeQuery(cmdSQL);
			while (rs.next()) {
				terminListe.add(rs.getString("zeit") + " "
						+ rs.getString("text"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Fehler beim Auslesen der Termine",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTermineHeute = new JLabel("Termine heute:");
		lblTermineHeute.setBounds(12, 12, 98, 15);
		contentPane.add(lblTermineHeute);

		terminListe = new List();
		lblTermineHeute.setLabelFor(terminListe);
		terminListe.setBounds(12, 33, 422, 230);
		contentPane.add(terminListe);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TermineHeute frame = new TermineHeute("Termine für heute anzeigen");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
