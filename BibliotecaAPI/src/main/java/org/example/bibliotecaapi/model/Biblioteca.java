package org.example.bibliotecaapi.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Biblioteca {
    private List<Libro> data;
}
