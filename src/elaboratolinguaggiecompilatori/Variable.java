package elaboratolinguaggiecompilatori;

public class Variable {

    public String type;
    public String name;
    public String value;
    public String arrayValue[];
    public int index = 0;

    public Variable(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
        this.value = new String("");
    }

    public Variable(String type, String name, int index) {
        this.type = type;
        this.name = name;
        this.index = index;
        this.arrayValue = new String[this.index];
        for (int i = 0; i < this.index; i++) {
            this.arrayValue[i] = "";
        }
    }

    public boolean isNull() {
        return this.value.equals("");
    }

    public boolean isNull(int index) {
        return this.arrayValue[index].equals("");
    }



    public boolean setValue(String value, int index) {
        if(index>this.index) {
            return false;
        }
        switch (this.type) {
            case "int":
                this.arrayValue[index] = "" + ((int) Double.parseDouble(value));
                break;
            case "float":
                this.arrayValue[index] = "" + ((float) Double.parseDouble(value));
                break;
            case "double":
                this.arrayValue[index] = "" + Double.parseDouble(value);
                break;
            default:
                this.arrayValue[index] = value;
        }
        return true;
    }


    public String getValue(int index) {
        if(index>this.index) {
            return "wtf";
        }
        if(this.arrayValue[index].equals("")) {
            return "";
        }
        else {
            
        
        switch (this.type) {
            case "int":
                return ("" + ((int) Double.parseDouble(this.arrayValue[index])));

            case "float":
                return ("" + ((float) Double.parseDouble(this.arrayValue[index])));

            case "double":
                return ("" + Double.parseDouble(this.arrayValue[index]));

            default:
                return this.value;
        }
        }
    }

}
