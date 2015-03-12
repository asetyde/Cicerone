package elaboratolinguaggiecompilatori;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TxtFilter extends FileFilter {
	  
	public boolean accept(File file) {
		return file.isDirectory() || file.getName().toLowerCase().endsWith(".wtf");
		}
	
	public String getDescription() {
		return "file di testo *.wtf";
	}
}
