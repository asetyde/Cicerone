package elaboratolinguaggiecompilatori;

public class Function {

    public VariablesList variablesList;

    public CommandsList commandsList;
    public String name;
    public Executor exe;

    public Function(String name, CommandsList commandsList) {

        this.variablesList = new VariablesList();
        this.commandsList = commandsList;
        this.name = name;

    }

    public Function(String name) {
        this.name = name;
        this.variablesList = new VariablesList();
    }

    public void addList(VariablesList globalVariablesList) {
        variablesList.addList(globalVariablesList);
    }

}
