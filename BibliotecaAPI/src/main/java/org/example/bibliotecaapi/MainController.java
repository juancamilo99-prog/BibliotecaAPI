package org.example.bibliotecaapi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.bibliotecaapi.api.APIController;
import org.example.bibliotecaapi.controller.AddFavorites;
import org.example.bibliotecaapi.model.Libro;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnaddfavorite;

    @FXML
    private Button btnimport;

    @FXML
    private Text txInfo;

    @FXML
    private TextField textsearch;

    @FXML
    private ListView<Libro> listaFavorito;

    @FXML
    private ListView<Libro> listaLibro;

    APIController apiController;
    AddFavorites addfavorites;
    private ObservableList<Libro> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apiController = new APIController();
        addfavorites = new AddFavorites();
        btnaddfavorite.setOnAction(event -> setBtnaddfavorite());
        showListFavoritos();
        setBtnbuscar();
        btnimport.setOnAction(event -> setBtnimport());

    }

    //agregar favoritos
    public void setBtnaddfavorite(){
        Libro libroSeleccionado = listaLibro.getSelectionModel().getSelectedItem();
        if (libroSeleccionado == null){
            System.out.println("Seleccione un libro");
            txInfo.setText("Debe seleccionar un libro");
            txInfo.setFill(Color.RED);
            return;
        }
        addfavorites.addFavorites(libroSeleccionado.getId());
        txInfo.setText("Libro agregado correctamente");
        txInfo.setFill(Color.GREEN);

    }

    //mostrar lista de favoritos
    public void showListFavoritos(){
        listaFavorito.setItems(addfavorites.getListaFavoritos());
    }

    //buscar libros
    public void setBtnbuscar(){
        List<Libro>  viewLibros = apiController.getBiblioteca();
        observableList = FXCollections.observableArrayList(viewLibros);
        FilteredList<Libro> filteredList = new FilteredList<>(observableList, libroEncontrado -> true);
        textsearch.textProperty().addListener((observableValue, libro, valor) ->{
            filteredList.setPredicate(libroEncontrado -> {
                if (valor == null || valor.isEmpty()){
                    return true;
                }
                String filtro = valor.toLowerCase().trim();

                return libroEncontrado.getTitle() != null &&
                        libroEncontrado.getTitle().toLowerCase().contains(filtro);
            });
        } );

        listaLibro.setItems(filteredList);
    }

    //importar fichero de favoritos
    public void setBtnimport(){
        List<Libro> viewImportFavorites = addfavorites.importarLibros();
        if (viewImportFavorites.isEmpty()){
            txInfo.setText("No hay libros encontrados");
            txInfo.setFill(Color.RED);
        }else {
            listaFavorito.getItems().clear();
            listaFavorito.getItems().setAll(viewImportFavorites);
            txInfo.setText("Libro importado correctamente");
            txInfo.setFill(Color.GREEN);
        }
    }
}
