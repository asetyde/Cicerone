package elaboratolinguaggiecompilatori;

public class Command {

    public String command;


    public CommandsList commandsList;

    public Command(String command, boolean control) {
        this.command = command;
        if(control) {
            this.commandsList = new CommandsList();
        }
        
    }
    
    

    public Command(String command, CommandsList commandsList) {
        this.command = command;
        this.commandsList = commandsList;
    }

}
