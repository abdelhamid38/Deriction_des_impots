package Bénéficiaires;

import Outils.Convert_date;
import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import System_classes.Propriétaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proprietaire.PropriétairesFXMLController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddbeneficiaireController implements Initializable {

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
    private TextField wilaya;

    @FXML
    private TextField commune;

    @FXML
    private TextField nationalite;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeStageDrageable();

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BénéficiairesController.addstage.hide();
        });
        reduire.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });

        try {
            Connection connection= (Connection) DB_Connect.getConnection();
            String SQL="SELECT id FROM `beneficiaire` ORDER BY id DESC limit 1";
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
            String SQL="INSERT INTO `beneficiaire`(`id`,`nom_prenom_or_RS`, `date_nss`,`Adresse`, `wilaya`, `commune`, `nationalite`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);

            statement.setInt(1,bénéficiaires.getId());
            statement.setString(2,bénéficiaires.getName());
            statement.setDate(3,bénéficiaires.getDate_nss());
            statement.setString(4,bénéficiaires.getAdress());
            statement.setString(5,bénéficiaires.getWilaya());
            statement.setString(6,bénéficiaires.getCommune());
            statement.setString(7,bénéficiaires.getNationalite());

            statement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération réusie");
            alert.setContentText("Le bénéficiare a été ajouté complétement .");
            alert.show();

            id.setText(bénéficiaires.getId()+"");
            Name.setText(null);
            date_nss.setValue(null);
            Adress.setText(null);
            wilaya.setText(null);
            commune.setText(null);
            nationalite.setText(null);

        } catch (ClassNotFoundException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le bénéficiare n'est pas ajouté.");
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
            BénéficiairesController.addstage.setX(event.getScreenX() - xOffset);
            BénéficiairesController.addstage.setY(event.getScreenY() - yOffset);
            BénéficiairesController.addstage.setOpacity(0.7f);
        });
        Parents.setOnDragDone((e) -> {
            BénéficiairesController.addstage.setOpacity(1.0f);
        });
        Parents.setOnMouseReleased((e) -> {
            BénéficiairesController.addstage.setOpacity(1.0f);
        });
    }
}
