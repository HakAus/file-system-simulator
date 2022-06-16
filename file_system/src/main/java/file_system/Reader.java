package file_system;

import java.io.File;

public class Reader {

    public String readFile(SimulationFile file) {
        FileSectorList list = new FileSectorList();
        Sector tmp = list.getOrigin();
        StringBuilder builder = new StringBuilder();

        File discFile = new File(Constants.DISC_PATH);

        // while (tmp.getPosition() != file.getEnd()) {
        // builder.append(str)
        // }
        // return
        return "";
    }

}
