package com.example.dataform;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class ApplicationFormFX extends Application {

    // List to store submitted data
    private ObservableList<String[]> submittedData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Person Application Form");

        // Create a colorful banner
        StackPane bannerPane = new StackPane();
        bannerPane.setStyle("-fx-background-color: #4CAF50; -fx-padding: 20px;");

        Text bannerText = new Text("Application Form");
        bannerText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        bannerText.setFill(Color.WHITE);
        bannerPane.getChildren().add(bannerText);

        // Form elements
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(20));
        formGrid.setHgap(15);
        formGrid.setVgap(15);
        formGrid.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 5px;");

        // Name
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nameField = new TextField();
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);

        // Father's Name
        Label fatherNameLabel = new Label("Father's Name:");
        fatherNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField fatherNameField = new TextField();
        formGrid.add(fatherNameLabel, 0, 1);
        formGrid.add(fatherNameField, 1, 1);

        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField emailField = new TextField();
        formGrid.add(emailLabel, 0, 2);
        formGrid.add(emailField, 1, 2);

        // City
        Label cityLabel = new Label("City:");
        cityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField cityField = new TextField();
        formGrid.add(cityLabel, 0, 3);
        formGrid.add(cityField, 1, 3);

        // Address
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextArea addressArea = new TextArea();
        addressArea.setPrefRowCount(3);
        formGrid.add(addressLabel, 0, 4);
        formGrid.add(addressArea, 1, 4);

        // Image Upload
        Label imageLabel = new Label("Upload Image:");
        imageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Button uploadButton = new Button("Choose File");
        uploadButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-border-color: black;");

        HBox imageBox = new HBox(10, uploadButton, imageView);
        formGrid.add(imageLabel, 0, 5);
        formGrid.add(imageBox, 1, 5);

        // File chooser for image upload
        FileChooser fileChooser = new FileChooser();
        uploadButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });

        // Gender
        Label genderLabel = new Label("Gender:");
        genderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        maleButton.setToggleGroup(genderGroup);
        femaleButton.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(10, maleButton, femaleButton);
        formGrid.add(genderLabel, 0, 6);
        formGrid.add(genderBox, 1, 6);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setPrefWidth(100);

        VBox mainLayout = new VBox(10, bannerPane, formGrid, submitButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #e8f5e9;");

        // Submit button action
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String email = emailField.getText();
            String city = cityField.getText();
            String address = addressArea.getText();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "Not Selected");

            if (name.isEmpty() || fatherName.isEmpty() || email.isEmpty() || city.isEmpty() || address.isEmpty() || gender.equals("Not Selected")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
                alert.showAndWait();
                return;
            }

            // Store data
            submittedData.add(new String[]{name, fatherName, email, city, address, gender});

            // Show submitted data in new window
            showDataScreen();
        });

        // Set the scene
        Scene scene = new Scene(mainLayout, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDataScreen() {
        Stage dataStage = new Stage();
        dataStage.setTitle("Submitted Data");

        VBox dataLayout = new VBox(10);
        dataLayout.setPadding(new Insets(20));
        dataLayout.setStyle("-fx-background-color: #ffffff;");

        Label dataLabel = new Label("Submitted Data:");
        dataLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        dataLayout.getChildren().add(dataLabel);

        for (String[] entry : submittedData) {
            String entryString = String.format(
                    "Name: %s\nFather's Name: %s\nEmail: %s\nCity: %s\nAddress: %s\nGender: %s\n",
                    entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]
            );
            Label entryLabel = new Label(entryString);
            entryLabel.setStyle("-fx-border-color: gray; -fx-padding: 10px;");
            dataLayout.getChildren().add(entryLabel);
        }

        Scene dataScene = new Scene(dataLayout, 400, 400);
        dataStage.setScene(dataScene);
        dataStage.show();
    }
}
