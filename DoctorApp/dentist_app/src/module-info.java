/*
module gui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires json.simple;
    opens gui to javafx.fxml;
    opens main to javafx.fxml;
    exports gui;
    exports main;
}
*/

module gui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires json.simple;

    opens gui to javafx.fxml;
    opens main to javafx.fxml;

    exports gui;
    exports main;
}

