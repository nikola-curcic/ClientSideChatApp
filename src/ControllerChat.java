import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nikola on 26.5.17..
 */
public class ControllerChat implements Initializable {

     @FXML
     private Button submit;

     @FXML
     private TextArea enterText;

     @FXML
     private Label lejbl;

     @FXML
     private  TextArea glavniProzor, trenutnoOnline;


    @Override
    public void initialize(URL location, ResourceBundle resources)// some parameters which are set in the moment main chat window is loaded
     {

        lejbl.setVisible(false);
        lejbl.setText("Tekst mozete uneti i \npritiskom na dugme ENTER.");
        submit.setOnMouseEntered(e-> info());
         /* only once, app will pop up a message that entering data is possible both by clicking submit button but
            also by pressing ENTER on the keyboard
              */

        submit.setOnMouseExited(e -> lejbl.setVisible(false));

        enterText.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.ENTER)
                enter();});


        enterText.setWrapText(true);
        glavniProzor.setWrapText(true);
        trenutnoOnline.setWrapText(true);

        Sporedna sp = new Sporedna();
        sp.setProzori(glavniProzor,trenutnoOnline);// sends instances of two text areas to class sporedna
        Thread t1 = new Thread(sp);

        t1.start();
    }


    private void info() // method to stop the mentioned message to pop up more than once
    {
        lejbl.setVisible(true);
        submit.setOnMouseEntered(null);
    }

    public void enter() //forwards the message from enterText area to the class Sporedna
    {
        Sporedna.proslediPoruku(enterText.getText());
        enterText.clear();
    }


}
