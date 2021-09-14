package Paiements;

import Outils.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class paiementController implements Initializable {

    @FXML
    private TableView<?> Table;

    @FXML
    private JFXButton Parcourir;

    @FXML
    private TextField search;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXTextField width_field;

    @FXML
    private JFXTextField heith_field;

    @FXML
    private JFXTextField x;

    @FXML
    private JFXTextField y;

    @FXML
    private JFXTextArea Ocr_TextArea;

    @FXML
    private ScrollPane scrollp1;

    @FXML
    private JFXTextField imgh,imgw;

    @FXML
    private AnchorPane anchore;


    private Group scrollContent;
    public static String path;
    private final Rectangle zone = new Rectangle();
    @FXML
    private final Light.Point anchor = new Light.Point();
    static int getx, gety, getwidth, getheight;
    private Group group=new Group();
    FileChooser Brows=new FileChooser();
    File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        group = new Group(imageView);
        scrollContent = new Group(group);
        scrollp1.setContent(scrollContent);

        Parcourir.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{

            for (Node node:scrollContent.getChildren()) {

                if (node.getClass().getName().equals("Rectangle"))

                    anchore.getChildren().remove(node);


            }

            file = Brows.showOpenDialog(null);
            try {
                FileChooser.ExtensionFilter images=new FileChooser.ExtensionFilter("image","*PNG","*JPG","*GIF");
                Brows.setInitialDirectory(file.getParentFile());
                FileInputStream stream=new FileInputStream(file.getAbsolutePath());
                path=file.getAbsolutePath();
                Brows.getExtensionFilters().add(images);
                Image image=new Image(stream);
                imageView.setFitWidth(image.getWidth());
                imageView.setFitHeight(image.getHeight());
                imageView.setImage(image);

                imgh.setText("Height :"+image.getHeight());
                imgw.setText("Width :"+image.getWidth());

                for (String s: foldersnameList.list) {

                    Rectangle rectangle=LinearRegression.posinnement(s,imageView);

                    rectangle.setFill(Color.rgb(0,0,0,0));
                    rectangle.setStroke(Color.valueOf("#0746A6"));

                    scrollContent.getChildren().add(rectangle);
                    scrollp1.setContent(scrollContent);
                    //anchore.getChildren().add(rectangle);

                }

                /*for (String s:list) {
                    File theDir = new File("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\"+s);
                    if (!theDir.exists()){
                        theDir.mkdirs();
                    }
                }*/

            } catch (FileNotFoundException fileNotFoundException) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("fileNotFoundException");
                alert.setContentText(fileNotFoundException.getLocalizedMessage());
                alert.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
        /*imageView.setOnMouseMoved((e) -> {
            anchor.setX(e.getX());
            anchor.setY(e.getY());
            int agetx = (int) Math.round(anchor.getX());
            int agety = (int) Math.round(anchor.getY());

        });
        imageView.setOnMousePressed(event -> {
            //whene you clicked at imageview delete the rectongle zone
            group.getChildren().remove(zone);
            //initialiser les X,Y,Height,Width de
            zone.setWidth(0);
            zone.setHeight(0);
            anchor.setX(event.getX());
            anchor.setY(event.getY());
            zone.setX(event.getX());
            zone.setY(event.getY());
            zone.setFill(Color.rgb(255, 0, 0, 0)); // transparent color
            zone.setStroke(Color.valueOf("#0746A6")); // border
            zone.getStrokeDashArray();
            group.getChildren().add(zone);
        });
        imageView.setOnMouseDragged(event -> {
            zone.setWidth(Math.abs(event.getX() - anchor.getX()));
            zone.setHeight(Math.abs(event.getY() - anchor.getY()));
            zone.setX(Math.min(anchor.getX(), event.getX()));
            zone.setY(Math.min(anchor.getY(), event.getY()));
        });
        imageView.setOnMouseReleased(event -> {
            // Do what you want with selection's properties here
            System.out.printf("X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f%n",zone.getX(), zone.getY(), zone.getWidth(), zone.getHeight());
            getx = (int) Math.round(zone.getX());
            gety = (int) Math.round(zone.getY());
            getwidth = (int) Math.round(zone.getWidth());
            getheight = (int) Math.round(zone.getHeight());
            //values from double to int
            width_field.setText(String.valueOf(getwidth));
            heith_field.setText(String.valueOf(getheight));
            x.setText(String.valueOf(getx));
            y.setText(String.valueOf(gety));




            //Do ocr to the rectangle zone
            if (getwidth != 0 && getheight != 0) {
                String Result_zone_ocr = null;
                try {
                    System.out.println("width :"+getwidth+"height :"+getheight);
                    Result_zone_ocr = Recognition.recognition(getx, gety, getwidth, getheight, path);
                    System.out.println(Result_zone_ocr);
                    Ocr_TextArea.setStyle("-fx-text-fill: darkblue ;" + "-fx-font-weight: 700;");
                    Ocr_TextArea.setText(Result_zone_ocr);
                } catch (TesseractException e) { e.printStackTrace();}
            } else {
                    Ocr_TextArea.setStyle("-fx-text-fill: red ;" + "-fx-font-weight: 700;");
                    Ocr_TextArea.setText(" Null !! ");
                    System.out.println(" null !!! ");
            }
        });*/
    }
}