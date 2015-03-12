package elaboratolinguaggiecompilatori;

public class Error {

    public int row;
    public String code;
    public String typeError;

    public Error(int row, String code, int typeError) {
        this.row = row;
        this.code = code;
        this.typeError = getError(typeError);
    }
    
    public Error(int row, String code) {
        this.row = row;
        this.code = code;

    }

    private String getError(int index) {
        switch (index) {
            case 1:
                return "type of variable did not recognized";
            case 2:
                return "variable alredy declared";
            case 3:
                return "declared type is not String";
            case 4:
                return "variable not declared";
            case 5:
                return "expected ;";
            case 6:
                return "function not declared";
            case 7:
                return "math operator not recognized";
            case 8:
                return "math operator not compatible with String";
            case 9:
                return "String can not cast to a number format";
            case 10:
                return "cycle not closed";
            case 11:
                return "expected done;";
            case 12:
                return "command not recognize";
            case 13:
                return "variable can not inizialize here";
            case 14:
                return "function can not inizialize here";
            case 15:
                return "function alredy declared";
            case 16:
                return "impossible to divide by 0";
            case 17:
                return "variable name can not contain _";
            case 30:
                return "the condition format is wrong";
            case 31:
                return "the operator of condition is wrong";
            case 32:
                return "the values in condition are of different type";
            case 33:
                return "for String type this operator can not use";
            case 22:
                return "String can operate only with +";
            case 23:
                return "expected var_=_value";
            case 24:
                return "var and value types are different";
            case 25:
                return "incremental value not correct";
            case 26:
                return "expected do";
            case 27:
                return "for syntax is wrong";
            case 40:
                return "print syntax is wrong";
            case 41:
                return "func syntax is wrong";
            case 42:
                return "deleteF syntax is wrong";
            case 43:
                return "delete syntax is wrong";
            case 44:
                return "setVar syntax is wrong";
            case 45:
                return "var syntax is wrong";
            case 46:
                return "initVar syntax is wrong";
            case 47:
                return "op syntax is wrong";
            case 48:
                return "assign syntax is wrong";
            case 49:
                return "initFunc syntax is wrong";
            case 50:
                return "while syntax is wrong";
            case 51:
                return "if/elseif syntax is wrong";

            default:
                return "";
        }
    }
}
