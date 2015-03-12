package elaboratolinguaggiecompilatori;

public class GrammarAnalyzer {

    private VariablesList variablesList;
    private FunctionsList functionsList;

    private VariablesList globalVariablesList;
    private FunctionsList globalFunctionsList;

    private CommandsList commandsList;

    private ErrorsList syntaxErrorsList;
    private ErrorsList structuralErrorsList;
    private ErrorsList logicalErrorsList;
    private ErrorsList errorsList;

    public boolean debugMode = false;

    private int counter;

    private int deepLevel;
    private boolean controlFuncDeepLevel;

    private int counterRow = 0;

    private String commands;

    private String splitSpace[];

    private final String[] typeOfVariable = {"int", "float", "double", "String"};
    private final String[] mathOperator = {"+", "*", "-", "/"};
    private final String[] condOperator = {"==", "+", "<", ">", ">=", "<=", "isEquals", "isNotEquals"};

    public GrammarAnalyzer(VariablesList variablesList, FunctionsList functionsList) {

        this.variablesList = new VariablesList();
        this.functionsList = new FunctionsList();
        commandsList = new CommandsList();
        errorsList = new ErrorsList();
        syntaxErrorsList = new ErrorsList();
        structuralErrorsList = new ErrorsList();
        logicalErrorsList = new ErrorsList();

        globalVariablesList = variablesList;
        globalFunctionsList = functionsList;

    }

