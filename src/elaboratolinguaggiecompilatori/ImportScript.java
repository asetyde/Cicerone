package elaboratolinguaggiecompilatori;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImportScript {
	
	private String contenutoFile = "";
	private boolean control = true;
	
	public ImportScript(String other) {
		try {
			InputStream scriptContent = new FileInputStream(other);
			//RIVERSO IL CONTENUTO DEL FILE SU STRINGACONTATTI
			int index=0;
			while (index != -1) {
				index = scriptContent.read();
				if (index != -1)
					contenutoFile += ((char) index); 
			}
		}
		catch (IOException y) { 
	    	control = false;
		}
	}
	
	public String getText() {
		return contenutoFile;
	}
	
	public boolean getControl() {
		return this.control;
	}

}
