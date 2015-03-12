package elaboratolinguaggiecompilatori;

import java.util.Vector;

public class CommandsList {

    private Vector<Command> commandsList;

    public CommandsList() {
        commandsList = new Vector<Command>();
    }

    public void addCommand(Command newCommand) {
        commandsList.add(newCommand);
    }

    public void remove(int index) {
        commandsList.remove(index);
    }

    public void removeAll() {
        commandsList.removeAllElements();
    }

    public Command getCommand(int index) {
        return commandsList.get(index);
    }

    public int size() {
        return commandsList.size();
    }

    public CommandsList clone() {
        CommandsList returnCommandsList = new CommandsList();

        for (int i = 0; i < commandsList.size(); i++) {
            returnCommandsList.addCommand(commandsList.get(i));
        }
        return returnCommandsList;
    }
}
