package elaboratolinguaggiecompilatori;


import javax.swing.JOptionPane;

/**
 * Mostra una finestra di dialogo che informa l'utente che 
 * deve selezionare un file rubrica valido
 */
public class MessaggioJFileChooser extends JOptionPane  {
		
	public MessaggioJFileChooser(short selettore) {
		if (selettore == 0) {
			String message = "Select a script with .wtf extension";
			String title = "Error";
			Object[] options = {"Ok"};
			 JOptionPane.showOptionDialog(null, message, title,JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,null, options, options[0]);
			
	}
                else if(selettore == 1){
                    String message = "Impossibile to open the file";
			String title = "Error";
			Object[] options = {"Ok"};
			 JOptionPane.showOptionDialog(null, message, title,JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,null, options, options[0]);
			
                }
                else if(selettore == 2){
                    String message = "Impossibile to open the file";
			String title = "Error";
			Object[] options = {"Ok"};
                    JOptionPane.showOptionDialog(null, message, title,JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,null, options, options[0]);
			
			
                }
}
}