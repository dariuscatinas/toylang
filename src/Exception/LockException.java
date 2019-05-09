package Exception;

public class LockException extends RuntimeException{
    private String mes;
    public LockException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }
}
