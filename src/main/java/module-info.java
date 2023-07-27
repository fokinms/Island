module com.javarush.fokin.island.islandfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.jetbrains.annotations;


    opens com.javarush.fokin.island.islandfx to javafx.fxml;
    exports com.javarush.fokin.island.islandfx;
}