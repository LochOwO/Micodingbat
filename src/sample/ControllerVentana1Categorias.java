package sample;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerVentana1Categorias implements Initializable
{
    String problema, problemaDos;

    @FXML
    ComboBox<String> comboCategorias;
    @FXML
    Label labeltituloapp;
    @FXML
    Label labelcategoriaselect;
    @FXML
    Label texttitulo;
    @FXML
    Label textdescripcion;
    @FXML
    Label labelcategoria;
    @FXML
    Button buttonaceptar;
    @FXML
    Label texttitulodos;
    @FXML
    Label textdescripciondos;
    @FXML
    Button buttonaceptardos;

    Categoria[] categorias;
    Problema[] problemas;
    int numCategoria = 0;

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase mongoDatabase = mongoClient.getDatabase("MiCondingBat");

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        MongoCollection<Document> collectionCategorias = mongoDatabase.getCollection("Categorias");

        try (MongoCursor<Document> mongoCursor = collectionCategorias.find().iterator())
        {
            while (mongoCursor.hasNext())
            {
                var doc = mongoCursor.next();
                numCategoria++;
            }
        }

        categorias = new Categoria[numCategoria];
        numCategoria = 0;

        try (MongoCursor<Document> mongoCursor = collectionCategorias.find().iterator())
        {
            while (mongoCursor.hasNext())
            {
                var doc = mongoCursor.next();
                var categoria = new ArrayList<>(doc.values());

                String uno = String.valueOf(String.valueOf(categoria.get(1)).charAt(0));
                String dos = (String) categoria.get(2);
                String tres = (String) categoria.get(3);

                categorias[numCategoria] = new Categoria(Integer.parseInt(uno), dos, tres);
                numCategoria++;
            }
        }

        ObservableList<String> lista = FXCollections.observableArrayList();

        for (int i = 0; i < categorias.length; i++)
        {
            lista.add(categorias[i].getNum() + ":   " + categorias[i].getCategoria());
        }

        comboCategorias.setItems(lista);

        int numProblemas = 0;

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
                System.out.println(problemas[numProblemas].getNombre() + " " +problemas[numProblemas].getParametros() + " " +problemas[numProblemas].getDescripcion() + " ");
                numProblemas++;
            }
        }

        comboCategorias.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                int cantidad = 0;

                for (int i = 0; i < problemas.length; i++)
                {
                    int num = Integer.parseInt(String.valueOf(comboCategorias.getSelectionModel().getSelectedItem().charAt(0)));
                    System.out.println(num + " " + problemas[i].getNumCategoria());
                    if(num == problemas[i].getNumCategoria())
                    {
                        if(cantidad > 0)
                        {
                            texttitulodos.setText(problemas[i].getNombre());
                            textdescripciondos.setText(categorias[problemas[i].getNumCategoria()-1].getDescripcion());
                            texttitulodos.setVisible(true);
                            textdescripciondos.setVisible(true);
                            buttonaceptardos.setVisible(true);

                            problemaDos = problemas[i].getNombre();
                        }
                        else
                        {
                            texttitulo.setText(problemas[i].getNombre());
                            textdescripcion.setText(categorias[problemas[i].getNumCategoria()-1].getDescripcion());
                            texttitulo.setVisible(true);
                            textdescripcion.setVisible(true);
                            buttonaceptar.setVisible(true);

                            problema = problemas[i].getNombre();
                        }

                        cantidad++;
                    }
                }

                if(cantidad <= 1)
                {
                    texttitulodos.setVisible(false);
                    textdescripciondos.setVisible(false);
                    buttonaceptardos.setVisible(false);
                }
            }
        });

        buttonaceptar.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    try
                    {
                        FileWriter fileWriter = new FileWriter("C:/Users/STLuc/Desktop/DATO.txt", false);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        bufferedWriter.write(problema);
                        bufferedWriter.newLine();

                        bufferedWriter.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                    showStageVentana2();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        buttonaceptardos.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    try
                    {
                        FileWriter fileWriter = new FileWriter("C:/Users/STLuc/Desktop/DATO.txt", false);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        bufferedWriter.write(problemaDos);
                        bufferedWriter.newLine();

                        bufferedWriter.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                    showStageVentana2();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showStageVentana2() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Ventana2Resolver.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
    }
}