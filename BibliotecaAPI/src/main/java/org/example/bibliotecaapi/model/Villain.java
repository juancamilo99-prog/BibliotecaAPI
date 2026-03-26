package org.example.bibliotecaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Villain {
    private String name;
    private String url;

    public void mostrarVillano(){
        System.out.println("Villain: " + name);;
        System.out.println("URL: " + url);
    }
}
