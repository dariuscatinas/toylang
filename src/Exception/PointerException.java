package Exception;

public class PointerException  extends RuntimeException{
    private String mes;
    public PointerException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }
}
