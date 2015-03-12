package elaboratolinguaggiecompilatori;

import java.util.Vector;

public class ErrorsList {

    private Vector<Error> errorsList;

    public ErrorsList() {
        errorsList = new Vector<Error>();
    }

    public Error getError(int index) {
        return errorsList.get(index);
    }

    public void addError(Error newError) {
        errorsList.add(newError);
    }

    public void removeAll() {
        errorsList.removeAllElements();
    }

    public int size() {
        return errorsList.size();
    }

    public void addList(ErrorsList errorsListToAdd) {
        for (int i = 0; i < errorsListToAdd.size(); i++) {
            errorsList.add(errorsListToAdd.getError(i));
        }
    }

    
    public ErrorsList extract() {
        int counter = 0;
        Error error;
        ErrorsList errorsListToExtract = new ErrorsList();
        boolean condition = true;
        while (condition && errorsList.size() > 0) {
            error = errorsList.get(counter);
            errorsListToExtract.addError(error);
            if (counter < errorsList.size() - 1) {
                if (error.row != errorsList.get(counter + 1).row) {
                    condition = false;
                }
            } else {
                condition = false;
            }
            counter++;
        }
        return errorsListToExtract;
    }
}
