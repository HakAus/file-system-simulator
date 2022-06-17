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
                FileSystem.createFile("test", "test");
                break;

            default:
                break;
        }
    }
}
