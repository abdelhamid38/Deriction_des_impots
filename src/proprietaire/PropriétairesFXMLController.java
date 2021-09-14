/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proprietaire;

import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import System_classes.Propriétaires;
import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Abdelhamid
 */
public class PropriétairesFXMLController implements Initializable {

       @FXML
    private TableView<Propriétaires> Table;

    @FXML
    private TableColumn<?,?> id;

    @FXML
    private TableColumn<?, ?> Name;

    @FXML
    private TableColumn<?, ?> Date_nss;

    @FXML
    private TableColumn<?, ?> Adress;

    @FXML
    private TableColumn<?, ?> Tlf;

    @FXML
    private TableColumn<Propriétaires, String> Action;

    @FXML
    private TextField search;

    @FXML
    private JFXButton Add;

    public static Stage stage=null;

    Connection connection;
    ObservableList<Propriétaires> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Loadtable();
        filtersearch();
    }

    @FXML
    void Add(ActionEvent event) {

        try {
            AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/proprietaire/AddPropriétaire.fxml"));
            Scene scene=new Scene(anchorPane);
            Stage s=new Stage();
            s.setScene(scene);
            s.initStyle(StageStyle.TRANSPARENT);
            s.show();
            stage=s;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Loadtable(){

        list.clear();
        Table.refresh();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Date_nss.setCellValueFactory(new PropertyValueFactory<>("date_nss"));
        Adress.setCellValueFactory(new PropertyValueFactory<>("Adress"));
        Tlf.setCellValueFactory(new PropertyValueFactory<>("Tlf"));

        Callback<TableColumn<Propriétaires, String>, TableCell<Propriétaires, String>> cellFoctory = (TableColumn<Propriétaires, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<Propriétaires, String> cell = new TableCell<Propriétaires, String>() {

                // Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {

                    super.updateItem(item, empty);
                    // ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            int i=Table.getSelectionModel().getSelectedItem().getId();
                            try {
                                Connection connection= (Connection) DB_Connect.getConnection();
                                String SQL="DELETE FROM `proprietaire` WHERE id="+i;
                                PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
                                statement.execute();

                                list.remove(Table.getSelectionModel().getSelectedItem());
                                Table.refresh();

                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }

                        });

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            try {
                                AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/proprietaire/Update.fxml"));
                                Scene scene=new Scene(anchorPane);
                                Stage s=new Stage();
                                s.setScene(scene);
                                s.setUserData(Table.getSelectionModel().getSelectedItem());
                                s.initStyle(StageStyle.TRANSPARENT);
                                s.show();
                                stage=s;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        managebtn.setSpacing(5);

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };

            return cell;

        };

        Action.setCellFactory(cellFoctory);

        try {
            connection=(Connection) DB_Connect.getConnection();

            String SQL="Select * from proprietaire";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
            ResultSetImpl resultSet = (ResultSetImpl) statement.executeQuery();


            while (resultSet.next()){

                list.add(new Propriétaires(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            Table.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {

        }
    }

    @FXML
    void Reload(MouseEvent event) {


        Loadtable();

    }


    public void filtersearch(){
        FilteredList<Propriétaires> Filteredlist=new FilteredList<>(this.list,b -> true);
        search.textProperty().addListener((observable,oldvalue,newvalue)->{
            Filteredlist.setPredicate(Propriétaires -> {
                if(newvalue==null || newvalue.isEmpty()){
                    return true;
                }
                String lowercase=newvalue.toLowerCase();
                if (Propriétaires.getName().toLowerCase().indexOf(lowercase)!= -1){
                    return true;
                }else if (Propriétaires.getAdress().toLowerCase().indexOf(lowercase)!=-1){
                    return true;
                }else if (Propriétaires.getTlf().toLowerCase().indexOf(lowercase)!= -1){
                    return true;
                }else return false;
            });
        });
        SortedList<Propriétaires> sortedList=new SortedList<>(Filteredlist);
        sortedList.comparatorProperty().bind(Table.comparatorProperty());
        Table.setItems(sortedList);

    }
   
    
}