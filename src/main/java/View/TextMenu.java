package View;

import Model.adt.MyDict;

import java.util.Scanner;

public class TextMenu {
    private final MyDict<String, Command> commands;

    public TextMenu() { commands = new MyDict<String, Command>(); }

    public void addCommand(Command c){ commands.add(c.getKey(),c);}

    private void printMenu() {
        for (Command com : commands.getAllValues()) {
            String line = String.format("%s:\n%s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void show() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.lookup(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}