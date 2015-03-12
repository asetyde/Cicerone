/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaboratolinguaggiecompilatori;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author alberto
 */
public class SaveFilter extends FileFilter {
	  
	public boolean accept(File file) {
		return file.isDirectory();
		}
	
	public String getDescription() {
		return "All";
	}


}
