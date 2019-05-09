package View;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import Exception.CommandException;

public class CommandMenu {
    private List<Command> commands;

    public CommandMenu(){
        commands = new LinkedList<Command>();
    }

    public CommandMenu(List<Command> cmd){
        commands = cmd;
    }

    public void addCommand(Command cmd){
        commands.add(cmd);
    }
    private void printMenu(){
        for (Command cmd : commands){
            System.out.println(cmd.getId() + ". " + cmd.getDescription());
        }
    }

    private Command chooseCommand(Scanner scanner){
        System.out.println("Give option: ");
        String key = scanner.nextLine();
        for (Command cmd: commands){
            if (cmd.getId().equals(key)){
                commands.remove(cmd);
                return cmd;
            }
        }
        return null;
    }

    public void start(){
        boolean alive = true;
        Scanner sc = new Scanner(System.in);
        while(alive){
            printMenu();
            Command cmd = chooseCommand(sc);
            if( cmd != null){
                try {
                    cmd.execute();
                }
                catch (CommandException cex){
                    System.out.println(cex.toString());
                }
            }
            else{
                System.out.println("Invalid command");
            }
        }

    }

}
