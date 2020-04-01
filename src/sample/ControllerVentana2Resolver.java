package sample;

import bsh.Interpreter;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.bson.Document;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerVentana2Resolver implements Initializable
{
    String[] stringProblema;
    int[] intProblema;

    int cantidadInt = 0;
    int cantidadString = 0;
    int cantidadIntArray = 0;
    int cantidadStringArray = 0;

    String codigo;
    String problema;

    @FXML
    Label descripcionproblema;
    @FXML
    Label tituloproblema;
    @FXML
    TextField txtingresarparametros;
    @FXML
    Button btnComprobar;
    @FXML
    Button btnSalir;
    @FXML
    Label labelresultado;
    @FXML
    Button buttonaber;

    int numProblemas = 0, numeroProblema;
    Problema[] problemas;

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase mongoDatabase = mongoClient.getDatabase("MiCondingBat");

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initComponents();

        btnComprobar.setOnAction(handlerComprobar);
        btnSalir.setOnAction(handlerSalir);
        buttonaber.setOnAction(handlerAber);

        MongoCollection<Document> collectionProblemas = mongoDatabase.getCollection("Problemas");

        try (MongoCursor<Document> mongoCursor = collectionProblemas.find().iterator())
        {
            while (mongoCursor.hasNext())
            {
                var doc = mongoCursor.next();
                numProblemas++;
            }
        }

        problemas = new Problema[numProblemas];
        numProblemas = 0;

        try (MongoCursor<Document> mongoCursor = collectionProblemas.find().iterator())
        {
            while (mongoCursor.hasNext())
            {
                var doc = mongoCursor.next();
                var problema = new ArrayList<>(doc.values());

                String uno = String.valueOf(String.valueOf(problema.get(1)).charAt(0));
                String dos = (String) problema.get(2);
                String tres = (String) problema.get(3);
                String cuatro = (String) problema.get(4);
                String cinco = (String) problema.get(5);
                String seis = String.valueOf(String.valueOf(problema.get(6)).charAt(0));

                problemas[numProblemas] = new Problema(Integer.parseInt(uno), Integer.parseInt(seis), dos, tres, cuatro, cinco);
                numProblemas++;
            }
        }

        try
        {
            FileReader fileReader = new FileReader("C:/Users/STLuc/Desktop/DATO.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea = bufferedReader.readLine();

            while (linea != null)
            {
                if(!linea.isEmpty())
                {
                    problema = linea;
                }

                linea = bufferedReader.readLine();
            }

            fileReader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        tituloproblema.setText(problema);

        for (int i = 0; i < problemas.length; i++)
        {
            if(problemas[i].getNombre().equals(problema))
            {
                descripcionproblema.setText(problemas[i].getDescripcion());
                codigo = problemas[i].getPrograma();

                numeroProblema = i;

                String[] parametros = problemas[i].getParametros().split(",");

                for (int j = 0; j < parametros.length; j++)
                {
                    parametros[j] = parametros[j].trim();

                    String[] tipos = parametros[j].split(" ");

                    if(tipos[0].equals("int"))
                    {
                        cantidadInt++;
                    }
                    else if(tipos[0].equals("String"))
                    {
                        cantidadString++;
                    }
                    else if(tipos[0].equals("int[]"))
                    {
                        cantidadIntArray++;
                    }
                    else if(tipos[0].equals("String[]"))
                    {
                        cantidadStringArray++;
                    }
                }

                if(cantidadIntArray == 0 && cantidadString == 0 && cantidadStringArray == 0)
                {
                    intProblema = new int[cantidadInt];
                }
                else if(cantidadString == 0 && cantidadStringArray == 0 && cantidadInt == 0)
                {
                    intProblema = new int[99];
                }

                if(cantidadStringArray == 0 && cantidadIntArray == 0 && cantidadInt == 0)
                {
                    stringProblema = new String[cantidadString];
                }
                else if(cantidadIntArray == 0 && cantidadInt == 0 && cantidadString == 0)
                {
                    stringProblema = new String[99];
                }

                System.out.println("tenemos " + cantidadInt + " ints y " + cantidadString + " strings y " + cantidadIntArray + " int[] y " + cantidadStringArray + " strings[]");
            }
        }
    }

    private void initComponents()
    {

    }

    EventHandler<ActionEvent> handlerComprobar=new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event)
        {
            String[] textoParametros = txtingresarparametros.getText().split(" ");
            String parametros = "";

            for (int i = 0; i < textoParametros.length; i++)
            {
                if(cantidadInt > 0)
                {
                    parametros = parametros + "int a" + (i+1) + " = " + textoParametros[i] + ";";
                }

                if(cantidadIntArray > 0)
                {
                    parametros = parametros + textoParametros[i];

                    if(i < textoParametros.length - 1)
                    {
                        parametros = parametros + ",";
                    }
                }

                if(cantidadString > 0)
                {
                    parametros = parametros + "String string = " + "\"" + textoParametros[i] + "\";";
                }

                if(cantidadStringArray > 0)
                {
                    parametros = parametros + "\"" + textoParametros[i] + "\"";

                    if(i < textoParametros.length - 1)
                    {
                        parametros = parametros + ",";
                    }
                }
            }

            if(cantidadStringArray > 0)
            {
                parametros = "String[] strings = {" + parametros + "};";
                labelresultado.setFont(new Font("Arial", 16));
            }
            else
            {
                labelresultado.setFont(new Font("Arial", 31));
            }

            if(cantidadIntArray > 0)
            {
                parametros = "int[] nums = {" + parametros + "};";
            }

            System.out.println(parametros);

            String code = parametros + codigo;

            Interpreter interpreter = new Interpreter();

            try
            {
                interpreter.set("context", this);
//                System.out.println(interpreter.eval(code));
                labelresultado.setText("Resultado: " + String.valueOf(interpreter.eval(code)));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                labelresultado.setText("Resultado: ERROR");
            }

        }
    };

    EventHandler<ActionEvent> handlerSalir=new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event)
        {
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    };

    EventHandler<ActionEvent> handlerAber=new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event)
        {
            MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
            DB db = mongoClient.getDB("MiCondingBat");
            DBCollection collection = db.getCollection("Problemas");

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("nombre", tituloproblema.getText());
            DBCursor cursor = collection.find(whereQuery);
            while(cursor.hasNext())
            {
                System.out.println(cursor.next());

                try
                {
                    FileWriter fileWriter = new FileWriter("C:/Users/STLuc/Desktop/JSON.txt", false);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    bufferedWriter.write("id_problema: " + problemas[numeroProblema].getNum());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("nombre: " + problemas[numeroProblema].getNombre());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("descripcion: " + problemas[numeroProblema].getDescripcion());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("parametros: " + problemas[numeroProblema].getParametros());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("programa: " + problemas[numeroProblema].getPrograma());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("id_categoria: " + problemas[numeroProblema].getNumCategoria());
                    bufferedWriter.newLine();

                    bufferedWriter.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

                File file = new File("C:/Users/STLuc/Desktop/JSON.txt");

                if (Desktop.isDesktopSupported())
                {
                    try
                    {
                        Desktop.getDesktop().edit(file);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    // dunno, up to you to handle this
                }
            }
        }
    };
}

//
//
////            String code = "System.out.println(\"XD\");";
//
////            String code = "int a = 1; \n System.out.println(\"ABER \" + a);";
//
//    String code = "int a = 1;\n" +
//            "int b = 2;\n" +
//            "int c = 3;\n" +
//            "\n" +
//            "  if(a == 2 && a == b && a == c)\n" +
//            "  {\n" +
//            "    return 10;\n" +
//            "  }\n" +
//            "  else if(a == b && a == c && b == c)\n" +
//            "  {\n" +
//            "    return 5;\n" +
//            "  }\n" +
//            "  else if(a != b && a != c)\n" +
//            "  {\n" +
//            "    return 1;\n" +
//            "  }\n" +
//            "  else\n" +
//            "  {\n" +
//            "    return 0;\n" +
//            "  }";
//
//    Interpreter interpreter = new Interpreter();
//            try
//                    {
//                    interpreter.set("context", this);//set any variable, you can refer to it directly from string
//                    System.out.println(interpreter.eval(code));//execute code
//                    }
//                    catch (Exception e){//handle exception
//                    e.printStackTrace();
//                    }
