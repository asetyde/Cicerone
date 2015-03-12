package elaboratolinguaggiecompilatori;

import java.util.Vector;

public class FunctionsList {

    private Vector<Function> functionsList;

    public FunctionsList() {
        functionsList = new Vector<Function>();
    }

    public int size() {
        return functionsList.size();
    }

    public int searchFunction(String name) {
        for (int i = 0; i < functionsList.size(); i++) {
            if (functionsList.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean addFunction(Function newFunction) {
        if (searchFunction(newFunction.name) != -1) {
            return false;
        }
        functionsList.add(newFunction);
        return true;
    }

    public void remove(int index) {
        functionsList.remove(index);
    }

    public void removeAll() {
        functionsList.removeAllElements();
    }

    public Function getFunc(int index) {
        return functionsList.get(index);
    }

    public void addList(FunctionsList functionsListToAdd) {
        for (int i = 0; i < functionsListToAdd.size(); i++) {
            functionsList.add(functionsListToAdd.getFunc(i));
        }
    }
}
