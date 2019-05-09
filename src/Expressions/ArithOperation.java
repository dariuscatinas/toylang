package Expressions;

public enum ArithOperation {
    ADD, SUB , MUL, DIV;

    @Override
    public String toString() {
        switch (this){
            case ADD:
                return "+";
            case SUB:
                return "-";
            case MUL:
                return "*";
            case DIV:
                return "/";

        }
        return null;
    }
}
