package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class file extends tools{

    Scanner fileReader;
    Formatter formatter;

    /**
     * Create new file.
     * @param path - file location
     * @param mode - 'w' for write mode, 'r' for read mode.
     */
    public file(String path, char mode){
        if(tostring(mode).equals("w")){
            try {
                formatter = new Formatter(path);
            } catch (FileNotFoundException e) {
                print(e.getMessage());
            }
        }

        else if(tostring(mode).equals("r")){
            File file = new File(path);
            try {
                fileReader = new Scanner(file);
            } catch (FileNotFoundException e) {
                print(e.getMessage());
            }
        }

        else {
            try {
                throw new FileModeException("Unexpected mode: '" + mode + "'. Modes available: 'w', 'r'.");
            } catch (FileModeException e) {
                e.printMessage();
            }
        }
    }

    /**
     * Returns the entire file's content.
     * @return - A String containing the file's content.
     */
    public String read(){
        if(fileReader == null) {
            try {
                throw new FileModeException("Can't read a file in 'write' mode. Use mode 'r' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return null;
            }
        }
        return fileReader.useDelimiter("\\A").next();
    }

    /**
     * Returns the next line in the file.
     * @return - Next line. If reached the end: "End".
     */
    public String readline(){
        if(fileReader == null) {
            try {
                throw new FileModeException("Can't read a file in 'write' mode. Use mode 'r' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return null;
            }
        }
        if(fileReader.hasNextLine())
            return fileReader.nextLine();
        else
            return "End";
    }

    /**
     * Returns the next word in the file.
     * @return - Next word. If reached the end: "End".
     */
    public String readWord(){
        if(fileReader == null) {
            try {
                throw new FileModeException("Can't read a file in 'write' mode. Use mode 'r' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return null;
            }
        }
        if(fileReader.hasNext())
            return fileReader.next();
        else
            return "End";
    }

    /**
     * Checks if there is a next word.
     * @return
     */
    public boolean hasNext(){
        if(fileReader == null) {
            try {
                throw new FileModeException("Can't read a file in 'write' mode. Use mode 'r' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return false;
            }
        }
        return fileReader.hasNext();
    }

    /**
     * Checks if there is a next line.
     * @return
     */
    public boolean hasNextLine(){
        if(fileReader == null) {
            try {
                throw new FileModeException("Can't read a file in 'write' mode. Use mode 'r' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return false;
            }
        }
        return fileReader.hasNextLine();
    }

    /**
     * Append new String to the file.
     * @param value
     */
    public void write(String value){
        if(formatter == null) {
            try {
                throw new FileModeException("Can't write a file in 'read' mode. Use mode 'w' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return;
            }
        }
        formatter.format("%s", value);
    }

    /**
     * Append new values to the file using a format String.
     * @param format
     * @param values
     */
    public void format(String format, Object... values){
        if(formatter == null) {
            try {
                throw new FileModeException("Can't write a file in 'read' mode. Use mode 'w' instead.");
            } catch (FileModeException e) {
                e.printMessage();
                return;
            }
        }
        formatter.format(format, values);
    }

    /**
     * Closes the file. Should always be called after the file is not in use anymore.
     */
    public void close(){
        if (fileReader != null)
            fileReader.close();
        if (formatter != null)
            formatter.close();
    }
}
