package org.example.bibliotecaapi;

import javafx.application.Application;
import org.example.bibliotecaapi.api.APIController;
import org.example.bibliotecaapi.controller.AddFavorites;
import org.example.bibliotecaapi.model.Biblioteca;
import org.example.bibliotecaapi.model.Libro;

public class Launcher {
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
        //APIController controller = new APIController();
        //controller.getLibro(1);
        //AddFavorites addFavorites = new AddFavorites();
        //addFavorites.addFavorites(1);
        //addFavorites.importarLibros();
        //System.out.println("-----------------------------------");
        //addFavorites.getListaFavoritos();
    }
}
