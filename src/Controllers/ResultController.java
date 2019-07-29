package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.Tree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class ResultController {

    private static Tree crawlResult;

    public static void setCrawlResult(Tree crawlResult) {
        ResultController.crawlResult = crawlResult;
    }

    private static TreeItem<String> createTree(Tree tree) {

        if (tree.children.size() == 0) {
            return new TreeItem<>(tree.node);
        } else {
            TreeItem<String> result = new TreeItem<>(tree.node);
            for (Tree tree1 : tree.children) {
                result.getChildren().add(createTree(tree1));
            }
            return result;
        }
    }

    private String createPath(TreeItem<String> treeItem) {
        String ans = "";
        TreeItem<String> current = treeItem;
        while(current.getParent() != null) {
            ans = current.getValue() + "/" +  ans;
            current = current.getParent();
        }
        ans = current.getValue() + "/" + ans;
        return ans;
    }

    private void textFilePreview(File file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String s;
            while((s = br.readLine()) != null) {
                textPreview.setText(textPreview.getText() + s + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TreeView<String> fileTree;

    @FXML
    private TextArea textPreview;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.close();
        });

        //crawlResult.clearTree();
        fileTree.setRoot(createTree(crawlResult));
        fileTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String path = createPath(selectedItem);
                File file = new File(path);
                if(!file.isDirectory())
                    textFilePreview(file);
            }

        });

    }
}
