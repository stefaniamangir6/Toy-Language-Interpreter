module com.example.exam_cyclic_barrier {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.exam_cyclic_barrier to javafx.fxml;
    exports com.example.exam_cyclic_barrier;
}