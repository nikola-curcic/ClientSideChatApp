import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by nikola on 10.6.17..
 */
public class Sporedna implements Runnable{

    private static JSONObject obj, receiveObj;
    private static PrintStream p;
    private static Socket s;
    private static BufferedReader is;
    private static JSONParser parser = new JSONParser();
    private static boolean prvaPrimljena = false;


    @FXML
    private static TextArea glavniProzor, trenutnoOnline;


    public static void setSporedna (PrintStream pr, JSONObject objc, Socket sock)// accepts necessary instances from main class
    {
      p = pr;
      obj = objc;
      s = sock;
    }

    public static void closeSock()  {//closes sock when requested by main class

        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logIn(String ime) //method to send to the server info that new user has been logged in
    {
        obj.put("obicna_poruka",false);
        obj.put("poruka", ime);

        p.println(obj);
        Glavna.returnInstance().setIme(ime);
    }

    public static void proslediPoruku(String poruka) // method to send regular messages to the server
    {
        obj.put("obicna_poruka",true);
        obj.put("poruka", poruka);
        p.println(obj);
    }

    /*separate thread for receiving messages from the server */

    @Override
    public void run() {

        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

         String line;

            try {
                while ((line = is.readLine()) != null) {

                    try {
                        receiveObj = (JSONObject) parser.parse(line);

                        /*by reading characteristics of received JSON message, this if clause will decide whether
                         * it should forward the message to the main chat area or update list of on line users
                         * or do nothing (there are specific messages that are pulled from database i the moment a
                         * new user logs in)*/

                        if((Boolean)receiveObj.get("prva") && prvaPrimljena==false)
                        {
                            prvaPrimljena = true;
                            if(!receiveObj.get("poruka").equals(""))
                            setTextGlavni((String) receiveObj.get("poruka"));
                        }
                        else if((Boolean) receiveObj.get("obicna") && !(Boolean)receiveObj.get("prva"))
                        {
                            setTextGlavni((String) receiveObj.get("poruka"));
                        }
                        else if(!(Boolean)receiveObj.get("prva"))
                            {
                            setTextOnline((String) receiveObj.get("poruka"));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static void setProzori(TextArea glavni, TextArea trenutno) // receives instances of two text areas from ControllerChat class
    {
        glavniProzor = glavni;
        trenutnoOnline = trenutno;
    }

    private void setTextGlavni(String poruka) // sets text to main chat area

    {
      glavniProzor.appendText(poruka+"\n");
    }

    private void setTextOnline(String poruka) // updates the list of online users
    {
        trenutnoOnline.clear();
        trenutnoOnline.setText(poruka);
    }

}
