package org.example.bibliotecaapi.api;

import com.google.gson.Gson;
import org.example.bibliotecaapi.model.Biblioteca;
import org.example.bibliotecaapi.model.Libro;
import org.example.bibliotecaapi.model.LibroBiblioteca;

import java.util.List;

import static org.example.bibliotecaapi.conection.GetJson.getJson;

public class APIController {
    String urlBase = "https://stephen-king-api.onrender.com/api/";
    public List<Libro> getBiblioteca() {
        String urlDatos = urlBase + "books/";
        try{
            Gson gson = new Gson();
            String jsonPost = getJson(urlDatos);
            Biblioteca respuesta = gson.fromJson(jsonPost, Biblioteca.class);
            for (Libro libro : respuesta.getData()){
                libro.mostrarDatos();
            }
            return respuesta.getData();
        }catch(Exception e){
            System.out.println("Error al recuperar los bibliotecas" + e.getMessage());
        }
        return null;
    }

    public Biblioteca getLibro(long id){
        String urlDatos = urlBase + "book/"+id;
        try{
            Gson gson = new Gson();
            String jsonPost = getJson(urlDatos);
            System.out.println("JSON recibido");
            LibroBiblioteca respuesta = gson.fromJson(jsonPost, LibroBiblioteca.class);
            respuesta.getData().mostrarDatos();
        }catch (Exception e){
            System.out.println("Error al recuperar los bibliotecas" + e.getMessage());
        }
        return null;
    }
}
