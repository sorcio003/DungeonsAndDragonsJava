module com.dnd.it {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.dnd.it to javafx.fxml;
    exports com.dnd.it;
}
