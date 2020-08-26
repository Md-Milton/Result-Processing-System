/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alertmessage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author milton
 */
public class AlertMaker {
    
    public static void showConfirmation(String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("header");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
     public static void showInformation(String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("header");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public static void showError(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("header");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public static void showAlert(StackPane stackPane,String header,String body){
         JFXDialogLayout dialogLayout = new JFXDialogLayout();
             JFXDialog dialog = new JFXDialog(stackPane,dialogLayout,JFXDialog.DialogTransition.TOP);
             
             Button controlButton = new Button("OK");
             controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
                    dialog.close();
                });
             Label head = new Label(header);
             head.setStyle("-fx-text-fill:red");
             Label lblBody = new Label(body);
             lblBody.setStyle("-fx-text-fill:black");
             controlButton.setMinWidth(100);
             controlButton.setStyle("-fx-background-color:#345678;-fx-text-fill:white;");
             dialogLayout.setActions(controlButton);
             dialogLayout.setHeading(head);
             dialogLayout.setBody(lblBody);
             dialog.show();
    }

public static void showSuccessAlert(StackPane stackPane,String header,String body){
         JFXDialogLayout dialogLayout = new JFXDialogLayout();
             JFXDialog dialog = new JFXDialog(stackPane,dialogLayout,JFXDialog.DialogTransition.TOP);
             
             Button controlButton = new Button("OK");
             controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
                    dialog.close();
                });
             Label head = new Label(header);
             head.setStyle("-fx-text-fill:green");
             Label lblBody = new Label(body);
             lblBody.setStyle("-fx-text-fill:black");
             controlButton.setMinWidth(100);
             controlButton.setStyle("-fx-background-color:#345678;-fx-text-fill:white;");
             dialogLayout.setActions(controlButton);
             dialogLayout.setHeading(head);
             dialogLayout.setBody(lblBody);
             dialog.show();
    }


 public static void showMaterial(StackPane stackPane,List<JFXButton>controlsButton,String header,String body){
           BoxBlur blur = new BoxBlur(3,3,3);
           
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane,dialogLayout,JFXDialog.DialogTransition.TOP);
           
            
            controlsButton.forEach(controls->{
                
                controls.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
                dialog.close();
                });
            
            });
            
            dialogLayout.setActions(controlsButton);
            dialogLayout.setHeading(new Label(header));
            dialogLayout.setBody(new Label(body));
            dialog.show();
            
            dialog.setOnDialogClosed((dialogevent) -> {
               // stackPane.setEffect(null);
            });
            
            //stackPane.setEffect(blur);
    }


public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(blur);
    }

}

