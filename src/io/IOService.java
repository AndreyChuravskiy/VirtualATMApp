package io;

import java.util.Scanner;

public class IOService {
    private Scanner scanner;

    public IOService() {
        this.scanner = new Scanner(System.in);
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}
