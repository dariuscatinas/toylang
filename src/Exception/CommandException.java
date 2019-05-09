package Exception;

public class CommandException extends RuntimeException {
    private String mes;

    public CommandException(String mes) {
        super(mes);
        this.mes = mes;
    }

    public String toString() {
        return mes;
    }
}
