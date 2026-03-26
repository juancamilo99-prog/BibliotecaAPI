package org.example.bibliotecaapi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.bibliotecaapi.api.APIController;
import org.example.bibliotecaapi.model.Libro;
import org.example.bibliotecaapi.model.Villain;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddFavorites {
    //creamos una lista libro
    private  List<Libro> listaLibros;
    //creamos una lista observable de libro para guardar los favoritos
    private ObservableList<Libro> listaFavoritos;
    APIController controller = new APIController();

    public AddFavorites() {
        //lista libro lo igualamos para obtener toda la biblioteca que contiene los libros
        listaLibros = controller.getBiblioteca();
        //la igualamos como una lista "especial" o "detectable" para javafx
        listaFavoritos = FXCollections.observableArrayList();
    }

    //agregar libros favoritos
    public void addFavorites(long id) {
        //creamos el archivo
        File file = new File("src/main/resources/org/example/bibliotecaapi/datos/favorites.obj");
        //llamamos el objeto para buscar los libros por su id
        Libro libro = searchLibros(id);
        //convertimos un array en un solo texto, la "," es un separador.
        String notes = String.join(",", libro.getNotes());
        //sacamos una copia del array y con el map obtenemos solo el nombre de cada villano y los seperamos con una ","
        String villains = Arrays.stream(libro.getVillains()).map(Villain::getName).collect(Collectors.joining(","));
        //convertimos todos los datos del libro en una linea de texto completa
        String favoritesExport = libro.getId() + ";" + libro.getYear() + ";" + libro.getTitle() + ";" + libro.getHandle() + ";" + libro.getPublisher() + ";" + libro.getISBN()
                + ";" + libro.getPages() + ";" + notes + ";" + villains;
        //si dentro de objeto buscar libro no hay nada
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        //si la carpeta no esta creada, la crea en la ruta del file.
        if (file.getParentFile() != null) {
            file.getParentFile().mkdir();
        }

        //el file lo ponemos en modo escritura y le pasamos el file
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            //si en el archivo no hay nada creamos la cabecera
            if (file.length() == 0) {
                bw.write("ID YEAR TITULO HANDLE PUBLISHER ISBN PAGES NOTES VILLAINS");
                //damos un "salto" de linea.
                bw.newLine();
            }
            //si en la lista de favoritos no esta este libro
            if (!listaFavoritos.contains(libro)) {
                //lo agregar
                listaFavoritos.add(libro);
                //lo exporta
                bw.write(favoritesExport);
                //y crea un salto de linea
                bw.newLine();
                System.out.println("libro añadido correctamente");
            }else  {
                System.out.println("El libro ya esta añadido en la lista.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //metodo de buscar libro por su id
    public Libro searchLibros(long id) {
        System.out.println("total libros" + listaLibros.size());
        //retornamos una copia de la lista libro
        //filtramos un libro por su id y pregutamos si
        //el id que ptenemos existe dentro de la lista y nos arroje el primero
        //si no, arroja null
        return listaLibros.stream()
                .filter(libro -> libro.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //metodo para obtener la lista de favoritos
    public ObservableList<Libro> getListaFavoritos() {
        //recorremos la lista de favoritos y retornamos la lista
        for (Libro libro : listaFavoritos) {
            System.out.println(libro.getId());
            return listaFavoritos;
        }
        return listaFavoritos;
    }

    //metodo de importar libros
    public List<Libro> importarLibros() {
        //creo una lista del objeto
        List<Libro> favoritosImportados = new ArrayList<>();
        //se crea el file con la ruta
        File file = new File("src/main/resources/org/example/bibliotecaapi/datos/favorites.obj");

        //si no existe, retornamos los libros importados.
        if (!file.exists()) {
            System.out.println("No hay libros favoritos en la lista");
            return favoritosImportados;
        }
        //ponemos el file en modo lectura
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //creamos una linea
            String line;
            //lo leemos
            br.readLine();
            //mientras la linea este vacia, lo guarda.
            while ((line = br.readLine()) != null){
                System.out.println("linea: "+line);
                //dividimos el texto en partes usando ";" y con -1 admitimos hasta los vacios.
                String[] data = line.split(";", -1);
                System.out.println("partes: "+data.length);
                //llamaos los datos a guardar
                //le asignamos los datos.
                long id = Long.parseLong(data[0]);
                long year = Long.parseLong(data[1]);
                String title = data[2];
                String handle = data[3];
                String publisher = data[4];
                String isbn = data[5];
                long pages = Long.parseLong(data[6]);

                //notass
                //crea un array de notas
                //comprobamos si las notas estan vacias, si esta vacio crea un array vacio y si no esta
                //divide el texto por ","
                String[] notes = data[7].isEmpty() ? new String[0] : data[7].split(",");
                //villanos
                Villain[] villains;
                //si estan vacias crea un array vacio
                if (data[8].isEmpty()){
                    villains = new Villain[0];
                }else {
                    //si no, dividimos el texto por ","
                    String[] villainAux = data[8].split(",");
                    //usamos una copia del array y devolvemos el nombre de cada villano
                    villains = Arrays.stream(villainAux).map(nombre -> {
                        Villain v = new Villain();
                        v.setName(nombre);
                        return v;
                    })
                            .toArray(Villain[]::new);
                }

                Libro libro = new Libro();
                libro.setId(id);
                libro.setYear(year);
                libro.setTitle(title);
                libro.setHandle(handle);
                libro.setPublisher(publisher);
                libro.setISBN(isbn);
                libro.setPages(pages);
                libro.setNotes(notes);
                libro.setVillains(villains);

                favoritosImportados.add(libro);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return favoritosImportados;
    }
}
