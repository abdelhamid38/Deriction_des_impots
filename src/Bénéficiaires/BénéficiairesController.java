/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bénéficiaires;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Outils.DB_Connect;
import System_classes.Bénéficiaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author Abdelhamid
 */
public class BénéficiairesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Bénéficiaires> Table;

    @FXML
    private TableColumn<?,?> id;

    @FXML
    private TableColumn<?, ?> Name;

    @FXML
    private TableColumn<?, ?> Date_nss;

    @FXML
    private TableColumn<?, ?> Adress;

    @FXML
    private TableColumn<?, ?> Wilaya;

    @FXML
    private TableColumn<?, ?> Commune;

    @FXML
    private TableColumn<?, ?> Nationalite;

    @FXML
    private TableColumn<Bénéficiaires, String> Action;

    @FXML
    private TextField search;

    @FXML
    private JFXButton Add;


    @FXML
    private JFXPopup popup;

    public static Stage editstage=null;
    public static Stage addstage=null;
    public static Stage displaystage=null;

    Connection connection;
    ObservableList<Bénéficiaires> list = FXCollections.observableArrayList();
    ListView <VBox> view=new ListView<>();
    VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Loadtable();
        filtersearch();
        //initPopup();
    }
    JFXButton add,remove;
    private void initPopup() {

        add=new JFXButton("Add");
        remove=new JFXButton("remove");

        add.setPrefSize(200,50);
        remove.setPrefSize(200,50);

        add.setStyle("-fx-background-color:#0746A6;" +
                     "-fx-text-fill:white;" +
                     "-fx-font-size:18px;");

        remove.setStyle("-fx-background-color:#0746A6;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:18px;");

        vBox=new VBox(add,remove);
        popup.setContent(vBox);
        popup.setSource(Table);
        popup.setPrefWidth(200);
    }

      @FXML
    void showPopup(MouseEvent event){

     /* if(event.getButton()==MouseButton.PRIMARY)
        popup.show(JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT,event.getX(),event.getY());

        try {
            Thread.sleep(3000,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        popup.close();*/

    }

    @FXML
    void Add(ActionEvent event) {

        try {
            AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/Bénéficiaires/Addbénéficiaire.fxml"));
            Scene scene=new Scene(anchorPane);
            Stage s1=new Stage();
            s1.setScene(scene);
            s1.initStyle(StageStyle.TRANSPARENT);
            s1.show();
            addstage=s1;
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
        Wilaya.setCellValueFactory(new PropertyValueFactory<>("Wilaya"));
        Commune.setCellValueFactory(new PropertyValueFactory<>("Commune"));
        Nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));

        Callback<TableColumn<Bénéficiaires, String>, TableCell<Bénéficiaires, String>> cellFoctory = (TableColumn<Bénéficiaires, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<Bénéficiaires, String> cell = new TableCell<Bénéficiaires, String>() {

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
                        FontAwesomeIconView DisplayIcon = new FontAwesomeIconView(FontAwesomeIcon.MAP);

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
                        DisplayIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#0746A6;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            int i=Table.getSelectionModel().getSelectedItem().getId();
                            try {
                                Connection connection= (Connection) DB_Connect.getConnection();
                                String SQL="DELETE FROM `beneficiaire` WHERE id="+i;
                                PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
                                statement.execute();

                                list.remove(Table.getSelectionModel().getSelectedItem());
                                Table.refresh();

                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }

                        });
                        Tooltip tooltip1=new Tooltip("Remove This one");
                        FontAwesomeIconView iconView1=new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        iconView1.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        tooltip1.setStyle(
                                "-fx-font-size:18px;" +
                                        "-fx-font-family:Tahoma;");
                        tooltip1.setGraphic(iconView1);
                        Tooltip.install(deleteIcon,tooltip1);

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            try {
                                AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/Bénéficiaires/Update.fxml"));
                                Scene scene=new Scene(anchorPane);
                                Stage s=new Stage();
                                s.setScene(scene);
                                s.setUserData(Table.getSelectionModel().getSelectedItem());
                                s.initStyle(StageStyle.TRANSPARENT);
                                s.show();
                                editstage=s;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });

                        Tooltip tooltip2=new Tooltip("Edit info");
                        FontAwesomeIconView iconView2=new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        iconView2.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        tooltip2.setStyle(
                                "-fx-font-size:18px;" +
                                        "-fx-font-family:Tahoma;");
                        tooltip2.setGraphic(iconView2);
                        Tooltip.install(editIcon,tooltip2);

                        DisplayIcon.setOnMouseClicked((MouseEvent e)->{

                            try {
                                AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/Bénéficiaires/Displayinfo.fxml"));
                                Scene scene=new Scene(anchorPane);
                                Stage s2=new Stage();
                                s2.setScene(scene);
                                s2.setUserData(Table.getSelectionModel().getSelectedItem());
                                s2.initStyle(StageStyle.TRANSPARENT);
                                s2.show();
                                displaystage=s2;
                            }catch (Exception exception){

                            }

                        });
                        Tooltip tooltip=new Tooltip("Display info");
                        FontAwesomeIconView iconView=new FontAwesomeIconView(FontAwesomeIcon.MAP);
                        iconView.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#0746A6;"
                        );
                        tooltip.setStyle(
                                "-fx-font-size:18px;" +
                                        "-fx-font-family:Tahoma;");
                        tooltip.setGraphic(iconView);
                        Tooltip.install(DisplayIcon,tooltip);

                        HBox managebtn = new HBox(editIcon, deleteIcon,DisplayIcon);
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

            String SQL="Select * from beneficiaire";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
            ResultSetImpl resultSet = (ResultSetImpl) statement.executeQuery();


            while (resultSet.next()){

                list.add(new Bénéficiaires(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
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
        FilteredList<Bénéficiaires> Filteredlist=new FilteredList<>(this.list,b -> true);
        search.textProperty().addListener((observable,oldvalue,newvalue)->{
            Filteredlist.setPredicate(bénéficiaires -> {
                if(newvalue==null || newvalue.isEmpty()){
                    return true;
                }
                String lowercase=newvalue.toLowerCase();
                if (bénéficiaires.getName().toLowerCase().indexOf(lowercase)!= -1){
                    return true;
                }else if (bénéficiaires.getAdress().toLowerCase().indexOf(lowercase)!=-1){
                    return true;
                }else if (bénéficiaires.getCommune().toLowerCase().indexOf(lowercase)!= -1){
                    return true;
                }else return false;
            });
        });
        SortedList<Bénéficiaires> sortedList=new SortedList<>(Filteredlist);
        sortedList.comparatorProperty().bind(Table.comparatorProperty());
        Table.setItems(sortedList);

    }
}