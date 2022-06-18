package file_system;

public class CommandHandler {

    public static String Command;
    private FileSystem FileSystem;

    CommandHandler(FileSystem fileSystem) {
        Command = "";
        FileSystem = fileSystem;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public void execute() {
        switch (Command) {
            case Constants.CREATE:
                SimulationFile directory = getFileSystem().currentDirectory;
                FileSystem.createFile(directory, "test", "test");
                break;
            
            case Constants.PROPS:
                System.out.println("Properties");
                
                break;

            default:
                break;
        }
    }

    public FileSystem getFileSystem() {
        return FileSystem;
    }
}
