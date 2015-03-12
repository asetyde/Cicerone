package elaboratolinguaggiecompilatori;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Mostra una finestra di dialogo che chiede all'utente di confermare
 * o meno l'uscita dal programma
 */
public class ExitDialog extends JOptionPane  {
	
	private JFrame exit;
	
	public ExitDialog() {
		String message = "Are you sure to exit?";
	    String title = "Exit";
	    int risposta = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	    if (risposta == JOptionPane.YES_OPTION)
	    	System.exit(0);
	    else if (risposta == JOptionPane.NO_OPTION){
                
            }
	}
}
