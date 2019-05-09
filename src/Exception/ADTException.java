package Exception;

public class ADTException extends RuntimeException{
    private String mes;
    public ADTException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }
}
