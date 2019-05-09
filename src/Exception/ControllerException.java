package Exception;

public class ControllerException  extends RuntimeException{
    private String mes;
    public ControllerException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }
}