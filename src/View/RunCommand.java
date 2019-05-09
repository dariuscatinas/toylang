package View;

import Controller.Controller;
import Exception.ControllerException;
import Exception.CommandException;

public class RunCommand extends Command{

    private Controller c;
    public RunCommand(String key, String descr,Controller c){
        super(key, descr);
        this.c = c;
    }

    public void execute(){
        try {
            c.allStep();
        }
        catch (ControllerException cex){
            throw new CommandException(cex.toString());
        }

    }

}
