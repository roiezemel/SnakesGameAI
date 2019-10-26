package utils;

public class FileModeException extends Throwable {

    String message;

    public FileModeException(String message){
        this.message = message;
    }

    public void printMessage(){
        tools.print(ConsoleColors.RED + "FileModeException: " + message + ConsoleColors.RESET + "\n");
    }

}
