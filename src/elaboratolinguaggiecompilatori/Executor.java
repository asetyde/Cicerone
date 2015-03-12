package elaboratolinguaggiecompilatori;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Executor {

    private ErrorsList errorsList;
    private FunctionsList functionsList;
    private CommandsList commandsList;
    private VariablesList variablesList;
    private String text = "";
    public boolean debugMode = false;
    public int counterRow = 0;

    private javax.swing.JTextArea console;
    private String oldText;

    public Executor(VariablesList variablesList, FunctionsList functionsList) {
        this.variablesList = variablesList;
        this.functionsList = functionsList;
        errorsList = new ErrorsList();
    }

    public void setCommands(CommandsList commandsList) {
        this.commandsList = commandsList;
    }

    public void setConsoles(javax.swing.JTextArea console) {
        this.console = console;
    }

    public void setOldText(String oldText) {
        this.oldText = oldText;
    }

    public boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int IfCondition(String format, VariablesList variablesList) {
        String condition[] = format.split("_");
        int index;
        boolean type0 = isNumeric(condition[0]);
        boolean type1 = isNumeric(condition[2]);

        if (type0 || type1) { //Control numeric type
            if (type0 && type1) {
                switch (condition[1]) {

                    case "==":
                        if (condition[0] == condition[2]) {
                            System.out.println("fnsn ==");
                            return 1;
                        } else {
                            System.out.println("fnsn !==");
                            return 0;
                        }
                    case "isequals":
                        if (condition[0].equals(condition[2])) {
                            System.out.println("fnsn isequals");
                            return 1;
                        } else {
                            System.out.println("fnsn !isequals");
                            return 0;
                        }
                    case "!=":
                        if (!condition[0].equals(condition[2])) {
                            System.out.println("fnsn !=");
                            return 1;
                        } else {
                            System.out.println("fnsn !!=");
                            return 0;
                        }
                    case "isnotequals":
                        if (!condition[0].equals(condition[2])) {
                            System.out.println("fnsn isnotequals");
                            return 1;
                        } else {
                            System.out.println("fnsn !isnotequals");
                            return 0;
                        }
                    case ">":
                        if (Double.parseDouble(condition[0]) > Double.parseDouble(condition[2])) {
                            System.out.println("fnsn >");
                            return 1;
                        } else {
                            System.out.println("fnsn !>");
                            return 0;
                        }
                    case ">=":
                        if (Double.parseDouble(condition[0]) >= Double.parseDouble(condition[2])) {
                            System.out.println("fnsn >=");
                            return 1;
                        } else {
                            System.out.println("fnsn !>=");
                            return 0;
                        }
                    case "<":
                        if (Double.parseDouble(condition[0]) < Double.parseDouble(condition[2])) {
                            System.out.println("fnsn <");
                            return 1;
                        } else {
                            System.out.println("fnsn !<");
                            return 0;
                        }
                    case "<=":
                        if (Double.parseDouble(condition[0]) <= Double.parseDouble(condition[2])) {
                            System.out.println("fnsn <=");
                            return 1;
                        } else {
                            System.out.println("fnsn !<=");
                            return 0;
                        }

                }
            }//end fnsn
            else {
                if (type0) {//first numeric second string
                    index = variablesList.searchByName(condition[2]);
                    if (index != -1) {
                        String result = variablesList.getVariable(index).value;
                        switch (condition[1]) {
                            case "==":
                                if (condition[0] == result) {
                                    System.out.println("fnsv ==");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !==");
                                    return 0;
                                }
                            case "isequals":
                                if (condition[0].equals(result)) {
                                    System.out.println("fnsv isequals");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !isequals");
                                    return 0;
                                }
                            case "isnotequals":
                                if (!condition[0].equals(result)) {
                                    System.out.println("fnsv isnotequals");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !isnotequals");
                                    return 0;
                                }
                            case "!=":
                                if (condition[0] != result) {
                                    System.out.println("fnsv !=");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !!=");
                                    return 0;
                                }
                            case ">":
                                if (Double.parseDouble(condition[0]) > Double.parseDouble(result)) {
                                    System.out.println("fnsv >");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !>");
                                    return 0;
                                }
                            case ">=":
                                if (Double.parseDouble(condition[0]) >= Double.parseDouble(result)) {
                                    System.out.println("fnsv >=");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !>=");
                                    return 0;
                                }
                            case "<":
                                if (Double.parseDouble(condition[0]) < Double.parseDouble(result)) {
                                    System.out.println("fnsv <");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !<");
                                    return 0;
                                }
                            case "<=":
                                if (Double.parseDouble(condition[0]) <= Double.parseDouble(result)) {
                                    System.out.println("fnsv <=");
                                    return 1;
                                } else {
                                    System.out.println("fnsv !<=");
                                    return 0;
                                }

                        }
                    }
                }//end fnss
                else {//fist string and second numeric
                    index = variablesList.searchByName(condition[0]);
                    if (index != -1) {
                        String result = variablesList.getVariable(index).value;
                        switch (condition[1]) {
                            case "==":
                                if (result.equals(condition[2])) {
                                    System.out.println("fvsn ==");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !==");
                                    return 0;
                                }
                            case "isequals":
                                if (result.equals(condition[2])) {
                                    System.out.println("fvsn isequals");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !isequals");
                                    return 0;
                                }
                            case "isnotequals":
                                if (!result.equals(condition[2])) {
                                    System.out.println("fvsn isnotequals");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !isnotequals");
                                    return 0;
                                }
                            case "!=":
                                if (!result.equals(condition[2])) {
                                    System.out.println("fvsn !=");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !!=");
                                    return 0;
                                }
                            case ">":
                                if (Double.parseDouble(result) > Double.parseDouble(condition[2])) {
                                    System.out.println("fvsn >");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !>");
                                    return 0;
                                }
                            case ">=":
                                if (Double.parseDouble(result) >= Double.parseDouble(condition[2])) {
                                    System.out.println("fvsn >=");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !>=");
                                    return 0;
                                }
                            case "<":
                                if (Double.parseDouble(result) < Double.parseDouble(condition[2])) {
                                    System.out.println("fvsn <");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !<");
                                    return 0;
                                }
                            case "<=":
                                if (Double.parseDouble(result) <= Double.parseDouble(condition[2])) {
                                    System.out.println("fvsn <=");
                                    return 1;
                                } else {
                                    System.out.println("fvsn !<=");
                                    return 0;
                                }

                        }
                    }
                }//end fssn
            }
        } //End of if(type0 || type1)
        else {//both of value are string
            int indexOfValue0 = variablesList.searchByName(condition[0]);
            int indexOfValue1 = variablesList.searchByName(condition[2]);
            if (((indexOfValue0 != -1) || (indexOfValue1 != -1))) {
                if (((indexOfValue0 != -1) && (indexOfValue1 != -1))) {
                    String value0 = variablesList.getVariable(indexOfValue0).value;
                    String value1 = variablesList.getVariable(indexOfValue1).value;
                    switch (condition[1]) {
                        case "==":
                            if (value0.equals(value1)) {
                                System.out.println("fvsv ==");
                                return 1;
                            } else {
                                System.out.println("fvsv !==");
                                return 0;
                            }
                        case "isequals":
                            if (value0.equals(value1)) {
                                System.out.println("fvsv isequals");
                                return 1;
                            } else {
                                System.out.println("fvsv !isequals");
                                return 0;
                            }
                        case "!=":
                            if (!value0.equals(value1)) {
                                System.out.println("fvsv !=");
                                return 1;
                            } else {
                                System.out.println("fvsv !!=");
                                return 0;
                            }
                        case "isnotequals":
                            if (!value0.equals(value1)) {
                                System.out.println("fvsv isnotequal");
                                return 1;
                            } else {
                                System.out.println("fvsv !isnotequal");
                                return 0;
                            }
                        case ">":
                            if (Double.parseDouble(value0) > Double.parseDouble(value1)) {
                                System.out.println("fvfv >");
                                return 1;
                            } else {
                                System.out.println("fvfv !>");
                                return 0;
                            }
                        case ">=":
                            if (Double.parseDouble(value0) >= Double.parseDouble(value1)) {
                                System.out.println("fvfv >=");
                                return 1;
                            } else {
                                System.out.println("fvfv !>=");
                                return 0;
                            }
                        case "<":
                            if (Double.parseDouble(value0) < Double.parseDouble(value1)) {
                                System.out.println("fvfv <");
                                return 1;
                            } else {
                                System.out.println("fvfv !<");
                                return 0;
                            }
                        case "<=":
                            if (Double.parseDouble(value0) <= Double.parseDouble(value1)) {
                                System.out.println("fvfv <=");
                                return 1;
                            } else {
                                System.out.println("fvfv !<=");
                                return 0;
                            }
                    }
                } else {//incerted
                    if (indexOfValue0 != -1) {
                        String value0 = variablesList.getVariable(indexOfValue0).value;
                        switch (condition[1]) {
                            case "==":
                                if (value0 == condition[2]) {
                                    System.out.println("fvss ==");
                                    return 1;
                                } else {
                                    System.out.println("fvss !==");
                                    return 0;
                                }
                            case "isequals":
                                if (value0.equals(condition[2])) {
                                    System.out.println("fvss isequals");
                                    return 1;
                                } else {
                                    System.out.println("fvss !isequals");
                                    return 0;
                                }
                            case "!=":
                                if (value0 != condition[2]) {
                                    System.out.println("fvss !=");
                                    return 1;
                                } else {
                                    System.out.println("fvss !!=");
                                    return 0;
                                }
                            case "isnotequals":
                                if (!value0.equals(condition[2])) {
                                    System.out.println("fvss isnotequals");
                                    return 1;
                                } else {
                                    System.out.println("fvss !isnotequals");
                                    return 0;
                                }
                        }
                    } else {
                        String value1 = variablesList.getVariable(indexOfValue1).value;
                        switch (condition[1]) {
                            case "==":
                                if (value1 == condition[0]) {
                                    System.out.println("fssv ==");
                                    return 1;
                                } else {
                                    System.out.println("fssv !==");
                                    return 0;
                                }
                            case "isequals":
                                if (value1.equals(condition[0])) {
                                    System.out.println("fssv isequals");
                                    return 1;
                                } else {
                                    System.out.println("fssv !isequals");
                                    return 0;
                                }
                            case "!=":
                                if (value1 != condition[0]) {
                                    System.out.println("fssv !=");
                                    return 1;
                                } else {
                                    System.out.println("fssv !!=");
                                    return 0;
                                }
                            case "isnotequals":
                                if (!value1.equals(condition[0])) {
                                    System.out.println("fssv isnotequals");
                                    return 1;
                                } else {
                                    System.out.println("fssv !isnotequals");
                                    return 0;
                                }
                        }
                    }
                }
            } else {
                switch (condition[1]) {
                    case "==":
                        if (condition[0] == condition[2]) {
                            System.out.println("fsss ==");
                            return 1;
                        } else {
                            System.out.println("fsss !==");
                            return 0;
                        }
                    case "isequals":
                        if (condition[0].equals(condition[2])) {
                            System.out.println("fsss isequals");
                            return 1;
                        } else {
                            System.out.println("fsss !isequals");
                            return 0;
                        }
                    case "!=":
                        if (condition[0] != condition[2]) {
                            System.out.println("fsss !=");
                            return 1;
                        } else {
                            System.out.println("fsss !!=");
                            return 0;
                        }
                    case "isnotequals":
                        if (!condition[0].equals(condition[2])) {
                            System.out.println("fsss isnotequals");
                            return 1;
                        } else {
                            System.out.println("fsss !isnotequals");
                            return 0;
                        }

                }
            }

        }   //End of else of if(type0 || type1)

        return -1;

    }

    public String op(int counter, String splitSpace[], VariablesList variablesList) {
        Error newError;
        int index = variablesList.searchByName(splitSpace[1]);
        int indexVar1 = variablesList.searchByName(splitSpace[2]);
        int indexVar2 = variablesList.searchByName(splitSpace[4]);
        if (indexVar1 != -1 || indexVar2 != -1) {
            if (indexVar1 != -1 && indexVar2 != -1) {
                switch (variablesList.getVariable(index).type) {
                    case "String":
                        return (variablesList.getVariable(indexVar1).value + variablesList.getVariable(indexVar2).value);
                    case "int":
                        switch (splitSpace[3]) {
                            case "+":
                                return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) + Integer.parseInt(variablesList.getVariable(indexVar2).value));
                            case "-":
                                return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) - Integer.parseInt(variablesList.getVariable(indexVar2).value));
                            case "*":
                                return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) * Integer.parseInt(variablesList.getVariable(indexVar2).value));
                            case "/":
                                if (Integer.parseInt(variablesList.getVariable(indexVar2).value) == 0) {
                                    newError = new Error(counter, "op-7): can not divide by 0");
                                    errorsList.addError(newError);
                                    return "wtf";
                                }
                                return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) / Integer.parseInt(variablesList.getVariable(indexVar2).value));
                        }
                        break;
                    case "float":
                        switch (splitSpace[3]) {
                            case "+":
                                return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) + Float.parseFloat(variablesList.getVariable(indexVar2).value));
                            case "-":
                                return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) - Float.parseFloat(variablesList.getVariable(indexVar2).value));
                            case "*":
                                return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) * Float.parseFloat(variablesList.getVariable(indexVar2).value));
                            case "/":
                                if (Float.parseFloat(variablesList.getVariable(indexVar2).value) == 0) {
                                    newError = new Error(counter, "op-8): can not divide by 0");
                                    errorsList.addError(newError);
                                    return "wtf";
                                }

                                return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) / Float.parseFloat(variablesList.getVariable(indexVar2).value));
                        }
                        break;
                    case "double":
                        switch (splitSpace[3]) {
                            case "+":
                                return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) + Double.parseDouble(variablesList.getVariable(indexVar2).value));
                            case "-":
                                return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) - Double.parseDouble(variablesList.getVariable(indexVar2).value));
                            case "*":
                                return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) * Double.parseDouble(variablesList.getVariable(indexVar2).value));
                            case "/":
                                if (Double.parseDouble(variablesList.getVariable(indexVar2).value) == 0) {
                                    newError = new Error(counter, "op-9)): can not divide by 0");
                                    errorsList.addError(newError);
                                    return "wtf";
                                }
                                return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) / Double.parseDouble(variablesList.getVariable(indexVar2).value));
                        }
                        break;
                }
            } else {
                if (indexVar1 != -1) {
                    switch (variablesList.getVariable(index).type) {
                        case "String":
                            return (variablesList.getVariable(indexVar1).value + splitSpace[4]);
                        case "int":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) + Integer.parseInt(splitSpace[4]));
                                case "-":
                                    return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) - Integer.parseInt(splitSpace[4]));
                                case "*":
                                    return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) * Integer.parseInt(splitSpace[4]));
                                case "/":
                                    if (Integer.parseInt(splitSpace[4]) == 0) {
                                        newError = new Error(counter, "op-10): can not divide by 0");
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }
                                    return "" + (Integer.parseInt(variablesList.getVariable(indexVar1).value) / Integer.parseInt(splitSpace[4]));
                            }
                            break;
                        case "float":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) + Float.parseFloat(splitSpace[4]));
                                case "-":
                                    return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) - Float.parseFloat(splitSpace[4]));
                                case "*":
                                    return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) * Float.parseFloat(splitSpace[4]));
                                case "/":
                                    if (Float.parseFloat(splitSpace[4]) == 0) {
                                        newError = new Error(counter, "op-11): can not divide by 0");
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }

                                    return "" + (Float.parseFloat(variablesList.getVariable(indexVar1).value) / Float.parseFloat(splitSpace[4]));
                            }
                            break;
                        case "double":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) + Double.parseDouble(splitSpace[4]));
                                case "-":
                                    return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) - Double.parseDouble(splitSpace[4]));
                                case "*":
                                    return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) * Double.parseDouble(splitSpace[4]));
                                case "/":
                                    if (Double.parseDouble(splitSpace[4]) == 0) {
                                        newError = new Error(counter, splitSpace[0] + " " + splitSpace[1] + " " + splitSpace[2] + " " + splitSpace[3] + " " + splitSpace[4], 16);
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }
                                    return "" + (Double.parseDouble(variablesList.getVariable(indexVar1).value) / Double.parseDouble(splitSpace[4]));
                            }
                            break;
                    }
                } else {
                    switch (variablesList.getVariable(index).type) {
                        case "String":
                            return (splitSpace[2] + variablesList.getVariable(indexVar2).value);
                        case "int":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Integer.parseInt(splitSpace[2]) + Integer.parseInt(variablesList.getVariable(indexVar2).value));
                                case "-":
                                    return "" + (Integer.parseInt(splitSpace[2]) - Integer.parseInt(variablesList.getVariable(indexVar2).value));
                                case "*":
                                    return "" + (Integer.parseInt(splitSpace[2]) * Integer.parseInt(variablesList.getVariable(indexVar2).value));
                                case "/":
                                    if (Integer.parseInt(variablesList.getVariable(indexVar2).value) == 0) {
                                        newError = new Error(counter, "op-12): can not divide by 0");
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }
                                    return "" + (Integer.parseInt(splitSpace[2]) / Integer.parseInt(variablesList.getVariable(indexVar2).value));
                            }
                            break;
                        case "float":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Float.parseFloat(splitSpace[2]) + Float.parseFloat(variablesList.getVariable(indexVar2).value));
                                case "-":
                                    return "" + (Float.parseFloat(splitSpace[2]) - Float.parseFloat(variablesList.getVariable(indexVar2).value));
                                case "*":
                                    return "" + (Float.parseFloat(splitSpace[2]) * Float.parseFloat(variablesList.getVariable(indexVar2).value));
                                case "/":
                                    if (Float.parseFloat(variablesList.getVariable(indexVar2).value) == 0) {
                                        newError = new Error(counter, "op-13): can not divide by 0");
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }

                                    return "" + (Float.parseFloat(splitSpace[2]) / Float.parseFloat(variablesList.getVariable(indexVar2).value));
                            }
                            break;
                        case "double":
                            switch (splitSpace[3]) {
                                case "+":
                                    return "" + (Double.parseDouble(splitSpace[2]) + Double.parseDouble(variablesList.getVariable(indexVar2).value));
                                case "-":
                                    return "" + (Double.parseDouble(splitSpace[2]) - Double.parseDouble(variablesList.getVariable(indexVar2).value));
                                case "*":
                                    return "" + (Double.parseDouble(splitSpace[2]) * Double.parseDouble(variablesList.getVariable(indexVar2).value));
                                case "/":
                                    if (Double.parseDouble(variablesList.getVariable(indexVar2).value) == 0) {
                                        newError = new Error(counter, "op-14): can not divide by 0");
                                        errorsList.addError(newError);
                                        return "wtf";
                                    }
                                    return "" + (Double.parseDouble(splitSpace[2]) / Double.parseDouble(variablesList.getVariable(indexVar2).value));
                            }
                            break;
                    }
                }
            }
        } else {
            switch (variablesList.getVariable(index).type) {
                case "String":
                    return (splitSpace[2] + splitSpace[4]);
                case "int":
                    switch (splitSpace[3]) {
                        case "+":
                            return "" + (Integer.parseInt(splitSpace[2]) + Integer.parseInt(splitSpace[4]));
                        case "-":
                            return "" + (Integer.parseInt(splitSpace[2]) - Integer.parseInt(splitSpace[4]));
                        case "*":
                            return "" + (Integer.parseInt(splitSpace[2]) * Integer.parseInt(splitSpace[4]));
                        case "/":
                            if (Integer.parseInt(splitSpace[4]) == 0) {
                                newError = new Error(counter, "op-12): can not divide by 0");
                                errorsList.addError(newError);
                                return "wtf";
                            }
                            return "" + (Integer.parseInt(splitSpace[2]) / Integer.parseInt(splitSpace[4]));
                    }
                    break;
                case "float":
                    switch (splitSpace[3]) {
                        case "+":
                            return "" + (Float.parseFloat(splitSpace[2]) + Float.parseFloat(splitSpace[4]));
                        case "-":
                            return "" + (Float.parseFloat(splitSpace[2]) - Float.parseFloat(splitSpace[4]));
                        case "*":
                            return "" + (Float.parseFloat(splitSpace[2]) * Float.parseFloat(splitSpace[4]));
                        case "/":
                            if (Float.parseFloat(splitSpace[4]) == 0) {
                                newError = new Error(counter, "op-13): can not divide by 0");
                                errorsList.addError(newError);
                                return "wtf";
                            }
                            return "" + (Float.parseFloat(splitSpace[2]) / Float.parseFloat(splitSpace[4]));
                    }
                    break;
                case "double":
                    switch (splitSpace[3]) {
                        case "+":
                            return "" + (Double.parseDouble(splitSpace[2]) + Double.parseDouble(splitSpace[4]));
                        case "-":
                            return "" + (Double.parseDouble(splitSpace[2]) - Double.parseDouble(splitSpace[4]));
                        case "*":
                            return "" + (Double.parseDouble(splitSpace[2]) * Double.parseDouble(splitSpace[4]));
                        case "/":
                            if (Double.parseDouble(splitSpace[4]) == 0) {
                                newError = new Error(counter, "op-14): can not divide by 0");
                                errorsList.addError(newError);
                                return "wtf";
                            }
                            return "" + (Double.parseDouble(splitSpace[2]) / Double.parseDouble(splitSpace[4]));
                    }
                    break;

            }
        }
        return "";

    }

    public boolean execution() {
        errorsList.removeAll();
        text = "";
        text += oldText;
        counterRow = 0;
        return exe(commandsList, variablesList);
    }

    //Eseguire i metodi e aggiungere a text (text += ...) eventuali messaggi che saranno mandati alla gui
    public boolean exe(CommandsList commandsList, VariablesList variablesList) {
        int counter = 0;
        while (counter < commandsList.size()) {
            CommandsList tempCommandsList;
            counterRow++;
            boolean controlCycle = true;
            Variable newVariable;
            Function newFunction;
            String split[];
            int index;
            String splittingSpace[] = commandsList.getCommand(counter).command.split(" ");
            if (debugMode) {
                System.out.println("Comando in esecuzione: " + commandsList.getCommand(counter).command);
            }
            switch (splittingSpace[0]) {
                case "initVar":
                    switch (splittingSpace[1]) {
                        case "int":
                            newVariable = new Variable(splittingSpace[1], splittingSpace[2], "" + ((int) Double.parseDouble(splittingSpace[3])));
                            break;
                        case "float":
                            newVariable = new Variable(splittingSpace[1], splittingSpace[2], "" + ((float) Double.parseDouble(splittingSpace[3])));
                            break;
                        case "double":
                            newVariable = new Variable(splittingSpace[1], splittingSpace[2], "" + Double.parseDouble(splittingSpace[3]));
                            break;
                        default:
                            newVariable = new Variable(splittingSpace[1], splittingSpace[2], splittingSpace[3]);
                            break;
                    }
                    variablesList.addVariable(newVariable);
                    if (debugMode) {
                        text += "New variable " + splittingSpace[2] + " added\n";
                    }
                    counter++;
                    break;
                case "var":
                    newVariable = new Variable(splittingSpace[1], splittingSpace[2]);
                    variablesList.addVariable(newVariable);
                    if (debugMode) {
                        text += "New variable " + splittingSpace[2] + " added\n";
                    }
                    counter++;
                    break;
                case "setVar":
                    index = variablesList.searchByName(splittingSpace[1]);
                    if (variablesList.getVariable(index).type.equals("int")) {
                        variablesList.getVariable(index).value = "" + ((int) Double.parseDouble(splittingSpace[2]));
                    } else if (variablesList.getVariable(index).type.equals("float")) {
                        variablesList.getVariable(index).value = "" + ((float) Double.parseDouble(splittingSpace[2]));
                    } else if (variablesList.getVariable(index).type.equals("double")) {
                        variablesList.getVariable(index).value = "" + Double.parseDouble(splittingSpace[2]);
                    } else {
                        variablesList.getVariable(index).value = splittingSpace[2];
                    }
                    if (debugMode) {
                        text += "Value set for variable " + splittingSpace[1] + "\n";
                    }
                    counter++;
                    break;
                case "print":
                    
                    if(splittingSpace[1].contains("_")) {
                        split = splittingSpace[1].split("_");
                        for(int i=0; i<split.length; i++) {
                            text += split[i] + " ";
                        }
                        text += "\n";
                    }
                    else {
                        if(splittingSpace[1].contains("[")) {
                            split = splittingSpace[1].split("\\[");
                            if(split.length>2) {
                                text += splittingSpace[1] + "\n";
                            }
                            else {
                                if(splittingSpace[1].contains("]")) {
                                     split = splittingSpace[1].split("]");
                                     if(split.length>2) {
                                           text += splittingSpace[1] + "\n";
                                     }
                                     else {
                                         String name = nameArray(splittingSpace[1]);
                                         String indexArray = indexArray(splittingSpace[1]);
                                         index = variablesList.searchByName(name);
                                         if(index==-1) {
                                             text += splittingSpace[1] + "\n";
                                         }
                                         else {
                                             if(isNumeric(indexArray)) {
                                                 text += name +"["+indexArray+"] = "+variablesList.getVariable(index).getValue(Integer.parseInt(indexArray)) +"\n";
                                             }
                                             else {
                                                 int index2 = variablesList.searchByName(indexArray);
                                                 if(index2==-1) {
                                                     text += splittingSpace[1] + "\n";
                                                 }
                                                 else {
                                                    String value = variablesList.getVariable(index2).value;
                                                    text += name +"["+value+"] = "+variablesList.getVariable(index).getValue(Integer.parseInt(value)) +"\n";
                                                 }
                                             }
                                         }
                                     }
                                }
                                else {
                                    text += splittingSpace[1] + "\n";
                                }
                            }
                        }
                        else if (splittingSpace[1].contains("]")) {
                            text += splittingSpace[1] + "\n";
                        }
                        else {
                            index = variablesList.searchByName(splittingSpace[1]);
                            if (index != -1) {
                                text += splittingSpace[1] + " = " + variablesList.getVariable(index).value + "\n";
                            } else {
                                text += splittingSpace[1] + "\n";
                            }
                        }
                        
                    }
                   
                    counter++;
                    break;
                case "deleteF":
                    index = functionsList.searchFunction(splittingSpace[1]);
                    functionsList.remove(index);
                    if (debugMode) {
                        text += "Function " + splittingSpace[1] + " removed\n";
                    }
                    counter++;
                    break;
                case "initFunc":
                    newFunction = new Function(splittingSpace[1], commandsList.getCommand(counter).commandsList);
                    functionsList.addFunction(newFunction);
                    if (debugMode) {
                        text += "New function " + splittingSpace[1] + " added\n";
                    }
                    counter++;
                    break;
                case "op":
                    index = variablesList.searchByName(splittingSpace[1]);
                    String value = op(counterRow, splittingSpace, variablesList);
                    if (value.equals("wtf")) {
                        return false;
                    }
                    variablesList.getVariable(index).value = value;
                    if (debugMode) {
                        text += "Value set for variable " + splittingSpace[1] + "\n";
                    }
                    counter++;
                    break;
                case "while":
                    controlCycle = true;
                    while (controlCycle) {
                        if (IfCondition(splittingSpace[1], variablesList) == 1) {
                            if (!exe(commandsList.getCommand(counter).commandsList, variablesList)) {
                                return false;
                            }
                        } else {
                            controlCycle = false;
                        }
                    }
                    counter++;
                    break;
                case "for":
                    split = splittingSpace[2].split("=");
                    newVariable = new Variable(splittingSpace[1], split[0], split[1]);
                    variablesList.addVariable(newVariable);
                    index = variablesList.searchByName(split[0]);
                    if (debugMode) {
                        System.out.println("Aggiunta nuova variabile " + splittingSpace[1] + " " + split[0] + "=" + split[1] + " con indice " + index);
                    }
                    controlCycle = true;

                    while (controlCycle) {

                        if (IfCondition(splittingSpace[3], variablesList) == 1) {

                            if (!exe(commandsList.getCommand(counter).commandsList, variablesList)) {
                                return false;
                            }
                            switch (variablesList.getVariable(index).type) {
                                case "int":
                                    variablesList.getVariable(index).value = "" + ((int) Double.parseDouble(variablesList.getVariable(index).value) + (int) Double.parseDouble(splittingSpace[4]));
                                    break;
                                case "float":
                                    variablesList.getVariable(index).value = "" + ((float) Double.parseDouble(variablesList.getVariable(index).value) + (float) Double.parseDouble(splittingSpace[4]));
                                    break;
                                case "double":
                                    variablesList.getVariable(index).value = "" + (Double.parseDouble(variablesList.getVariable(index).value) + Double.parseDouble(splittingSpace[4]));
                                    break;

                            }
                            if (debugMode) {
                                System.out.println(variablesList.getVariable(index).name + " = " + variablesList.getVariable(index).value);
                            }

                        } else {
                            controlCycle = false;
                        }
                    }
                    variablesList.remove(index);
                    if (debugMode) {
                        System.out.println("Variabile eliminata");
                    }
                    counter++;
                    break;
                case "elseif":
                case "if":
                    if (IfCondition(splittingSpace[1], variablesList) == 1) {
                        int jump = 1;
                        if (commandsList.size() > 1) {
                            for (int i = 1; i < commandsList.size(); i++) {
                                if (commandsList.getCommand(i).command.startsWith("else")) {
                                    jump++;
                                } else {
                                    break;
                                }
                            }
                        }

                        if (!exe(commandsList.getCommand(counter).commandsList, variablesList)) {
                            return false;
                        }
                        counter += jump;
                    } else {
                        counter++;
                    }
                    break;
                case "else":

                    if (!exe(commandsList.getCommand(counter).commandsList, variablesList)) {
                        return false;
                    }
                    counter++;
                    break;
                case "func":
                    index = functionsList.searchFunction(splittingSpace[1]);
                    functionsList.getFunc(index).variablesList.removeAll();
                    functionsList.getFunc(index).variablesList.addList(this.variablesList);
                    int size = this.variablesList.size();
                    if (!exe(functionsList.getFunc(index).commandsList, functionsList.getFunc(index).variablesList)) {
                        return false;
                    }
                    this.variablesList.removeAll();
                    for(int i=0; i < size; i++){
                       this.variablesList.addVariable(functionsList.getFunc(index).variablesList.getVariable(i));
                    }
                    counter++;

                    break;
                case "reset;":
                    this.variablesList.removeAll();
                    functionsList.removeAll();
                    counter++;
                    break;
                case "authors;":
                    text += "Progetto UNIVR per Linguaggi 2013/2014\n\nAlexandro Todeschini - alexandro.todeschini87@hmail.com\nAlberto Scisciani\nFabio Mori - mrofba91@gmail.com\n";
                    counter++;
                    break;
                case "clean;":
                    text = "";
                    counter++;
                    break;
                case "log;":
                    GregorianCalendar gc = new GregorianCalendar();

                    try {
                        PrintWriter out = new PrintWriter("log" + gc.YEAR + "-" + gc.MONTH + "-" + gc.DAY_OF_MONTH + "_" + gc.HOUR + ":" + gc.MINUTE + ":" + gc.SECOND + ".txt");
                        out.println(console.getText());
                        out.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (debugMode) {
                        text += "Created a log file\n";
                    }
                    counter++;
                    break;
                case "delete":
                    System.out.println(splittingSpace[1]);
                    index = variablesList.searchByName(splittingSpace[1]);
                    variablesList.remove(index);
                    if (debugMode) {
                        text += "Variable " + splittingSpace[1] + " removed\n";
                    }
                    counter++;
                    break;
                case "initArray":
                    variablesList.addVariable(new Variable(splittingSpace[1], splittingSpace[2], Integer.parseInt(splittingSpace[3])));
                    if (debugMode) {
                        text += "Variable " + splittingSpace[1] + " added\n";
                    }
                    counter++;
                    break;
                case "setArray":
                    index = variablesList.searchByName(splittingSpace[1]);
                    int indexArray;
                    if (isNumeric(splittingSpace[2])) {
                        indexArray = Integer.parseInt(splittingSpace[2]);
                    } else {
                        indexArray = variablesList.searchByName(splittingSpace[2]);
                    }
                    if (!variablesList.getVariable(index).setValue(splittingSpace[3], indexArray)) {
                        errorsList.addError(new Error(counterRow, "Out of bounds for array named " + splittingSpace[1]));
                        return false;
                    }
                    counter++;
                break;
                case "assign":
                    if (isArray(splittingSpace[1]) || isArray(splittingSpace[2])) {
                        if (isArray(splittingSpace[1]) && isArray(splittingSpace[2])) {
                            String name1 = nameArray(splittingSpace[1]);
                            String name2 = nameArray(splittingSpace[2]);
                            String tempIndexArray1 = indexArray(splittingSpace[1]);
                            String tempIndexArray2 = indexArray(splittingSpace[2]);
                            int indexArray1 = 0;
                            int indexArray2 = 0;
                            if (isNumeric(tempIndexArray1)) {
                                indexArray1 = Integer.parseInt(tempIndexArray1);
                            } else {
                                index = variablesList.searchByName(tempIndexArray1);
                                indexArray1 = Integer.parseInt(variablesList.getVariable(index).value);
                            }
                            if (isNumeric(tempIndexArray2)) {
                                indexArray2 = Integer.parseInt(tempIndexArray2);
                            } else {
                                index = variablesList.searchByName(tempIndexArray2);
                                indexArray2 = Integer.parseInt(variablesList.getVariable(index).value);
                            }
                            index = variablesList.searchByName(name1);
                            int index2 = variablesList.searchByName(name2);
                            value = variablesList.getVariable(index2).getValue(indexArray2);
                            if (value.equals("wtf")) {
                                errorsList.addError(new Error(counterRow, "out of bonds for array named " + name2));
                                return false;
                            }
                            switch (variablesList.getVariable(index).type) {
                                case "int":
                                    if (!variablesList.getVariable(index).setValue("" + ((int) Double.parseDouble(value)), indexArray1)) {
                                        errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                        return false;
                                    }
                                    break;
                                case "float":
                                    if (!variablesList.getVariable(index).setValue("" + ((float) Double.parseDouble(value)), indexArray1)) {
                                        errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                        return false;
                                    }
                                    break;
                                case "double":
                                    if (!variablesList.getVariable(index).setValue("" + Double.parseDouble(value), indexArray1)) {
                                        errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                        return false;
                                    }
                                    break;

                                case "String":
                                    if (!variablesList.getVariable(index).setValue(value, indexArray1)) {
                                        errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                        return false;
                                    }
                                    break;

                            }
                            if (debugMode) {
                                text += "New value for variable " + splittingSpace[1] + " assigned\n";
                            }

                        } else {
                            if (isArray(splittingSpace[1])) {
                                String name1 = nameArray(splittingSpace[1]);
                                String tempIndexArray1 = indexArray(splittingSpace[1]);
                                int indexArray1 = 0;
                                if (isNumeric(tempIndexArray1)) {
                                    indexArray1 = Integer.parseInt(tempIndexArray1);
                                } else {
                                    index = variablesList.searchByName(tempIndexArray1);
                                    indexArray1 = Integer.parseInt(variablesList.getVariable(index).value);
                                }
                                index = variablesList.searchByName(name1);
                                int index2 = variablesList.searchByName(splittingSpace[2]);
                                value = variablesList.getVariable(index2).value;
                                switch (variablesList.getVariable(index).type) {
                                    case "int":
                                        if (!variablesList.getVariable(index).setValue("" + ((int) Double.parseDouble(value)), indexArray1)) {
                                            errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                            return false;
                                        }
                                        break;
                                    case "float":
                                        if (!variablesList.getVariable(index).setValue("" + ((float) Double.parseDouble(value)), indexArray1)) {
                                            errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                            return false;
                                        }
                                        break;
                                    case "double":
                                        if (!variablesList.getVariable(index).setValue("" + Double.parseDouble(value), indexArray1)) {
                                            errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                            return false;
                                        }
                                        break;

                                    case "String":
                                        if (!variablesList.getVariable(index).setValue(value, indexArray1)) {
                                            errorsList.addError(new Error(counterRow, "out of bonds for array named " + name1));
                                            return false;
                                        }
                                        break;

                                }
                                if (debugMode) {
                                    text += "New value for variable " + splittingSpace[1] + " assigned\n";
                                }
                            } else {
                                String name2 = nameArray(splittingSpace[2]);
                                String tempIndexArray2 = indexArray(splittingSpace[2]);
                                int indexArray2 = 0;
                                if (isNumeric(tempIndexArray2)) {
                                    indexArray2 = Integer.parseInt(tempIndexArray2);
                                } else {
                                    index = variablesList.searchByName(tempIndexArray2);
                                    indexArray2 = Integer.parseInt(variablesList.getVariable(index).value);
                                }
                                int index2 = variablesList.searchByName(name2);
                                value = variablesList.getVariable(index2).getValue(indexArray2);
                                if (value.equals("wtf")) {
                                    errorsList.addError(new Error(counterRow, "out of bonds for array named " + name2));
                                    return false;
                                }
                                index = variablesList.searchByName(splittingSpace[1]);
                                switch (variablesList.getVariable(index).type) {
                                    case "int":
                                        variablesList.getVariable(index).value = "" + ((int) Double.parseDouble(value));
                                        break;
                                    case "float":
                                        variablesList.getVariable(index).value = "" + ((float) Double.parseDouble(value));
                                        break;
                                    case "double":
                                        variablesList.getVariable(index).value = "" + Double.parseDouble(value);
                                        break;
                                    case "String":
                                        variablesList.getVariable(index).value = value;
                                        break;
                                }
                                if (debugMode) {
                                    text += "New value for variable " + splittingSpace[1] + " assigned\n";
                                }

                            }
                        }
                    } else {
                        index = variablesList.searchByName(splittingSpace[1]);
                        int index2 = variablesList.searchByName(splittingSpace[2]);
                        switch (variablesList.getVariable(index).type) {
                            case "int":
                                variablesList.getVariable(index).value = "" + ((int) Double.parseDouble(variablesList.getVariable(index2).value));
                                break;
                            case "float":
                                variablesList.getVariable(index).value = "" + ((float) Double.parseDouble(variablesList.getVariable(index2).value));
                                break;
                            case "double":
                                variablesList.getVariable(index).value = "" + Double.parseDouble(variablesList.getVariable(index2).value);
                                break;
                            case "String":
                                variablesList.getVariable(index).value = variablesList.getVariable(index2).value;
                                break;
                        }
                        if (debugMode) {
                            text += "New value for variable " + splittingSpace[1] + " assigned\n";
                        }
                    }
                    counter++;
                    break;

            }
        }
        return true;
    }

    public ErrorsList getErrorsList() {
        return errorsList;
    }

    public String getText() {
        System.out.println(text);
        return text;
    }

    private String nameArray(String format) {
        String split[] = format.split("\\[");
        return split[0];
    }

    private String indexArray(String format) {
        String split[] = format.split("\\[");
        split = split[1].split("]");
        return split[0];
    }

    private boolean isArray(String format) {
        return format.contains("[");
    }
}
