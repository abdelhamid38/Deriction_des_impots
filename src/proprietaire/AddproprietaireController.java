package proprietaire;

import Outils.Convert_date;
import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import System_classes.Propriétaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import deriction_des_impots.Deriction_des_impots;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddproprietaireController implements Initializable {

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
    private TextField Adress;

    @FXML
    private TextField Tlf;

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

        try {
            Connection connection= (Connection) DB_Connect.getConnection();
            String SQL="SELECT id FROM `proprietaire` ORDER BY id DESC limit 1";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
            ResultSetImpl resultSet =(ResultSetImpl) statement.executeQuery();
            if (resultSet.next()){
                int i=resultSet.getInt(1);
                id.setText(i+1+"");
            }else id.setText("1");


        }catch (SQLException | ClassNotFoundException e){

        }

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
            String SQL="INSERT INTO `proprietaire`(`id`, `nom_prenom_or_RS`, `date_nss`, `adress`, `telephone`) VALUES (?,?,?,?,?)";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);

            statement.setInt(1,propriétaires.getId());
            statement.setString(2,propriétaires.getName());
            statement.setDate(3,propriétaires.getDate_nss());
            statement.setString(4,propriétaires.getAdress());
            statement.setString(5,propriétaires.getTlf());

            statement.executeUpdate();



            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération réusie");
            alert.setContentText("Le propriétaire a été ajouté complétement .");
            alert.show();

            id.setText(propriétaires.getId()+"");
            Name.setText(null);
            date_nss.setValue(null);
            Adress.setText(null);
            Tlf.setText(null);

        } catch (ClassNotFoundException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le propriétaire n'est pas ajouté.");
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
