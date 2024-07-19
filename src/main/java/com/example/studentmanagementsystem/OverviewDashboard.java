package com.example.studentmanagementsystem;

import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OverviewDashboard {
    @FXML
    private BorderPane contentPane;

    @FXML
    private TextField totalBooks, totalBorrowed, toBeReturned, totalReturned;

    private final String pathToBorrowCSV = "src/main/resources/borrowBook.csv";
    private final String pathToBookCSV = "src/main/resources/bookData.csv";

    public void initialize(){
        updateRows();
    }
    public void updateRows() {
        int totalBookCount = countNonEmptyRows(pathToBookCSV);
        totalBooks.setText(String.valueOf(totalBookCount));

        int borrowedBookCount = countNonEmptyRows(pathToBorrowCSV);
        totalBorrowed.setText(String.valueOf(borrowedBookCount));

        int returnedBookCount = countRowsWithNValue(pathToBorrowCSV, 5);
        totalReturned.setText(String.valueOf(returnedBookCount));

        int booksToReturn = borrowedBookCount -returnedBookCount;
        toBeReturned.setText(String.valueOf(booksToReturn));
    }


    public int countRowsWithNValue(String csvFilePath, int n) {
        int rowCount = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                int nonEmptyCount = 0;
                for (String field : row) {
                    if (field != null && !field.trim().isEmpty()) {
                        nonEmptyCount++;
                    }
                }
                if (nonEmptyCount == n) {
                    rowCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Node node = loader.load();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int countNonEmptyRows(String csvFilePath) {
        int rowCount = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                boolean isEmpty = true;
                for (String field : row) {
                    if (field != null && !field.trim().isEmpty()) {
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) {
                    rowCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount;
    }


    public void bookMngmtAction(ActionEvent actionEvent) {
        loadFXML("BookManagement.fxml");
    }

    public void libraryReviewAction(ActionEvent actionEvent) {
        loadFXML("LibraryManagement.fxml");
    }

    public void overviewAction(ActionEvent actionEvent) {
        loadFXML("Overview.fxml");
    }
}