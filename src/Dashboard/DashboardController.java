/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import deriction_des_impots.Deriction_des_impots;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelhamid
 */
public class DashboardController implements Initializable {


    @FXML
    private AnchorPane Parents;

    @FXML
    private JFXButton settings;

    @FXML
    private VBox VBox;

    @FXML
    private AnchorPane Parent;

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton reduire;

    @FXML
    private JFXDrawer sliders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        for (Node n : VBox.getChildren()) {
            n.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                switch (n.getAccessibleText()) {

                    case "Dashboard":
                        try {

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource(""));
                            Parent.getChildren().add(door);
                            break;

                        } catch (IOException ex) {

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Propriétés":
                        try {

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource("/Propriétés/Propriétés.fxml"));
                            Parent.getChildren().add(door);
                            break;

                        } catch (IOException ex) {

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Propriétaires":
                        try {

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource("/proprietaire/PropriétairesFXML.fxml"));
                            Parent.getChildren().add(door);
                            break;

                        } catch (IOException ex) {

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Contrats":
                        try {

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource("/Contract/Contrat.fxml"));
                            Parent.getChildren().add(door);
                            break;

                        } catch (IOException ex) {

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Bénéficiaires":
                        try {

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource("/Bénéficiaires/Bénéficiaires.fxml"));
                            Parent.getChildren().add(door);
                            break;

                        } catch (IOException ex) {

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Paiements":
                        try{

                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane door = FXMLLoader.load(getClass().getResource("/Paiements/Paiement.fxml"));
                            Parent.getChildren().add(door);
                            break;

                        }catch(IOException ex){

                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }
                    case "Scanner":
                        try {
                            Parent.toFront();
                            Parent.getChildren().clear();
                            AnchorPane pane=FXMLLoader.load(getClass().getResource("/Dematerialization/viewFacture.fxml"));
                            Parent.getChildren().add(pane);
                            break;
                        }catch (IOException ex)
                        {
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(ex.getMessage());
                            alert.setContentText(ex.getLocalizedMessage());
                            alert.show();
                            break;
                        }


                }
            });
        }
        try {
            AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/Dashboard/settings.fxml"));
            sliders.setSidePane(anchorPane);

            for (Node node:anchorPane.getChildren()){

                node.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                    switch (node.getAccessibleText()){
                        case "return":
                            sliders.hide();
                            sliders.toBack();
                            break;
                    }
                });

            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }


        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.exit();
        });
        reduire.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });

        makeStageDrageable();
    }

    @FXML
    void onDraw(ActionEvent event) {

        sliders.toFront();
        sliders.draw();

    }

    private double xOffset = 0;
    private double yOffset = 0;
    private void makeStageDrageable() {
        Parents.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        Parents.setOnMouseDragged((MouseEvent event) -> {
            Deriction_des_impots.stage.setX(event.getScreenX() - xOffset);
            Deriction_des_impots.stage.setY(event.getScreenY() - yOffset);
            Deriction_des_impots.stage.setOpacity(0.7f);
        });
        Parents.setOnDragDone((e) -> {
            Deriction_des_impots.stage.setOpacity(1.0f);
        });
        Parents.setOnMouseReleased((e) -> {
            Deriction_des_impots.stage.setOpacity(1.0f);
        });
    }
    
}
