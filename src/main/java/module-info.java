module com.gymmanage {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    // 1. Permite que JavaFX FXML acceda por reflexión a tus controladores:
    opens com.gymManage.controller to javafx.fxml;

    // Si usas FXML directamente en la vista principal o en tus modelos/UI, abrelos o expórtalos también:
    opens com.gymManage to javafx.fxml;
    opens com.gymManage.model to com.fasterxml.jackson.databind, javafx.base;

    // 2. Exporta tus paquetes
    exports com.gymManage;
    exports com.gymManage.controller;
}