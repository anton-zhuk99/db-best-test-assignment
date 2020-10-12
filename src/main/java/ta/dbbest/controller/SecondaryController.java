package ta.dbbest.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import ta.dbbest.algorithm.Dijkstra;
import ta.dbbest.algorithm.Graph;
import ta.dbbest.algorithm.Node;
import ta.dbbest.model.InputRecord;
import ta.dbbest.model.OutputRecord;
import ta.dbbest.model.TableRecord;
import ta.dbbest.persistance.DatabaseConnection;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    @FXML
    private TableView<TableRecord> dataTable;

    @FXML
    private TableColumn<TableRecord, Integer> idXColumn;

    @FXML
    private TableColumn<TableRecord, Integer> idYColumn;

    @FXML
    private TableColumn<TableRecord, Integer> lengthColumn;

    @FXML
    private Label filenameLabel;

    @FXML
    private Button chooseFileButton;

    @FXML
    private TableView<OutputRecord> resultTable;

    @FXML
    private TableColumn<InputRecord, Boolean> existsColumn;

    @FXML
    private TableColumn<InputRecord, Integer> resultLengthColumn;

    private final DatabaseConnection databaseConnection;
    private final ObservableList<TableRecord> data;
    private final ObservableList<InputRecord> input;
    private final ObservableList<OutputRecord> output;

    {
        databaseConnection = new DatabaseConnection();
        data = FXCollections.observableArrayList();
        input = FXCollections.observableArrayList();
        output = FXCollections.observableArrayList();
    }

    private int getMaxIdx() {
        int maxX = data.get(0).getIdX();
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).getIdX() > maxX) {
                maxX = data.get(i).getIdX();
            }
        }

        int maxY = data.get(0).getIdY();
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).getIdY() > maxY) {
                maxY = data.get(i).getIdY();
            }
        }

        return Math.max(maxX, maxY);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idXColumn.setCellValueFactory(new PropertyValueFactory<>("idX"));
        idYColumn.setCellValueFactory(new PropertyValueFactory<>("idY"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("len"));

        try {
            Connection c = databaseConnection.getConnection();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM DATA";
            ResultSet rsData = s.executeQuery(sql);
            while (rsData.next()) {
                data.add(new TableRecord(
                        rsData.getInt(1),
                        rsData.getInt(2),
                        rsData.getInt(3))
                );
            }
            s.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dataTable.setItems(data);
    }

    @FXML
    void calculate(ActionEvent event) {

        try {
            Connection c = databaseConnection.getConnection();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM INPUT";
            ResultSet rsInput = s.executeQuery(sql);
            while (rsInput.next()) {
                input.add(new InputRecord(
                        rsInput.getInt(1),
                        rsInput.getInt(2)
                ));
            }
            s.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        int size = getMaxIdx();
        int[][] graph = new int[size][size];

        for (TableRecord tr:
                data) {
            graph[tr.getIdX() - 1][tr.getIdY() - 1] = tr.getLen();
        }

        List<Node> nodes = new LinkedList<>();
        for (int i = 1; i <= size; ++i) {
            nodes.add(new Node(i));
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (graph[i][j] != 0) {
                    nodes.get(i).addDestination(nodes.get(j), graph[i][j]);
                }
            }
        }

        Graph g = new Graph();
        for (Node n:
                nodes) {
            g.addNode(n);
        }

        for (InputRecord ir: input) {
            Graph gg = Dijkstra.calculateShortestPathFromSource(g, nodes.get(ir.getIdX() - 1));
            for (Node n: gg.getNodes()) {
                if (n.getName() == ir.getIdY()) {
                    if (n.getDistance() != Integer.MAX_VALUE) {
                        output.add(new OutputRecord(
                                true,
                                n.getDistance()
                        ));
                    } else {
                        output.add(new OutputRecord(
                                false,
                                0
                        ));
                    }
                }
            }
        }

        existsColumn.setCellValueFactory(new PropertyValueFactory<>("exists"));
        resultLengthColumn.setCellValueFactory(new PropertyValueFactory<>("len"));
        resultTable.setItems(output);
    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File inputFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

        String sql = "DROP TABLE IF EXISTS INPUT;" +
                "CREATE TABLE INPUT AS SELECT * FROM CSVREAD('" + inputFile.getAbsolutePath() + "', null, 'fieldSeparator=;')";

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

        filenameLabel.setText(inputFile.getName());
    }

    @FXML
    void exitApp(ActionEvent event) {
        // TODO
    }

    @FXML
    void showReference(ActionEvent event) {
        // TODO
    }

}