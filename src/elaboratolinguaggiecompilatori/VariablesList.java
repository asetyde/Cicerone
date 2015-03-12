package elaboratolinguaggiecompilatori;

import java.util.Vector;

public class VariablesList {

    private Vector<Variable> variablesList;

    public VariablesList() {
        variablesList = new Vector<Variable>();
    }

    public void addVariable(Variable newVariable) {

        variablesList.add(newVariable);

    }

    public void remove(int index) {
        variablesList.remove(index);
    }

    public void removeAll() {
        variablesList.removeAllElements();
    }

    public int searchByName(String name) {
        for (int i = 0; i < variablesList.size(); i++) {
            if (variablesList.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return variablesList.size();
    }

    public Variable getVariable(int index) {
        return variablesList.get(index);
    }

    public void addList(VariablesList variablesListToAdd) {
        for (int i = 0; i < variablesListToAdd.size(); i++) {
            variablesList.add(variablesListToAdd.getVariable(i));
        }
    }
}
