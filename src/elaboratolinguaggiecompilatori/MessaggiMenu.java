package elaboratolinguaggiecompilatori;

import javax.swing.JOptionPane;

public class MessaggiMenu extends JOptionPane {
	
	short selettore;
	
	public MessaggiMenu(short sel) {
		selettore = sel;
		if (selettore == 0) {
			String message = "Contatto aggiunto correttamente";
			String title = "Aggiunto";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (selettore == 1) {
			String message = "Specificare sempre almeno nome e cognome";
			String title = "Non aggiunto";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		}
		else if (selettore == 2) {
			String message = "Contatto modificato correttamente";
			String title = "Modificato";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (selettore == 3) {
			String message = "Specificare sempre almeno nome e cognome";
			String title = "Non modificato";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		}
		else if (selettore == 4) {
			String message = "Contatto/i eliminato/i correttamente";
			String title = "Eliminato/i";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (selettore == 10) {
			String message = "Nessun contatto selezionato";
			String title = "Attenzione";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
		}
	}

}
