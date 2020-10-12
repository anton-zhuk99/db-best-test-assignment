package ta.dbbest.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import ta.dbbest.App;
import ta.dbbest.persistance.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PrimaryController {

    @FXML
    private Label filenameLabel;

    @FXML
    private Button chooseFileButton;

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File dataFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

        filenameLabel.setText(dataFile.getName());

        String sql = "DROP TABLE IF EXISTS DATA ;" +
                "CREATE TABLE DATA AS SELECT * FROM CSVREAD('" + dataFile.getAbsolutePath() + "', null, 'fieldSeparator=;')";
        
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void goNext(ActionEvent event) {
        try {
            App.setRoot("secondary");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    void openReference(ActionEvent event) {
        // TODO
    }

}