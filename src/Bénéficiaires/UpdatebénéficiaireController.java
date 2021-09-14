package Bénéficiaires;

import Outils.Convert_date;
import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdatebénéficiaireController implements Initializable {

    @FXML
    private AnchorPane Parents;

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton reduire;

    @FXML
    private TextField id;

    @FXML
    private TextField Name;

    @FXML
    private JFXDatePicker date_nss;

    @FXML
    private TextField wilaya;

    @FXML
    private TextField commune;

    @FXML
    private TextField Adress;

    @FXML
    private TextField nationalite;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeStageDrageable();

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BénéficiairesController.editstage.hide();
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

        id.setText(bénéficiaires.getId()+"");
        Name.setText(bénéficiaires.getName());
        date_nss.setValue(bénéficiaires.getDate_nss().toLocalDate());
        Adress.setText(bénéficiaires.getAdress());
        wilaya.setText(bénéficiaires.getWilaya());
        commune.setText(bénéficiaires.getCommune());
        nationalite.setText(bénéficiaires.getNationalite());

    }

    @FXML
    void save(MouseEvent event) {

        try {
            Bénéficiaires bénéficiaires=new Bénéficiaires(
                    Integer.parseInt(id.getText()),
                    Name.getText(),
                    Convert_date.CovertToSqlDate(Convert_date.Convert(date_nss.getValue())),
                    Adress.getText(),
                    wilaya.getText(),
                    commune.getText(),
                    nationalite.getText()
            );
            Connection connection= (Connection) DB_Connect.getConnection();
            String SQL="UPDATE `beneficiaire` SET `nom_prenom_or_RS`=?,`date_nss`=?,`Adresse`=?,`wilaya`=?,`commune`=?,`nationalite`=? WHERE `id`="+Integer.parseInt(id.getText());
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);

            statement.setString(1,bénéficiaires.getName());
            statement.setDate(2,bénéficiaires.getDate_nss());
            statement.setString(3,bénéficiaires.getAdress());
            statement.setString(4,bénéficiaires.getWilaya());
            statement.setString(5,bénéficiaires.getCommune());
            statement.setString(6,bénéficiaires.getNationalite());

            statement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération réusie");
            alert.setContentText("Le bénéficiare a été Modifier .");
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setOnHidden(event1 -> {
                BénéficiairesController.editstage.hide();
            });
            alert.setHeight(150);
            alert.setWidth(200);
            DialogPane dialogPane=alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/Bénéficiaires/addbeneficaire.css").toExternalForm()
            );

            dialogPane.getStyleClass().add("dialog-pane");
            alert.show();

        } catch (ClassNotFoundException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le bénéficiare n'est pas Modifier.");
            alert.show();

        }

    }

    private double xOffset = 0;
    private double yOffset = 0;

    private void makeStageDrageable() {
        Parents.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        Parents.setOnMouseDragged((MouseEvent event) -> {
            BénéficiairesController.editstage.setX(event.getScreenX() - xOffset);
            BénéficiairesController.editstage.setY(event.getScreenY() - yOffset);
            BénéficiairesController.editstage.setOpacity(0.7f);
        });
        Parents.setOnDragDone((e) -> {
            BénéficiairesController.editstage.setOpacity(1.0f);
        });
        Parents.setOnMouseReleased((e) -> {
            BénéficiairesController.editstage.setOpacity(1.0f);
        });
    }
}
