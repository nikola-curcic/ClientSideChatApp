import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


/*Controller class for the login window when opening the app*/

public class ControllerPrvi {



    @FXML
    private Button unesi;

    @FXML
    private TextField input;


    public void unesiIme() //user cannot go from opening window to main window without entering some name
    {
        if (input.getText().equals(""))
            showError();
        else {
            switchScene();
            Sporedna.logIn(input.getText());

        }
    }

    private void showError() //error that pop ups when user did not enter any name
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska");

        alert.setContentText("Molimo, unesite ime!");
        alert.showAndWait();
    }

    private void switchScene() // in case user has entered some name, app will switch scene to main chat window
    {
        Stage stage = (Stage) unesi.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ChatProzor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Stajl.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


}
