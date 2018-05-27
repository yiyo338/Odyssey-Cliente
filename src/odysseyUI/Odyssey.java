package odysseyUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import odysseyLogic.Base64_;
import odysseyLogic.ConversorMP3_ByteArray;
import org.apache.commons.codec.binary.Base64;
import odysseyLogic.DocumentoXML;
import odysseyLogic.MP3;
import odysseyLogic.Node_XML;
import odysseyLogic.ReproductorMp3;
import odysseyLogic.XMLParser;
import odysseyLogic.clientetcp;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Decoder;


public class Odyssey extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        DocumentoXML n = new DocumentoXML("Comunicacion");
        n.crearHijos("Codigo", "00");
        String msj = n.ConvertirXML_String();
        
        
       clientetcp client = new clientetcp();
		java.net.Socket socket = client.crear();
                client.enviar(socket, msj);
                
		//client.recibir(socket);
                System.out.println(client.getMensajeActual());
                XMLParser NUEVO = new XMLParser();
                NUEVO.parsearString(client.getMensajeActual());
                Base64_ nu = new Base64_();
                //String p = NUEVO.DepurarXML(client.getMensajeActual());
                
                
                Node_XML doc = new Node_XML(NUEVO.parsearString(client.getMensajeActual()));  
                NodeList lista = doc.by_tagName("mBytes");
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] decodedBytes = decoder.decodeBuffer(lista.item(0).getTextContent());
                
                //String dec = nu.descifrarBase64(lista.item(0).getTextContent());
                //byte[] v = dec.getBytes();
                //System.out.print("Soy este we: " + dec);
                
                /*byte[] decoded = Base64.decodeBase64(lista.item(0).getTextContent());
                //String ruta = "mp3";*/
               // System.out.println(decodedBytes);
                FileUtils.writeByteArrayToFile(new File("puta vida.mp3"), decodedBytes);
                     
                //MP3 repro = new MP3("/home/cris/NetBeansProjects/Odyssey/puta vida.mp3");
                //repro.play();
                
        
        
        Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
 
