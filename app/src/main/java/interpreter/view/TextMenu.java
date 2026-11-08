package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    private final Scanner scanner = new Scanner(System.in);
    private IStmt selected = null;

    public void show() {
        while (true) {
            System.out.println("1.Select a program");
            System.out.println("2.Run the selected program");
            System.out.println("0.Quit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> selectProgram();
                case "2" -> runSelectedProgram();
                case "0" -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void selectProgram() {
        System.out.println("1."+Examples.EX1);
        System.out.println("2."+Examples.EX2);
        System.out.println("3."+Examples.EX3);
        String program = scanner.nextLine();
        switch (program) {
            case "1" -> selected = Examples.EX1;
            case "2" -> selected = Examples.EX2;
            case "3" -> selected = Examples.EX3;
            default -> System.out.println("Invalid program");
        }
    }

    private void runSelectedProgram() {
        if (selected == null) {
            System.out.println("Please select a program");
            return;
        }
        PrgState state = PrgState.of(selected);
        Controller controller = new Controller(new Repository(state,"log.txt"),true);
        try {
            controller.allStep();
        } catch (MyException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

}