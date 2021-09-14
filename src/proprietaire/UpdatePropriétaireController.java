package proprietaire;

import Outils.Convert_date;
import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import System_classes.Propriétaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdatePropriétaireController implements Initializable {

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
    private TextField Tlf;

    @FXML
    private TextField Adress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeStageDrageable();

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            PropriétairesFXMLController.stage.hide();
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
        Propriétaires propriétaires= (Propriétaires) stage.getUserData();

        id.setText(propriétaires.getId()+"");
        Name.setText(propriétaires.getName());
        date_nss.setValue(propriétaires.getDate_nss().toLocalDate());
        Adress.setText(propriétaires.getAdress());
        Tlf.setText(propriétaires.getTlf());


    }

    @FXML
    void save(MouseEvent event) {

        try {
            Propriétaires propriétaires=new Propriétaires(
                    Integer.parseInt(id.getText()),
                    Name.getText(),
                    Convert_date.CovertToSqlDate(Convert_date.Convert(date_nss.getValue())),
                    Adress.getText(),
                    Tlf.getText()
            );
            Connection connection= (Connection) DB_Connect.getConnection();
            String SQL="UPDATE `proprietaire` SET `nom_prenom_or_RS`=?,`date_nss`=?,`Adress`=?,`telephone`=? WHERE `id`="+Integer.parseInt(id.getText());
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);

            statement.setString(1,propriétaires.getName());
            statement.setDate(2,propriétaires.getDate_nss());
            statement.setString(3,propriétaires.getAdress());
            statement.setString(4,propriétaires.getTlf());

            statement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération réusie");
            alert.setContentText("Le propriétaire a été Modifier .");
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setOnHidden(event1 -> {
                PropriétairesFXMLController.stage.hide();
            });
            alert.setHeight(150);
            alert.setWidth(200);
            DialogPane dialogPane=alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/proprietaire/addbeneficaire.css").toExternalForm()
            );

            dialogPane.getStyleClass().add("dialog-pane");
            alert.show();

        } catch (ClassNotFoundException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le propriétaires n'est pas Modifier.");
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
            PropriétairesFXMLController.stage.setX(event.getScreenX() - xOffset);
            PropriétairesFXMLController.stage.setY(event.getScreenY() - yOffset);
            PropriétairesFXMLController.stage.setOpacity(0.7f);
        });
        Parents.setOnDragDone((e) -> {
            PropriétairesFXMLController.stage.setOpacity(1.0f);
        });
        Parents.setOnMouseReleased((e) -> {
            PropriétairesFXMLController.stage.setOpacity(1.0f);
        });
    }
}
