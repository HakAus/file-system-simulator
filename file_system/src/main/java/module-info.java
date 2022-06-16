module file_system {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires transitive javafx.graphics;
    requires javafx.base;

    opens file_system to javafx.fxml;

    exports file_system;
}
