module ta.dbbest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;

    opens ta.dbbest to javafx.fxml;
    exports ta.dbbest;
}