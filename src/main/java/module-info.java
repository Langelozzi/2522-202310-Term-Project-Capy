module ca.bcit.comp2522.termproject.capy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens ca.bcit.comp2522.termproject.capy to javafx.fxml;
    exports ca.bcit.comp2522.termproject.capy;
}