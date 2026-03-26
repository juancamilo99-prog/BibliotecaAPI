module org.example.bibliotecaapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.net.http;
    requires com.google.gson;


    opens org.example.bibliotecaapi to javafx.fxml;
    opens org.example.bibliotecaapi.model to com.google.gson;
    exports org.example.bibliotecaapi;
    exports org.example.bibliotecaapi.model;
}