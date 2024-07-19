module com.example.studentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.example.studentmanagementsystem to javafx.fxml;
    exports com.example.studentmanagementsystem;
}