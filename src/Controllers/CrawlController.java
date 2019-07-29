package Controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.Crawler;
import Utils.Tree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class CrawlController {

    private File curFile;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField startFolderField;

    @FXML
    private Button browseButton;

    @FXML
    private TextField extensionField;

    @FXML
    private TextArea textField;

    @FXML
    private Button searchButton;

    @FXML
    void initialize() {
        browseButton.setOnAction(actionEvent -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Stage stage = (Stage) browseButton.getScene().getWindow();
            File file = directoryChooser.showDialog(stage);
            if(file != null) {
                startFolderField.setText(file.getPath());
                curFile = file;
            }
        });

        searchButton.setOnAction(actionEvent -> {

            String extension = extensionField.getText();
            if(extension == null)
                extension = "log";

            Tree crawlResult = Crawler.crawl(curFile.getAbsolutePath(), textField.getText(), extension);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML/result.fxml"));
            ResultController controller = loader.<ResultController>getController();
            controller.setCrawlResult(crawlResult);
            loader.setController(controller);
            try {
                loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }


            Stage stage = new Stage();
            stage.setTitle("Result");
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        });

    }
}
