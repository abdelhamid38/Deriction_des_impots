package Bénéficiaires;

import System_classes.Bénéficiaires;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Displayinfo implements Initializable {

    @FXML
    private AnchorPane Parent;

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton reduire;

    @FXML
    private Label Name;

    @FXML
    private Label Date_nss;

    @FXML
    private Label Adresse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeStageDrageable();
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BénéficiairesController.displaystage.hide();
        });
        reduire.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });



    }
    @FXML
    void getData(ActionEvent event) {
        Node node= (Node) event.getSource();
        Stage stage= (Stage) node.getScene().getWindow();
        Bénéficiaires bénéficiaires= (Bénéficiaires) stage.getUserData();

        Name.setText(bénéficiaires.getName());
        Date_nss.setText(bénéficiaires.getDate_nss()+"");
        Adresse.setText(bénéficiaires.getAdress());
    }

    private double xOffset = 0;
    private double yOffset = 0;
    private void makeStageDrageable() {
        Parent.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        Parent.setOnMouseDragged((MouseEvent event) -> {
            BénéficiairesController.displaystage.setX(event.getScreenX() - xOffset);
            BénéficiairesController.displaystage.setY(event.getScreenY() - yOffset);
            BénéficiairesController.displaystage.setOpacity(0.7f);
        });
        Parent.setOnDragDone((e) -> {
            BénéficiairesController.displaystage.setOpacity(1.0f);
        });
        Parent.setOnMouseReleased((e) -> {
            BénéficiairesController.displaystage.setOpacity(1.0f);
        });
    }
}