    public CommandsList getCommandsList() {
        return commandsList;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public ErrorsList getErrorsList() {
        errorsList.removeAll();
        errorsList.addList(syntaxErrorsList);
        errorsList.addList(structuralErrorsList);
        errorsList.addList(logicalErrorsList);

        return errorsList;
    }

    public void examination() {
        counter = 0;
        counterRow = 0;
        syntaxErrorsList.removeAll();
        commands = syntaxAnalyze();
        if (debugMode) {
            System.out.println("Comando mantenuto:\n" + commands);
            System.out.println("\n\nLista errori sintattici:");
            for (int i = 0; i < syntaxErrorsList.size(); i++) {
                System.out.println("\t- " + syntaxErrorsList.getError(i).code);
            }
        }
        deepLevel = 0;
        counterRow = 0;
        controlFuncDeepLevel = false;
        counter = 0;
        structuralErrorsList.removeAll();
        commandsList.removeAll();
        splitSpace = commands.split(" ");
        parser(deepLevel, commandsList);
        if (debugMode) {
            System.out.println("\n\nLista errori strutturali:");
            for (int i = 0; i < structuralErrorsList.size(); i++) {
                System.out.println("\t- " + structuralErrorsList.getError(i).code);
            }
            System.out.println("\n\nAlbero generato:\n");
            printAll(commandsList, "");
        }
        counterRow = 0;
        variablesList.removeAll();
        functionsList.removeAll();
        variablesList.addList(globalVariablesList);
        functionsList.addList(globalFunctionsList);
        logicalErrorsList.removeAll();
        logicalAnalyze(commandsList, variablesList);
        if (debugMode) {
            System.out.println("\n\nLista errori logici:");
            for (int i = 0; i < logicalErrorsList.size(); i++) {
                System.out.println("\t- " + logicalErrorsList.getError(i).code);
            }
        }

    }

    public String syntaxAnalyze() {
        String returnedCommands = "";
        String splittingSpace[] = commands.split(" ");
        while (counter < splittingSpace.length) {
            String split[];
            Error newError;
            switch (splittingSpace[counter]) {
                case "initVar":
                    if ((counter + 3) >= splittingSpace.length) {
                        newError = new Error(counterRow, "initVar-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 3].contains(";")) {
                            newError = new Error(counterRow, "initVar-1): " + splittingSpace[counter + 3] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 3].length() - 1) <= 0) {
                                newError = new Error(counterRow, "initVar-7): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {
                                if (!typeInList(splittingSpace[counter + 1])) {
                                    newError = new Error(counterRow, "initVar-2): " + splittingSpace[counter + 1] + " type not recognized");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    if (isNumeric(splittingSpace[counter + 2]) || splittingSpace[counter + 2].contains("_") || splittingSpace[counter + 2].contains("[") || splittingSpace[counter + 2].contains("]")) {
                                        newError = new Error(counterRow, "initVar-3): " + splittingSpace[counter + 2] + " name format is wrong");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {
                                        if (!isNumeric(splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1)) && !splittingSpace[counter + 1].equals("String")) {
                                            newError = new Error(counterRow, "initVar-4): " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1) + " type insert is not compatible with value");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        } else {
                                            returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2] + " " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1);
                                            counter += 4;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "var":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "var-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains(";")) {
                            newError = new Error(counterRow, "var-1): " + splittingSpace[counter + 2] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 2].length() - 1) <= 0) {
                                newError = new Error(counterRow, "var-6): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {

                                if (isNumeric(splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1)) || splittingSpace[counter + 2].contains("_") || splittingSpace[counter + 2].contains("[") || splittingSpace[counter + 2].contains("]")) {
                                    newError = new Error(counterRow, "var-2): " + splittingSpace[counter + 2] + " name format is wrong");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    if (!typeInList(splittingSpace[counter + 1])) {
                                        newError = new Error(counterRow, "var-3): " + splittingSpace[counter + 1] + " type not recognized");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {

                                        returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1);
                                        counter += 3;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "setVar":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "setVar-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains(";")) {
                            newError = new Error(counterRow, "setVar-1): " + splittingSpace[counter + 2] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 2].length() - 1) <= 0) {
                                newError = new Error(counterRow, "setVar-5): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {
                                if (!isNumeric(splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1))) {
                                    newError = new Error(counterRow, "setVar-2): " + splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1) + " value must be a number");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1);
                                    counter += 3;
                                }
                            }
                        }
                    }
                    break;
                case "initArray":
                    if ((counter + 3) >= splittingSpace.length) {
                        newError = new Error(counterRow, "initArray-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 3].contains(";")) {
                            newError = new Error(counterRow, "initArray-1): " + splittingSpace[counter + 3] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 3].length() - 1) <= 0) {
                                newError = new Error(counterRow, "initArray-8): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {

                                if (isNumeric(splittingSpace[counter + 2]) || splittingSpace[counter + 2].contains("_") || splittingSpace[counter + 2].contains("[") || splittingSpace[counter + 2].contains("]")) {
                                    newError = new Error(counterRow, "initArray-2): " + splittingSpace[counter + 2] + " name format is wrong");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    if (!typeInList(splittingSpace[counter + 1])) {
                                        newError = new Error(counterRow, "initArray-3): " + splittingSpace[counter + 1] + " type not recognized");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {
                                        if (!isNumeric(splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1))) {
                                            newError = new Error(counterRow, "initArray-4): " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1) + " index is not a number");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        } else {
                                            if ((Integer.parseInt(splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1))) <= 0) {
                                                newError = new Error(counterRow, "initArray-5): " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1) + " index must be >0");
                                                syntaxErrorsList.addError(newError);
                                                return returnedCommands;
                                            } else {
                                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2] + " " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1);
                                                counter += 4;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "setArray":
                    if ((counter + 3) >= splittingSpace.length) {
                        newError = new Error(counterRow, "setArray-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 3].contains(";")) {
                            newError = new Error(counterRow, "setArray-1): " + splittingSpace[counter + 3] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 3].length() - 1) <= 0) {
                                newError = new Error(counterRow, "setArray-7): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {

                                if (splittingSpace[counter + 3].contains("[") || splittingSpace[counter + 3].contains("]")) {
                                    newError = new Error(counterRow, "setArray-2): " + splittingSpace[counter + 3] + "index must be a number or a not array variable");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2] + " " + splittingSpace[counter + 3].substring(0, splittingSpace[counter + 3].length() - 1);
                                    counter += 4;
                                }
                            }
                        }
                    }
                    break;
                case "delete":
                    if ((counter + 1) >= splittingSpace.length) {
                        newError = new Error(counterRow, "delete-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 1].contains(";")) {
                            newError = new Error(counterRow, "delete-1): " + splittingSpace[counter + 1] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 1].length() - 1) <= 0) {
                                newError = new Error(counterRow, "delete-4): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1].substring(0, splittingSpace[counter + 1].length() - 1);
                                counter += 2;
                            }
                        }
                    }
                    break;
                case "op":
                    if ((counter + 4) >= splittingSpace.length) {
                        newError = new Error(counterRow, "op-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 4].contains(";")) {
                            newError = new Error(counterRow, "op-1): " + splittingSpace[counter + 4] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 4].length() - 1) <= 0) {
                                newError = new Error(counterRow, "op-7): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {
                                if (!mathInList(splittingSpace[counter + 3])) {
                                    newError = new Error(counterRow, "op-2): " + splittingSpace[counter + 3] + " math operator not recognized");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    if (splittingSpace[counter + 4].contains("[") || splittingSpace[counter + 4].contains("]") || splittingSpace[counter + 2].contains("]") || splittingSpace[counter + 2].contains("[") || splittingSpace[counter + 1].contains("]") || splittingSpace[counter + 1].contains("[")) {
                                        newError = new Error(counterRow, "op-3): argoments can not be array variable");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {
                                        returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2] + " " + splittingSpace[counter + 3] + " " + splittingSpace[counter + 4].substring(0, splittingSpace[counter + 4].length() - 1);
                                        counter += 5;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "assign":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "assign-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains(";")) {
                            newError = new Error(counterRow, "assign-1): " + splittingSpace[counter + 2] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 2].length() - 1) <= 0) {
                                newError = new Error(counterRow, "assign-14): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {

                                if (splittingSpace[counter + 1].contains("[")) {
                                    split = splittingSpace[counter + 1].split("\\[");
                                    if (split.length > 2) {
                                        newError = new Error(counterRow, "assign-3): " + splittingSpace[counter + 1] + " variable not recognize");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    }
                                    if (splittingSpace[counter + 1].contains("]")) {
                                        split = splittingSpace[counter + 1].split("]");
                                        if (split.length > 2) {
                                            newError = new Error(counterRow, "assign-4): " + splittingSpace[counter + 1] + " variable not recognize");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        }
                                    } else {
                                        newError = new Error(counterRow, "assign-5): " + splittingSpace[counter + 1] + " variable not recognize");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    }
                                }
                                if (splittingSpace[counter + 2].contains("[")) {
                                    split = splittingSpace[counter + 2].split("\\[");
                                    if (split.length > 2) {
                                        newError = new Error(counterRow, "assign-6): " + splittingSpace[counter + 2] + " variable not recognize");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    }
                                    if (splittingSpace[counter + 2].contains("]")) {
                                        split = splittingSpace[counter + 2].split("]");
                                        if (split.length > 2) {
                                            newError = new Error(counterRow, "assign-7): " + splittingSpace[counter + 2] + " variable not recognize");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        }
                                    } else {
                                        newError = new Error(counterRow, "assign-8): " + splittingSpace[counter + 2] + " variable not recognize");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    }
                                }
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2].substring(0, splittingSpace[counter + 2].length() - 1);;
                                counter += 3;
                            }
                        }

                    }
                    break;
                case "initFunc":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "initFunc-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (isNumeric(splittingSpace[counter + 1]) || splittingSpace[counter + 1].contains("_") || splittingSpace[counter + 1].contains("[") || splittingSpace[counter + 1].contains("]")) {
                            newError = new Error(counterRow, "initFunc-1): " + splittingSpace[counter + 1] + " name format is wrong");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if (!splittingSpace[counter + 2].contains("do")) {
                                newError = new Error(counterRow, "initFunc-2): " + splittingSpace[counter + 2] + " expected do");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1];
                                counter += 3;
                            }

                        }
                    }
                    break;
                case "func":
                    if ((counter + 1) >= splittingSpace.length) {
                        newError = new Error(counterRow, "func-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 1].contains(";")) {
                            newError = new Error(counterRow, "func-1): " + splittingSpace[counter + 1] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;

                        } else {
                            if ((splittingSpace[counter + 1].length() - 1) <= 0) {
                                newError = new Error(counterRow, "func-3): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1].substring(0, splittingSpace[counter + 1].length() - 1);;
                                counter += 2;
                            }
                        }

                    }
                    break;
                case "deleteF":
                    if ((counter + 1) >= splittingSpace.length) {
                        newError = new Error(counterRow, "deleteF-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 1].contains(";")) {
                            newError = new Error(counterRow, "deleteF-1): " + splittingSpace[counter + 1] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 1].length() - 1) <= 0) {
                                newError = new Error(counterRow, "deleteF-4): need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {

                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1].substring(0, splittingSpace[counter + 1].length() - 1);;
                                counter += 2;
                            }
                        }

                    }
                    break;
                case "for":
                    if ((counter + 5) >= splittingSpace.length) {
                        newError = new Error(counterRow, "for-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {

                        if (!splittingSpace[counter + 5].equals("do")) {
                            newError = new Error(counterRow, "for-1): " + splittingSpace[counter + 5] + " expected do");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if (!typeInList(splittingSpace[counter + 1])) {
                                newError = new Error(counterRow, "for-2): " + splittingSpace[counter + 1] + " type not recognized");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {
                                if (splittingSpace[counter + 1].equals("String")) {
                                    newError = new Error(counterRow, "for-3): " + splittingSpace[counter + 1] + " type can not be String");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;

                                } else {

                                    if (splittingSpace[counter + 2].contains("[")) {
                                        split = splittingSpace[counter + 2].split("\\[");
                                        if (split.length > 2) {
                                            newError = new Error(counterRow, "for-4): " + splittingSpace[counter + 2] + " variable not recognized");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        } else if (split.length == 2) {
                                            split = splittingSpace[counter + 2].split("]");
                                            if (split.length > 2) {
                                                newError = new Error(counterRow, "for-5): " + splittingSpace[counter + 2] + " variable not recognized");
                                                syntaxErrorsList.addError(newError);
                                                return returnedCommands;
                                            } else {
                                                newError = new Error(counterRow, "for-6): " + splittingSpace[counter + 2] + " array can not use in for cycle");
                                                syntaxErrorsList.addError(newError);
                                                return returnedCommands;
                                            }
                                        }

                                    } else if (splittingSpace[counter + 2].contains("]")) {
                                        newError = new Error(counterRow, "for-7): " + splittingSpace[counter + 2] + " variable not recognized");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {
                                        split = splittingSpace[counter + 2].split("=");

                                        if (split.length > 2 || split.length < 2) {
                                            newError = new Error(counterRow, "for-8): " + splittingSpace[counter + 2] + " expected nameVar=value");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        } else {
                                            if (!isNumeric(split[1])) {
                                                newError = new Error(counterRow, "for-9): " + split[1] + " expected nameVar=value");
                                                syntaxErrorsList.addError(newError);
                                                return returnedCommands;
                                            } else {
                                                if (isNumeric(split[0])) {
                                                    newError = new Error(counterRow, "for-10): " + split[0] + " expected nameVar=value");
                                                    syntaxErrorsList.addError(newError);
                                                    return returnedCommands;
                                                } else if (split[0].contains("_")) {
                                                    newError = new Error(counterRow, "for-11): " + split[0] + " var name can not contains _");
                                                    syntaxErrorsList.addError(newError);
                                                    return returnedCommands;

                                                } else {

                                                    if (splittingSpace[counter + 3].contains("[")) {
                                                        split = splittingSpace[counter + 3].split("\\[");
                                                        if (split.length > 2) {
                                                            newError = new Error(counterRow, "for-12): " + splittingSpace[counter + 3] + " variable not recognized");
                                                            syntaxErrorsList.addError(newError);
                                                            return returnedCommands;
                                                        } else {
                                                            if (splittingSpace[counter + 3].contains("]")) {
                                                                split = splittingSpace[counter + 3].split("]");
                                                                if (split.length > 2) {
                                                                    newError = new Error(counterRow, "for-13): " + splittingSpace[counter + 3] + " variable not recognized");
                                                                    syntaxErrorsList.addError(newError);
                                                                    return returnedCommands;
                                                                } else {
                                                                    newError = new Error(counterRow, "for-14): " + splittingSpace[counter + 3] + " array can not use in for cycle");
                                                                    syntaxErrorsList.addError(newError);
                                                                    return returnedCommands;
                                                                }
                                                            }
                                                        }

                                                    } else if (splittingSpace[counter + 3].contains("]")) {
                                                        newError = new Error(counterRow, "for-15): " + splittingSpace[counter + 3].contains("]") + " variable not recognized");
                                                        syntaxErrorsList.addError(newError);
                                                        return returnedCommands;

                                                    } else {
                                                        split = splittingSpace[counter + 3].split("_");
                                                        if (split.length > 3 || split.length < 3) {
                                                            newError = new Error(counterRow, "for-16): " + splittingSpace[counter + 3] + " expected (value-var)_condOperator_(value-var)");
                                                            syntaxErrorsList.addError(newError);
                                                            return returnedCommands;
                                                        } else {
                                                            if (!condInList(split[1])) {
                                                                newError = new Error(counterRow, "for-17): " + split[1] + " cond operator not recognized");
                                                                syntaxErrorsList.addError(newError);
                                                                return returnedCommands;
                                                            } else {
                                                                if (!isNumeric(splittingSpace[counter + 4])) {
                                                                    newError = new Error(counterRow, "for-18): " + splittingSpace[counter + 4] + " incremental value must be a number");
                                                                    syntaxErrorsList.addError(newError);
                                                                    return returnedCommands;
                                                                } else {
                                                                    returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1] + " " + splittingSpace[counter + 2] + " " + splittingSpace[counter + 3] + " " + splittingSpace[counter + 4];
                                                                    counter += 6;
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }

                            }

                        }
                    }
                    break;
                case "while":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "while-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains("do")) {
                            newError = new Error(counterRow, "while-1): " + splittingSpace[counter + 2] + " expected do");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            split = splittingSpace[counter + 1].split("_");
                            if (split.length > 3 || split.length < 3) {
                                newError = new Error(counterRow, "while-2): " + splittingSpace[counter + 1] + " expected (value-var)_condOperator_(value-var)");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {
                                if (!condInList(split[1])) {
                                    newError = new Error(counterRow, "while-3): " + split[1] + " cond operator not recognized");
                                    syntaxErrorsList.addError(newError);
                                    return returnedCommands;
                                } else {
                                    if (splittingSpace[counter + 1].contains("[")) {
                                        split = splittingSpace[counter + 1].split("\\[");
                                        if (split.length > 2) {
                                            newError = new Error(counterRow, "while-4): " + splittingSpace[counter + 1] + " variable not recognized");
                                            syntaxErrorsList.addError(newError);
                                            return returnedCommands;
                                        } else {
                                            if (splittingSpace[counter + 1].contains("]")) {
                                                split = splittingSpace[counter + 1].split("]");
                                                if (split.length > 2) {
                                                    newError = new Error(counterRow, "while-5): " + splittingSpace[counter + 1] + " variable not recognized");
                                                    syntaxErrorsList.addError(newError);
                                                    return returnedCommands;
                                                } else {
                                                    newError = new Error(counterRow, "while-6): " + splittingSpace[counter + 1] + " array can not use in while cycle");
                                                    syntaxErrorsList.addError(newError);
                                                    return returnedCommands;
                                                }
                                            }
                                        }
                                    } else if (splittingSpace[counter + 1].contains("]")) {
                                        newError = new Error(counterRow, "while-7): " + splittingSpace[counter + 1] + " variable not recognized");
                                        syntaxErrorsList.addError(newError);
                                        return returnedCommands;
                                    } else {
                                        returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1];
                                        counter += 3;
                                    }
                                }
                            }
                        }

                    }
                    break;

                case "if":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "if-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains("then")) {
                            newError = new Error(counterRow, "if-1): " + splittingSpace[counter + 2] + " expected then");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            split = splittingSpace[counter + 1].split("_");
                            if (split.length > 3 || split.length < 3) {
                                newError = new Error(counterRow, "if-2: " + splittingSpace[counter + 1] + " expected (value-var)_condOperator_(value-var)");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1];
                                counter += 3;
                            }
                        }

                    }
                    break;
                case "elseif":
                    if ((counter + 2) >= splittingSpace.length) {
                        newError = new Error(counterRow, "elseif-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 2].contains("then")) {
                            newError = new Error(counterRow, "elseif-1): " + splittingSpace[counter + 2] + " expected then");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            split = splittingSpace[counter + 1].split("_");
                            if (split.length > 3 || split.length < 3) {
                                newError = new Error(counterRow, "elseif-2): " + splittingSpace[counter + 1] + " expected (value-var)_condOperator_(value-var)");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;

                            } else {
                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1];
                                counter += 3;
                            }
                        }

                    }
                    break;
                case "clean;":
                case "else":
                case "log;":
                case "authors;":
                case "reset;":
                case "done;":
                    returnedCommands += splittingSpace[counter];
                    counter++;
                    break;

                case "print":
                    if ((counter + 1) >= splittingSpace.length) {
                        newError = new Error(counterRow, "print-0): syntax is wrong");
                        syntaxErrorsList.addError(newError);
                        return returnedCommands;
                    } else {
                        if (!splittingSpace[counter + 1].contains(";")) {
                            newError = new Error(counterRow, "print-1): " + splittingSpace[counter + 1] + " expected ;");
                            syntaxErrorsList.addError(newError);
                            return returnedCommands;
                        } else {
                            if ((splittingSpace[counter + 1].length() - 1) <= 0) {
                                newError = new Error(counterRow, "print-2): " + splittingSpace[counter + 1] + " need an argoment");
                                syntaxErrorsList.addError(newError);
                                return returnedCommands;
                            } else {

                                returnedCommands += splittingSpace[counter] + " " + splittingSpace[counter + 1].substring(0, splittingSpace[counter + 1].length() - 1);
                                counter += 2;

                            }
                        }
                    }
                    break;

                default:
                    newError = new Error(counterRow, splittingSpace[0] + " command not recognize");
                    syntaxErrorsList.addError(newError);
                    return returnedCommands;

            }
            returnedCommands += " ";
            counterRow++;
        }
        return returnedCommands;
    }

    public int parser(int deepLevel, CommandsList parsingCommandsList) {
        while (counter < splitSpace.length) {
            Command newCommand;
            Error newError;
            int index;
            switch (splitSpace[counter]) {
                case "op":  //op finalVar [var-num-string] mathOperator [var-num-string];
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim() + " " + splitSpace[counter + 3].trim() + " " + splitSpace[counter + 4].trim(), false);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 5;
                    break;
                case "initArray": //initVar type name value;
                    if ((deepLevel == 0) || ((deepLevel == 1) && (controlFuncDeepLevel))) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim() + " " + splitSpace[counter + 3].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 4;
                    } else {
                        newError = new Error(counterRow, "initArray-6): variable can not be inizialaze in conditional cycle");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }
                    break;
                case "initVar": //initVar type name value;
                    if ((deepLevel == 0) || ((deepLevel == 1) && (controlFuncDeepLevel))) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim() + " " + splitSpace[counter + 3].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 4;
                    } else {
                        newError = new Error(counterRow, "initVar-5): variable can not be inizialaze in conditional cycle");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }
                    break;
                case "setArray": //initVar type name value;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim() + " " + splitSpace[counter + 3].trim(), false);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 4;
                    break;
                case "var": //var type name;
                    if ((deepLevel == 0) || ((deepLevel == 1) && (controlFuncDeepLevel))) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 3;
                    } else {
                        newError = new Error(counterRow, "var-4): variable can not be inizialaze in conditional cycle");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }
                    break;
                case "assign":
                case "setVar":  //setVar name value;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim(), false);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 3;
                    break;
                case "func":
                case "print":
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), false);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 2;
                    break;
                case "deleteF":
                    if (deepLevel == 0) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 2;

                    } else {
                        newError = new Error(counterRow, "deleteF-2): can not be declared in conditional cycle or function");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }
                    break;
                case "delete":
                    if (deepLevel == 0) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 2;

                    } else {
                        newError = new Error(counterRow, "delete-2): can not be declared in conditional cycle or function");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }
                    break;
                case "for": //for [inValue-var] condition incrementalValue do commands; done;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim() + " " + splitSpace[counter + 2].trim() + " " + splitSpace[counter + 3].trim() + " " + splitSpace[counter + 4].trim(), true);
                    parsingCommandsList.addCommand(newCommand);

                    index = parsingCommandsList.size() - 1;
                    counter += 5;
                    if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {
                        newError = new Error(counterRow, "for-19): expected done;");
                        structuralErrorsList.addError(newError);
                        parsingCommandsList.remove(index);
                        counter = splitSpace.length;
                    }

                    break;
                case "done;":
                    counter++;
                    return (deepLevel - 1);
                case "initFunc":    //initFunc funcName do commands; done;
                    controlFuncDeepLevel = true;
                    if (deepLevel == 0) {
                        newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), true);
                        parsingCommandsList.addCommand(newCommand);
                        counter += 2;
                        index = parsingCommandsList.size() - 1;
                        if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {
                            //Quando la funzione tornerà avendo incontrato done; o fi; il controllo di chiusura chiuderà il ciclo
                            newError = new Error(counterRow, "initFunc-3): expected done;");
                            structuralErrorsList.addError(newError);
                            parsingCommandsList.remove(index);
                            counter = splitSpace.length;
                        }
                    } else {
                        counter = splitSpace.length;
                        newError = new Error(counterRow, "initFunc-4): function can not be inizialaze in conditional cycle or function");
                        structuralErrorsList.addError(newError);
                    }
                    controlFuncDeepLevel = false;
                    break;
                case "while":   //while condition do commands; done;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), true);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 2;
                    index = parsingCommandsList.size() - 1;

                    if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {
                        //Quando la funzione tornerà avendo incontrato done; o fi; il controllo di chiusura chiuderà il ciclo

                        newError = new Error(counterRow, "while-8): expected done;");
                        structuralErrorsList.addError(newError);
                        parsingCommandsList.remove(index);
                        counter = splitSpace.length;
                    }
                    break;
                case "elseif":  //elseif condition then commands; done;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), true);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 2;
                    index = parsingCommandsList.size() - 1;
                    if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {

                        newError = new Error(counterRow, "elseif-3): expected done;");
                        structuralErrorsList.addError(newError);
                        parsingCommandsList.remove(index);
                        counter = splitSpace.length;
                    }
                    break;
                case "if":  //if condition then commands; done;
                    newCommand = new Command(splitSpace[counter].trim() + " " + splitSpace[counter + 1].trim(), true);
                    parsingCommandsList.addCommand(newCommand);
                    counter += 2;
                    index = parsingCommandsList.size() - 1;
                    if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {
                        newError = new Error(counterRow, "if-3): expected done;");
                        structuralErrorsList.addError(newError);
                        parsingCommandsList.remove(index);
                        counter = splitSpace.length;
                    }
                    break;
                case "clean;":
                case "reset;":
                case "authors;":
                case "log;":
                    if ((deepLevel == 0)) {
                        newCommand = new Command(splitSpace[counter].trim(), false);
                        parsingCommandsList.addCommand(newCommand);
                        counter++;
                    } else {
                        newError = new Error(counterRow, "System command): can not be inizialaze here");
                        structuralErrorsList.addError(newError);
                        counter = splitSpace.length;
                    }

                    break;
                case "else":    //else commands; done;
                    newCommand = new Command(splitSpace[counter].trim(), true);
                    parsingCommandsList.addCommand(newCommand);
                    counter++;
                    index = parsingCommandsList.size() - 1;
                    if (parser(deepLevel + 1, parsingCommandsList.getCommand(index).commandsList) != deepLevel) {

                        newError = new Error(counterRow, "else-0): expected done;");
                        structuralErrorsList.addError(newError);
                        parsingCommandsList.remove(index);
                        counter = splitSpace.length;
                    }
                    break;
                default:
                    newError = new Error(counterRow, splitSpace[counter] + " commands not recognized");
                    structuralErrorsList.addError(newError);
                    counter = splitSpace.length;
                    break;
            }
            counterRow++;
        }
        return -1;
    }

    public boolean logicalAnalyze(CommandsList commandsListToAnalyze, VariablesList variablesListToUse) {
        int commandsCounter = 0;
        while (commandsCounter < commandsListToAnalyze.size()) {
            Error newError;
            int index;
            String split[];
            Command commandToAnalyze = commandsListToAnalyze.getCommand(commandsCounter);
            String splittingSpace[] = commandToAnalyze.command.split(" ");
            switch (splittingSpace[0]) {
                case "initVar":
                    if (variablesListToUse.searchByName(splittingSpace[2]) != -1) {
                        newError = new Error(counterRow, "initVar-6): variable " + splittingSpace[2] + " alredy declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    variablesListToUse.addVariable(new Variable(splittingSpace[1], splittingSpace[2], splittingSpace[3]));
                    commandsCounter++;
                    break;
                case "var":
                    if (variablesListToUse.searchByName(splittingSpace[2]) != -1) {
                        newError = new Error(counterRow, "var-5): variable " + splittingSpace[2] + " alredy declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    variablesListToUse.addVariable(new Variable(splittingSpace[1], splittingSpace[2]));
                    commandsCounter++;
                    break;
                case "initArray":
                    if (variablesListToUse.searchByName(splittingSpace[2]) != -1) {
                        newError = new Error(counterRow, "initArray-7): variable " + splittingSpace[2] + " alredy declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    variablesListToUse.addVariable(new Variable(splittingSpace[1], splittingSpace[2], Integer.parseInt(splittingSpace[3])));
                    commandsCounter++;
                    break;
                case "setArray":
                    index = variablesListToUse.searchByName(splittingSpace[1]);
                    if (index == -1) {
                        newError = new Error(counterRow, "setArray-3): variable " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    if (!typeString(splittingSpace[1], variablesListToUse).equals(typeString(splittingSpace[3], variablesListToUse))) {
                        newError = new Error(counterRow, "setArray-4): " + splittingSpace[1] + " " + splittingSpace[3] + "  types not compatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    if (isNumeric(splittingSpace[2])) {
                        if (variablesListToUse.getVariable(index).index < Integer.parseInt(splittingSpace[2])) {
                            newError = new Error(counterRow, "setArray-5): " + splittingSpace[2] + " index is > of the dimension of the array");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    } else {
                        if (typeString(splittingSpace[2], variablesListToUse).equals("String")) {
                            newError = new Error(counterRow, "setArray-6): " + splittingSpace[2] + " index must be a variable of type numeric");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    }
                    commandsCounter++;
                    break;
                case "setVar":
                    index = variablesListToUse.searchByName(splittingSpace[1]);
                    if (index == -1) {
                        newError = new Error(counterRow, "setVar-3): variable " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    if (!typeString(splittingSpace[1], variablesListToUse).equals(typeString(splittingSpace[2], variablesListToUse))) {
                        newError = new Error(counterRow, "setVar-4): " + splittingSpace[1] + " " + splittingSpace[3] + " types not compatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "delete":
                    if (variablesListToUse.searchByName(splittingSpace[1]) == -1) {
                        newError = new Error(counterRow, "delete-3): variable " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "deleteF":
                    if (functionsList.searchFunction(splittingSpace[1]) == -1) {
                        newError = new Error(counterRow, "deleteF-3): function " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "op":
                    index = variablesListToUse.searchByName(splittingSpace[1]);
                    if (index == -1) {
                        newError = new Error(counterRow, "op-4): variable " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    if (typeString(splittingSpace[1], variablesListToUse).equals("String")) {
                        if (!splittingSpace[3].equals("+")) {
                            newError = new Error(counterRow, "op-5): " + splittingSpace[1] + " for type String can only use +");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    } else {
                        if (typeString(splittingSpace[2], variablesListToUse).equals("String") || typeString(splittingSpace[4], variablesListToUse).equals("String")) {
                            newError = new Error(counterRow, "op-6): " + splittingSpace[2] + " " + splittingSpace[4] + " type not compatible");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    }
                    commandsCounter++;
                    break;
                case "assign":
                    String type1;
                    String type2;
                    String indexArray;
                    if (isArray(splittingSpace[1])) {
                        String name = nameArray(splittingSpace[1]);
                        index = variablesListToUse.searchByName(name);
                        if (index == -1) {
                            newError = new Error(counterRow, "assign-9): variable " + splittingSpace[1] + " not declared");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                        type1 = typeString(name, variablesListToUse);
                        indexArray = indexArray(splittingSpace[1]);
                        if (typeString(indexArray, variablesListToUse).equals("String")) {
                            newError = new Error(counterRow, "assign-10): " + splittingSpace[1] + " index array must be numeric type");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    } else {
                        type1 = typeString(splittingSpace[1], variablesListToUse);

                    }
                    if (isArray(splittingSpace[2])) {
                        String name = nameArray(splittingSpace[2]);
                        index = variablesListToUse.searchByName(name);
                        if (index == -1) {
                            newError = new Error(counterRow, "assign-11): variable " + splittingSpace[2] + " not declared");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                        type2 = typeString(name, variablesListToUse);
                        indexArray = indexArray(splittingSpace[2]);
                        if (typeString(indexArray, variablesListToUse).equals("String")) {
                            newError = new Error(counterRow, "assign-12 " + splittingSpace[2] + " index array must be numeric type");
                            logicalErrorsList.addError(newError);
                            return false;
                        }
                    } else {
                        type2 = typeString(splittingSpace[2], variablesListToUse);

                    }
                    if (!type1.equals(type2)) {
                        newError = new Error(counterRow, "assign-13): types are incompatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "initFunc":
                    index = functionsList.searchFunction(splittingSpace[1]);
                    if (index != -1) {
                        newError = new Error(counterRow, "initFunc-5): function " + splittingSpace[1] + " alredy declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    VariablesList temp = new VariablesList();
                    temp.addList(variablesList);
                    if (!logicalAnalyze(commandToAnalyze.commandsList, temp)) {
                        return false;
                    }
                    functionsList.addFunction(new Function(splittingSpace[1], commandToAnalyze.commandsList));
                    commandsCounter++;
                    break;
                case "func":
                    if (functionsList.searchFunction(splittingSpace[1]) == -1) {
                        newError = new Error(counterRow, "func-2): function " + splittingSpace[1] + " not declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "for":
                    split = splittingSpace[2].split("=");
                    if (variablesListToUse.searchByName(split[0]) != -1) {
                        newError = new Error(counterRow, "for-20): variable " + split[0] + " alredy declared");
                        logicalErrorsList.addError(newError);
                        return false;
                    } else {

                        variablesListToUse.addVariable(new Variable(splittingSpace[1], split[0], split[1]));
                        index = variablesListToUse.size() - 1;
                        if (!ifCondition(splittingSpace[3], variablesListToUse)) {
                            newError = new Error(counterRow, "for-21): " + splittingSpace[3] + " condition types are not compatible");
                            logicalErrorsList.addError(newError);
                            return false;
                        } else {

                            split = splittingSpace[3].split("_");
                            if (conditionType(splittingSpace[3], variablesListToUse).equals("String") && (split[1].contains(">") || split[1].contains("<"))) {
                                newError = new Error(counterRow, "for-22): " + splittingSpace[3] + " cond operator incompatible with String");
                                logicalErrorsList.addError(newError);
                                return false;
                            } else {

                                if (!logicalAnalyze(commandToAnalyze.commandsList, variablesListToUse)) {
                                    return false;
                                } else {
                                    variablesListToUse.remove(index);
                                }
                            }
                        }
                    }
                    commandsCounter++;
                    break;
                case "while":
                    if (!ifCondition(splittingSpace[1], variablesListToUse)) {
                        newError = new Error(counterRow, "while-9): " + splittingSpace[1] + " cond types not compatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    } else {
                        split = splittingSpace[1].split("_");
                        if (typeString(split[0], variablesListToUse).equals("String") && (split[1].contains(">") || split[1].contains("<"))) {
                            newError = new Error(counterRow, "while-10): " + splittingSpace[1] + " cond operator incompatible with type String");
                            logicalErrorsList.addError(newError);
                            return false;
                        } else {
                            if (!logicalAnalyze(commandToAnalyze.commandsList, variablesListToUse)) {
                                return false;
                            }
                        }
                    }
                    commandsCounter++;
                    break;
                case "elseif":
                    if (!ifCondition(splittingSpace[1], variablesListToUse)) {
                        newError = new Error(counterRow, "elseif-4): " + splittingSpace[1] + " cond types not compatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    } else {
                        split = splittingSpace[1].split("_");
                        if (typeString(split[0], variablesListToUse).equals("String") && (split[1].contains(">") || split[1].contains("<"))) {
                            newError = new Error(counterRow, "elseif-5): " + splittingSpace[1] + " cond operator incompatible with type String");
                            logicalErrorsList.addError(newError);
                            return false;
                        } else {
                            if (!logicalAnalyze(commandToAnalyze.commandsList, variablesListToUse)) {
                                return false;
                            }
                        }
                    }
                    commandsCounter++;
                    break;
                case "if":
                    if (!ifCondition(splittingSpace[1], variablesListToUse)) {
                        newError = new Error(counterRow, "if-4): " + splittingSpace[1] + " cond types not compatible");
                        logicalErrorsList.addError(newError);
                        return false;
                    } else {
                        split = splittingSpace[1].split("_");
                        if (typeString(split[0], variablesListToUse).equals("String") && (split[1].contains(">") || split[1].contains("<"))) {
                            newError = new Error(counterRow, "if-5): " + splittingSpace[1] + " cond operator incompatible with type String");
                            logicalErrorsList.addError(newError);
                            return false;
                        } else {
                            if (!logicalAnalyze(commandToAnalyze.commandsList, variablesListToUse)) {
                                return false;
                            }
                        }
                    }
                    commandsCounter++;
                    break;
                case "else":
                    if (!logicalAnalyze(commandToAnalyze.commandsList, variablesListToUse)) {
                        return false;
                    }
                    commandsCounter++;
                    break;
                case "clean;":
                case "reset;":
                case "authors;":
                case "print":
                case "log;":
                    commandsCounter++;
                    break;

            }
            counterRow++;
        }
        return true;
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

    private String typeString(String format, VariablesList variablesList) {
        if (isNumeric(format)) {
            return "int";
        } else {
            int index = variablesList.searchByName(format);
            if (index != -1) {
                if (variablesList.getVariable(index).type.equals("String")) {
                    return "String";
                } else {
                    return "int";
                }
            } else {
                return "String";
            }
        }
    }

    private boolean mathInList(String math) {
        for (int i = 0; i < mathOperator.length; i++) {
            if (math.equals(mathOperator[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean typeInList(String type) {
        for (int i = 0; i < typeOfVariable.length; i++) {

            if (type.equals(typeOfVariable[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean condInList(String cond) {
        for (int i = 0; i < condOperator.length; i++) {
            if (cond.equals(condOperator[i])) {
                return true;
            }
        }
        return false;
    }

    private void printAll(CommandsList printingCommandsList, String tab) {
        for (int i = 0; i < printingCommandsList.size(); i++) {
            System.out.println(tab + printingCommandsList.getCommand(i).command);
            if (printingCommandsList.getCommand(i).commandsList != null) {
                printAll(printingCommandsList.getCommand(i).commandsList, tab + "\t");
            }

        }

    }

    private String conditionType(String condition, VariablesList variablesList) {
        String split[] = condition.split("_");
        return typeString(split[0], variablesList);
    }

    private boolean ifCondition(String condition, VariablesList variablesList) {
        String split[] = condition.split("_");

        return typeString(split[0], variablesList).equals(typeString(split[2], variablesList));

    }

}
