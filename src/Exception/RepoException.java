package Exception;

public class RepoException  extends RuntimeException{
    private String mes;
    public RepoException(String mes){
        super(mes);
        this.mes = mes;
    }
    public String toString(){
        return mes;
    }
}
