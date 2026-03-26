package org.example.bibliotecaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private long id;
    private long Year;
    private String Title;
    private String handle;
    private String Publisher;
    private String ISBN;
    private long Pages;
    private String[] Notes;
    private Villain[] villains;

    public void mostrarDatos(){
        System.out.println("ID: " + id);
        System.out.println("Year: " + Year);
        System.out.println("Title: " + Title);
        System.out.println("Handle: " + handle);
        System.out.println("Publisher: " + Publisher);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Pages: " + Pages);
        for(int i = 0; i < villains.length; i++){
            System.out.println("Villain: " + villains[i].getName());
        }
        for(int j = 0; j < Notes.length; j++){
            System.out.println("Note: " + Notes[j]);
        }
    }

    @Override
    public String toString() {
        String showname = id+". "+Title;
        return showname;
    }
}
