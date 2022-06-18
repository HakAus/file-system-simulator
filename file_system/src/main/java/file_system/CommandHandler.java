package file_system;

public class CommandHandler {

    public static String command;
    private FileSystem fileSystem;

    CommandHandler(FileSystem pFileSystem) {
        command = "";
        fileSystem = pFileSystem;
    }

    public void setCommand(String pCommand) {
        command = pCommand;
    }

    public void execute() {
        switch (command) {
            case Constants.CREATE:
                SimulationFile directory = FileSystem.currentDirectory;
                fileSystem.createFile(directory, "test", "test");
                break;

            case Constants.PROPS:
                System.out.println("Properties");

                break;

            case Constants.VIEW:
                SimulationFile file = FileSystem.currentFile;

            default:
                break;
        }
    }
}
