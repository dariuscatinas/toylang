package Exception;


public class ExpressionException  extends RuntimeException{
    private String mes;
    public ExpressionException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }

}
