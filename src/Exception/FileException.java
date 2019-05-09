package Exception;

public class FileException extends RuntimeException{
private String mes;
public FileException(String mes){
        super(mes);
        this.mes = mes;
}
public String toString(){
        return mes;
}

}
