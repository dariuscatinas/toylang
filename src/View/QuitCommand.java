package View;

public class QuitCommand extends Command{

    public QuitCommand(String id, String desc){
        super(id, desc);
    }

    public void execute(){
        System.exit(0);
    }
}
