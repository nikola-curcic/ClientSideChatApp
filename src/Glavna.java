
/**
 * Created by nikola on 9.6.17..
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

import java.io.PrintStream;
import java.net.Socket;


public class Glavna extends Application{

    private static Glavna instance;
    private Stage primaryStage;

     public static Glavna returnInstance() // returning an instance of main class by using singleton pattern
     {
         return instance;
     }

    @Override
    public void start(Stage primaryStage) throws Exception{

        instance = this;
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("PrviProzor.fxml")); // launching window on opening app
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("Stajl.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
            Sporedna.closeSock(); //proper close of socket when exiting app
        });

   }

    public static void main(String [] args) throws IOException{

        JSONObject obj = new JSONObject();

        Socket s = new Socket("127.0.0.1", 1234);
        PrintStream p = new PrintStream(s.getOutputStream());

        Sporedna.setSporedna(p, obj,s);//sending instances of JSONObject, PrintStream and socket to Sporedna class

        launch(args);

    }


    public void setIme(String ime)  //invoked by class Sporedna  for updating the name of the Client in the top of the window
   {
      primaryStage.setTitle(ime);
   }

}
