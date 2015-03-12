package elaboratolinguaggiecompilatori;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser {

    private File selectedFile;
    private boolean exists = false;
    private boolean exists2 = false;

    public FileChooser(JFrame frame) {
        /*if (selettore == 0) {
         MessaggioJFileChooser messaggio = new MessaggioJFileChooser(selettore);
         }
         else if (selettore == 1) {
         MessaggioJFileChooser messaggio = new MessaggioJFileChooser(selettore);
         }*/

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new TxtFilter());
        int selezione = fileChooser.showOpenDialog(frame);

        if (selezione == JFileChooser.APPROVE_OPTION) {
            this.selectedFile = fileChooser.getSelectedFile();
            exists = true;
        }
        if (selezione == JFileChooser.CANCEL_OPTION) {

            fileChooser.getSelectedFile();
            selectedFile = null;
        }

    }

    public FileChooser(JFrame frame, String Content) {
        JFileChooser fileChooser = new JFileChooser();
        /* File selectedDirectory = fileChooser.getCurrentDirectory();
         System.out.println(selectedDirectory.toString());*/

        fileChooser.setDialogTitle("Select directory to save");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setApproveButtonText("Save");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new SaveFilter());
        int selezione = fileChooser.showOpenDialog(null);

        if (selezione == JFileChooser.APPROVE_OPTION) {
            this.selectedFile = fileChooser.getSelectedFile();
            System.out.println(fileChooser.getSelectedFile());
        }
        if (selezione == JFileChooser.CANCEL_OPTION) {

            fileChooser.getSelectedFile();
            selectedFile = null;
        }

    }

    public boolean getExists() {
        return exists;
    }

    public File getSelectedFile() {
        return this.selectedFile;
    }

    public boolean getExists2() {
        return exists2;
    }

}
