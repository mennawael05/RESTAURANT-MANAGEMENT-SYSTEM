package restaurant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;

public class RestaurantGUI extends Application {
    private RestaurantManager restaurantManager = new RestaurantManager();
    private User currentUser;
    private Stage primaryStage;
    private TextArea outputArea;
    private MediaPlayer mediaPlayer;
    private static final double WINDOW_WIDTH = 1550;
    private static final double WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("RESTAURANT MANAGEMENT SYSTEM");

        try {
            String musicFile = getClass().getResource("Camille_Michael_Giacchino_Le_Festin_From_Ratatouille_M4A_128K.mp3").toExternalForm();
            Media media = new Media(musicFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error loading background music: " + e.getMessage());
        }

        showWelcomeScreen();
    }

    private void showWelcomeScreen() {

        String imagePath;
        java.net.URL imageUrl = getClass().getResource("background.jpg");
        if (imageUrl != null) {
            imagePath = imageUrl.toExternalForm();
            System.out.println("Image loaded from resources: " + imagePath);
        } else {
            File imageFile = new File("background.jpg");
            imagePath = imageFile.toURI().toString();
            System.out.println("Attempting to load image from: " + imagePath);
            if (imageFile.exists()) {
                System.out.println("Image file found and will be used.");
            } else {
                System.out.println("Warning: Image file not found at: " + imageFile.getAbsolutePath());
                imagePath = null;
            }
        }

        ImageView backgroundImageView;
        if (imagePath != null) {
            backgroundImageView = new ImageView(new Image(imagePath));
            backgroundImageView.setFitWidth(WINDOW_WIDTH);
            backgroundImageView.setFitHeight(WINDOW_HEIGHT);
            backgroundImageView.setOpacity(0.8);
        } else {
            backgroundImageView = new ImageView();
            System.out.println("Background image not loaded; using empty ImageView.");
        }

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        Label title = new Label("Restaurant Management System");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Button adminButton = new Button("Admin");
        Button employeeButton = new Button("Employee");
        Button customerButton = new Button("Customer");
        Button exitButton = new Button("Exit");

        Button[] buttons = {adminButton, employeeButton, customerButton, exitButton};
        for (Button button : buttons) {
            applyButtonStyle(button);
        }

        adminButton.setOnAction(e -> showLoginRegisterScreen("Admin"));
        employeeButton.setOnAction(e -> showLoginRegisterScreen("Employee"));
        customerButton.setOnAction(e -> showLoginRegisterScreen("Customer"));
        exitButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.stop();
            primaryStage.close();
        });

        layout.getChildren().addAll(title, adminButton, employeeButton, customerButton, exitButton);

        StackPane rootPane = new StackPane(backgroundImageView, layout);
        rootPane.setStyle("-fx-background-color: #333333;");

        Scene scene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void applyButtonStyle(Button button) {
        button.setStyle("-fx-background-color: #ffffffcc; -fx-text-fill: #333333; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 30px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #444444; -fx-border-width: 1px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; -fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 30px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #ffffffcc; -fx-border-width: 1px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ffffffcc; -fx-text-fill: #333333; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 30px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #444444; -fx-border-width: 1px;"));
    }

    private void showLoginRegisterScreen(String role) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        Label title = new Label(role + " Portal");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Button loginButton = new Button("Log In");
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        applyButtonStyle(loginButton);
        applyButtonStyle(registerButton);
        applyButtonStyle(backButton);

        loginButton.setOnAction(e -> {
            System.out.println("Login button clicked for role: " + role);
            showLoginDialog(role);
        });
        registerButton.setOnAction(e -> {
            System.out.println("Register button clicked for role: " + role);
            showRegisterDialog(role);
        });
        backButton.setOnAction(e -> showWelcomeScreen());

        layout.getChildren().addAll(title, loginButton, registerButton, backButton);

        String imagePath;
        java.net.URL imageUrl = getClass().getResource("image2.jpg");
        if (imageUrl != null) {
            imagePath = imageUrl.toExternalForm();
            System.out.println("Image loaded from resources: " + imagePath);
        } else {
            File imageFile = new File("image2.jpg");
            imagePath = imageFile.toURI().toString();
            System.out.println("Attempting to load image from: " + imagePath);
            if (imageFile.exists()) {
                System.out.println("Image file found and will be used.");
            } else {
                System.out.println("Warning: Image file not found at: " + imageFile.getAbsolutePath());
                imagePath = "";
            }
        }

        if (!imagePath.isEmpty()) {
            layout.setStyle("-fx-background-image: url('" + imagePath + "'); " +
                    "-fx-background-size: cover; " +
                    "-fx-background-position: center; " +
                    "-fx-background-repeat: no-repeat; " +
                    "-fx-background-color: #333333;");
        } else {
            layout.setStyle("-fx-background-color: #333333;");
        }

        Scene scene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
    }

    private void showLoginDialog(String role) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Login as " + role);
        dialog.setHeaderText("Enter your credentials");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Button loginButton = (Button) dialog.getDialogPane().lookupButton(loginButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(loginButton);
        applyButtonStyle(cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Username and password cannot be empty.");
                    return null;
                }

                Task<User> loginTask = new Task<>() {
                    @Override
                    protected User call() {
                        try {
                            return UserModule.login(new Scanner(username + "\n" + password), role);
                        } catch (Exception e) {
                            String errorMessage = "An error occurred during login: " + e.getClass().getSimpleName() + " - " + e.getMessage();
                            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Login Error", errorMessage));
                            return null;
                        }
                    }
                };

                loginTask.setOnSucceeded(event -> {
                    User user = loginTask.getValue();
                    if (user != null) {
                        currentUser = user;
                        showMenuScreen(role);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials or user not found.");
                    }
                });

                loginTask.setOnFailed(event -> {
                    showAlert(Alert.AlertType.ERROR, "Login Error", "An unexpected error occurred during login.");
                });

                new Thread(loginTask).start();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showRegisterDialog(String role) {
        System.out.println("Opening Register Dialog for role: " + role);
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Register as " + role);
        dialog.setHeaderText("Enter registration details");

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID (" + getIdRange(role) + ")");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField positionField = new TextField();
        positionField.setPromptText("Position (required for Employee, optional for Admin)");
        if (role.equals("Customer")) {
            positionField.setDisable(true);
        }

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(new Label("Position:"), 0, 5);
        grid.add(positionField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        Button registerButton = (Button) dialog.getDialogPane().lookupButton(registerButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(registerButton);
        applyButtonStyle(cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String username = usernameField.getText().trim();
                    String password = passwordField.getText().trim();
                    String email = emailField.getText().trim();
                    String phone = phoneField.getText().trim();
                    String position = positionField.getText().trim();

                    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Username, password, email, and phone cannot be empty.");
                        return null;
                    }
                    if (role.equals("Employee") && position.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Position is required for Employee.");
                        return null;
                    }

                    int minId = role.equals("Admin") ? 1 : role.equals("Employee") ? 100 : 200;
                    int maxId = role.equals("Admin") ? 100 : role.equals("Employee") ? 200 : 300;
                    if (id < minId || id > maxId) {
                        showAlert(Alert.AlertType.ERROR, "Invalid ID", "ID must be between " + minId + " and " + maxId + ".");
                        return null;
                    }

                    String input;
                    if (role.equals("Customer")) {
                        input = id + "\n" + username + "\n" + password + "\n" + email + "\n" + phone;
                    } else {
                        input = id + "\n" + username + "\n" + password + "\n" + email + "\n" + phone + "\n" + position;
                    }

                    String[] lines = input.split("\n");
                    int expectedLines = role.equals("Customer") ? 5 : 6;
                    System.out.println("Debug Input: " + input.replace("\n", " | "));
                    System.out.println("Number of lines: " + lines.length + ", Expected: " + expectedLines);

                    if (lines.length != expectedLines) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Expected " + expectedLines + " lines, but got " + lines.length);
                        return null;
                    }

                    Task<Boolean> registerTask = new Task<>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try (Scanner sc = new Scanner(input)) {
                                System.out.println("Starting registration for role: " + role);
                                if (role.equals("Customer")) {
                                    restaurantManager.addCustomer(sc);
                                    System.out.println("Customer registration completed");
                                } else if (role.equals("Employee")) {
                                    restaurantManager.addEmployee(sc);
                                    System.out.println("Employee registration completed");
                                } else if (role.equals("Admin")) {

                                    restaurantManager.addAdmin(sc);
                                    System.out.println("Admin registration completed");
                                }
                                return true;
                            } catch (Exception e) {
                                System.out.println("Error during registration: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                                throw e;
                            }
                        }
                    };

                    registerTask.setOnSucceeded(event -> {
                        Boolean success = registerTask.getValue();
                        if (success != null && success) {
                            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", role + " registered successfully!");
                        }
                    });

                    registerTask.setOnFailed(event -> {
                        Throwable exception = registerTask.getException();
                        String errorMessage = "An unexpected error occurred during registration: " +
                                (exception != null ? exception.getClass().getSimpleName() + " - " + exception.getMessage() : "Unknown error");
                        showAlert(Alert.AlertType.ERROR, "Registration Error", errorMessage);
                    });

                    new Thread(registerTask).start();

                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a valid number.");
                } catch (Exception e) {
                    System.out.println("Unexpected error in register dialog: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                    showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }private void showMenuScreen(String role) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));


        Pane outputPane = new Pane();
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-font-size: 15px; -fx-background-color: transparent;");
        outputArea.setPrefWidth(650);
        outputArea.setPrefHeight(750);
        outputArea.setMaxWidth(650);
        outputArea.setMaxHeight(750);
        outputPane.getChildren().add(outputArea);

        outputArea.setTranslateX(1);
        layout.setCenter(outputPane);

        VBox menu = new VBox(15);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px;");

        Label title = new Label(role + " Menu");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 1);");

        menu.getChildren().add(title);

        if (role.equals("Admin")) {
            addAdminMenuButtons(menu);
        } else if (role.equals("Employee")) {
            addEmployeeMenuButtons(menu);
        } else {
            addCustomerMenuButtons(menu);
        }

        Button logoutButton = new Button("Log Out");
        applyButtonStyle(logoutButton);
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setOnAction(e -> {
            currentUser.logout();
            currentUser = null;
            showWelcomeScreen();
        });
        menu.getChildren().add(logoutButton);

        ScrollPane scrollPane = new ScrollPane(menu);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        layout.setLeft(scrollPane);

        String imagePath;
        java.net.URL imageUrl = getClass().getResource("image4.jpg");
        if (imageUrl != null) {
            imagePath = imageUrl.toExternalForm();
            System.out.println("Image loaded from resources: " + imagePath);
        } else {
            File imageFile = new File("image4.jpg");
            imagePath = imageFile.toURI().toString();
            System.out.println("Attempting to load image from: " + imagePath);
            if (imageFile.exists()) {
                System.out.println("Image file found and will be used.");
            } else {
                System.out.println("Warning: Image file not found at: " + imageFile.getAbsolutePath());
                imagePath = "";
            }
        }

        if (!imagePath.isEmpty()) {
            layout.setStyle("-fx-background-image: url('" + imagePath + "'); " +
                    "-fx-background-size: cover; " +
                    "-fx-background-position: center; " +
                    "-fx-background-repeat: no-repeat; " +
                    "-fx-background-color: rgba(0, 0, 0, 1);");
        } else {
            layout.setStyle("-fx-background-color: #333333;");
        }

        Scene scene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
    }


    private void addAdminMenuButtons(VBox menu) {
        String[] actions = {
                "Manage Customers", "Manage Employees", "Add Meal", "Delete Meal", "Update Meal",
                "Search Meal", "List Meals", "Add Special Offer", "List Special Offers",
                "Add Loyalty Program", "List Loyalty Programs", "Generate Reports", "List Orders",
                "Update Profile"
        };
        for (String action : actions) {
            Button button = new Button(action);
            applyButtonStyle(button);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> handleAdminAction(action));
            menu.getChildren().add(button);
        }
    }

    private void addEmployeeMenuButtons(VBox menu) {
        String[] actions = {
                "Manage Customers", "Manage Bills", "Make Order", "Cancel Order", "Send Gift",
                "List Offers", "Generate Reports", "Add Special Offer", "Add Loyalty Program",
                "List Special Offers", "List Loyalty Programs", "Update Profile", "Employee Report",
                "List Orders"
        };
        for (String action : actions) {
            Button button = new Button(action);
            applyButtonStyle(button);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> handleEmployeeAction(action));
            menu.getChildren().add(button);
        }
    }

    private void addCustomerMenuButtons(VBox menu) {
        String[] actions = {"View Profile", "Make Order", "View Special Offers", "View Loyalty Programs"};
        for (String action : actions) {
            Button button = new Button(action);
            applyButtonStyle(button);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> handleCustomerAction(action));
            menu.getChildren().add(button);
        }
    }

    private void handleAdminAction(String action) {
        switch (action) {
            case "Manage Customers":
                showManageCustomersDialog();
                break;
            case "Manage Employees":
                showManageEmployeesDialog();
                break;
            case "Add Meal":
                showAddMealDialog();
                break;
            case "Delete Meal":
                showDeleteMealDialog();
                break;
            case "Update Meal":
                showUpdateMealDialog();
                break;
            case "Search Meal":
                showSearchMealDialog();
                break;
            case "List Meals":
                captureOutput(() -> restaurantManager.listMeals());
                break;
            case "Add Special Offer":
                showAddSpecialOfferDialog();
                break;
            case "List Special Offers":
                captureOutput(() -> restaurantManager.listSpecialOffers());
                break;
            case "Add Loyalty Program":
                showAddLoyaltyProgramDialog();
                break;
            case "List Loyalty Programs":
                captureOutput(() -> restaurantManager.listLoyaltyPrograms());
                break;
            case "Generate Reports":
                captureOutput(() -> {
                    restaurantManager.generateCustomerReport();
                    restaurantManager.generateEmployeeReport();
                    restaurantManager.listOrders();
                });
                break;
            case "List Orders":
                captureOutput(() -> restaurantManager.listOrders());
                break;
            case "Update Profile":
                showUpdateProfileDialog();
                break;
        }
    }

    private void handleEmployeeAction(String action) {
        switch (action) {
            case "Manage Customers":
                showManageCustomersDialog();
                break;
            case "Manage Bills":
                showAddBillDialog();
                break;
            case "Make Order":
                showMakeOrderDialog(currentUser.getRole());
                break;
            case "Cancel Order":
                showCancelOrderDialog();
                break;
            case "Send Gift":
                showSendGiftDialog();
                break;
            case "List Offers":
                captureOutput(() -> restaurantManager.listOffers());
                break;
            case "Generate Reports":
                captureOutput(() -> {
                    restaurantManager.generateCustomerReport();
                    restaurantManager.generateEmployeeReport();
                });
                break;
            case "Add Special Offer":
                showAddSpecialOfferDialog();
                break;
            case "Add Loyalty Program":
                showAddLoyaltyProgramDialog();
                break;
            case "List Special Offers":
                captureOutput(() -> restaurantManager.listSpecialOffers());
                break;
            case "List Loyalty Programs":
                captureOutput(() -> restaurantManager.listLoyaltyPrograms());
                break;
            case "Update Profile":
                showUpdateProfileDialog();
                break;
            case "Employee Report":
                captureOutput(() -> restaurantManager.generateEmployeeReport());
                break;
            case "List Orders":
                captureOutput(() -> restaurantManager.listOrders());
                break;
        }
    }

    private void handleCustomerAction(String action) {
        switch (action) {
            case "View Profile":
                captureOutput(() -> {
                    try {
                        java.lang.reflect.Method method = Main.class.getDeclaredMethod("viewCustomerProfile", int.class);
                        method.setAccessible(true);
                        method.invoke(null, currentUser.getId());
                    } catch (Exception e) {
                        outputArea.setText("Error accessing profile: " + e.getMessage());
                    }
                });
                break;
            case "Make Order":
                showMakeOrderDialog(currentUser.getRole());
                break;
            case "View Special Offers":
                captureOutput(() -> restaurantManager.listSpecialOffers());
                break;
            case "View Loyalty Programs":
                captureOutput(() -> restaurantManager.listLoyaltyPrograms());
                break;
        }
    }

    private void showManageCustomersDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Customers");
        dialog.setHeaderText("Select an action");

        ButtonType listButton = new ButtonType("List Customers", ButtonBar.ButtonData.OK_DONE);
        ButtonType addButton = new ButtonType("Add Customer", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButton = new ButtonType("Delete Customer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(listButton, addButton, deleteButton, ButtonType.CANCEL);

        Button listBtn = (Button) dialog.getDialogPane().lookupButton(listButton);
        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button deleteBtn = (Button) dialog.getDialogPane().lookupButton(deleteButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(listBtn);
        applyButtonStyle(addBtn);
        applyButtonStyle(deleteBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == listButton) {
                captureOutput(() -> restaurantManager.listCustomers());
            } else if (dialogButton == addButton) {
                showAddCustomerDialog();
            } else if (dialogButton == deleteButton) {
                showDeleteCustomerDialog();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showManageEmployeesDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Employees");
        dialog.setHeaderText("Select an action");

        ButtonType listButton = new ButtonType("List Employees", ButtonBar.ButtonData.OK_DONE);
        ButtonType addButton = new ButtonType("Add Employee", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButton = new ButtonType("Delete Employee", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(listButton, addButton, deleteButton, ButtonType.CANCEL);

        Button listBtn = (Button) dialog.getDialogPane().lookupButton(listButton);
        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button deleteBtn = (Button) dialog.getDialogPane().lookupButton(deleteButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(listBtn);
        applyButtonStyle(addBtn);
        applyButtonStyle(deleteBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == listButton) {
                captureOutput(() -> restaurantManager.listEmployees());
            } else if (dialogButton == addButton) {
                showAddEmployeeDialog();
            } else if (dialogButton == deleteButton) {
                showDeleteEmployeeDialog();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAddCustomerDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Customer");
        dialog.setHeaderText("Enter customer details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID (200-300)");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String username = usernameField.getText().trim();
                    String password = passwordField.getText().trim();
                    String email = emailField.getText().trim();
                    String phone = phoneField.getText().trim();

                    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Username, password, email, and phone cannot be empty.");
                        return null;
                    }

                    if (id < 200 || id > 300) {
                        showAlert(Alert.AlertType.ERROR, "Invalid ID", "ID must be between 200 and 300.");
                        return null;
                    }

                    String input = id + "\n" + username + "\n" + password + "\n" + email + "\n" + phone;
                    String[] lines = input.split("\n");
                    System.out.println("Debug Input: " + input.replace("\n", " | "));
                    System.out.println("Number of lines: " + lines.length);

                    Task<Void> addTask = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            try (Scanner sc = new Scanner(input)) {
                                restaurantManager.addCustomer(sc);
                                Platform.runLater(() -> outputArea.setText("Customer added successfully!"));
                            } catch (Exception e) {
                                String errorMessage = "Error adding customer: " + e.getClass().getSimpleName() + " - " + e.getMessage();
                                Platform.runLater(() -> outputArea.setText(errorMessage));
                                throw e;
                            }
                            return null;
                        }
                    };

                    addTask.setOnFailed(event -> {
                        Throwable exception = addTask.getException();
                        Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", "Failed to add customer: " + exception.getMessage()));
                    });

                    new Thread(addTask).start();

                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a valid number.");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showDeleteCustomerDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Customer");
        dialog.setHeaderText("Enter customer ID to delete (200-300)");
        dialog.setContentText("Customer ID:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(okButton);
        applyButtonStyle(cancelButton);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> captureOutput(() -> restaurantManager.deleteCustomer(new Scanner(id))));
    }

    private void showAddEmployeeDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");
        dialog.setHeaderText("Enter employee details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID (100-200)");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField positionField = new TextField();
        positionField.setPromptText("Position");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(new Label("Position:"), 0, 5);
        grid.add(positionField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String input = id + "\n" + usernameField.getText().trim() + "\n" +
                            passwordField.getText().trim() + "\n" + emailField.getText().trim() + "\n" +
                            phoneField.getText().trim() + "\n" + positionField.getText().trim();
                    captureOutput(() -> restaurantManager.addEmployee(new Scanner(input)));
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a valid number.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showDeleteEmployeeDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Employee");
        dialog.setHeaderText("Enter employee ID to delete (100-200)");
        dialog.setContentText("Employee ID:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(okButton);
        applyButtonStyle(cancelButton);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> captureOutput(() -> restaurantManager.deleteEmployee(new Scanner(id))));
    }

    private void showAddMealDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Meal");
        dialog.setHeaderText("Enter meal details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Meal Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        grid.add(new Label("Meal Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Price:"), 0, 1);
        grid.add(priceField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String input = nameField.getText().trim() + "\n" + priceField.getText().trim();
                captureOutput(() -> restaurantManager.addMeal(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showDeleteMealDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Meal");
        dialog.setHeaderText("Enter meal name to delete");
        dialog.setContentText("Meal Name:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(okButton);
        applyButtonStyle(cancelButton);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> captureOutput(() -> restaurantManager.deleteMeal(new Scanner(name))));
    }

    private void showUpdateMealDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Meal");
        dialog.setHeaderText("Enter meal details");

        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Meal Name");
        TextField priceField = new TextField();
        priceField.setPromptText("New Price");

        grid.add(new Label("Meal Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("New Price:"), 0, 1);
        grid.add(priceField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Button updateBtn = (Button) dialog.getDialogPane().lookupButton(updateButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(updateBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                String input = nameField.getText().trim() + "\n" + priceField.getText().trim();
                captureOutput(() -> restaurantManager.updateMeal(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showSearchMealDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Meal");
        dialog.setHeaderText("Enter meal name to search");
        dialog.setContentText("Meal Name:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(okButton);
        applyButtonStyle(cancelButton);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Meal meal = restaurantManager.searchMealByName(name);
            outputArea.setText(meal != null ? "Found: " + meal : "Meal not found.");
        });
    }

    private void showAddSpecialOfferDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Special Offer");
        dialog.setHeaderText("Enter special offer details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("Offer ID");
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (200-300)");
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        grid.add(new Label("Offer ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Customer ID:"), 0, 1);
        grid.add(customerIdField, 1, 1);
        grid.add(new Label("Title:"), 0, 2);
        grid.add(titleField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String input = idField.getText().trim() + "\n" + customerIdField.getText().trim() + "\n" +
                        titleField.getText().trim() + "\n" + descriptionField.getText().trim();
                captureOutput(() -> restaurantManager.addSpecialOffer(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAddLoyaltyProgramDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Loyalty Program");
        dialog.setHeaderText("Enter loyalty program details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("Program ID");
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (200-300)");
        TextField programField = new TextField();
        programField.setPromptText("Program Name");

        grid.add(new Label("Program ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Customer ID:"), 0, 1);
        grid.add(customerIdField, 1, 1);
        grid.add(new Label("Program Name:"), 0, 2);
        grid.add(programField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String input = idField.getText().trim() + "\n" + customerIdField.getText().trim() + "\n" +
                        programField.getText().trim();
                captureOutput(() -> restaurantManager.addLoyaltyProgram(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAddBillDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Bill");
        dialog.setHeaderText("Enter bill details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("Bill ID");
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (200-300)");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        grid.add(new Label("Bill ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Customer ID:"), 0, 1);
        grid.add(customerIdField, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(addBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String input = idField.getText().trim() + "\n" + customerIdField.getText().trim() + "\n" +
                        amountField.getText().trim();
                captureOutput(() -> restaurantManager.addBill(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showMakeOrderDialog(String role) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Make Order");
        dialog.setHeaderText("Enter order details");

        ButtonType orderButton = new ButtonType("Place Order", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(orderButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (200-300)");
        if (role.equals("Customer")) {
            customerIdField.setText(String.valueOf(currentUser.getId()));
            customerIdField.setDisable(true);
        }
        TextField mealNameField = new TextField();
        mealNameField.setPromptText("Meal Name");
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Order ID");

        grid.add(new Label("Customer ID:"), 0, 0);
        grid.add(customerIdField, 1, 0);
        grid.add(new Label("Meal Name:"), 0, 1);
        grid.add(mealNameField, 1, 1);
        grid.add(new Label("Order ID:"), 0, 2);
        grid.add(orderIdField, 1, 2);

        Button listMealsButton = new Button("List Meals");
        applyButtonStyle(listMealsButton);
        listMealsButton.setOnAction(e -> captureOutput(() -> restaurantManager.listMeals()));
        grid.add(listMealsButton, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Button orderBtn = (Button) dialog.getDialogPane().lookupButton(orderButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(orderBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == orderButton) {
                String input = mealNameField.getText().trim() + "\n" + orderIdField.getText().trim();
                int customerId;
                try {
                    customerId = Integer.parseInt(customerIdField.getText().trim());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Customer ID must be a valid number.");
                    return null;
                }
                captureOutput(() -> restaurantManager.makeOrder(new Scanner(input), customerId, role));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showCancelOrderDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cancel Order");
        dialog.setHeaderText("Enter order ID to cancel");
        dialog.setContentText("Order ID:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(okButton);
        applyButtonStyle(cancelButton);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> captureOutput(() -> restaurantManager.cancelOrder(new Scanner(id))));
    }

    private void showSendGiftDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Send Gift");
        dialog.setHeaderText("Enter gift details");

        ButtonType sendButton = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (200-300)");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Gift Description");

        grid.add(new Label("Customer ID:"), 0, 0);
        grid.add(customerIdField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Button sendBtn = (Button) dialog.getDialogPane().lookupButton(sendButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(sendBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButton) {
                String input = customerIdField.getText().trim() + "\n" + descriptionField.getText().trim();
                captureOutput(() -> restaurantManager.sendGift(new Scanner(input)));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showUpdateProfileDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Profile");
        dialog.setHeaderText("Enter new profile details (leave blank to keep current)");

        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Current: " + currentUser.getUsername());
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Current: " + currentUser.getPassword());
        TextField emailField = new TextField();
        emailField.setPromptText("Current: " + currentUser.getEmail());
        TextField phoneField = new TextField();
        phoneField.setPromptText("Current: " + currentUser.getPhone());
        TextField positionField = new TextField();
        positionField.setPromptText("Current: " + currentUser.getPosition());
        if (currentUser.getRole().equals("Customer")) {
            positionField.setDisable(true);
        }

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Position:"), 0, 4);
        grid.add(positionField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Button updateBtn = (Button) dialog.getDialogPane().lookupButton(updateButton);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        applyButtonStyle(updateBtn);
        applyButtonStyle(cancelBtn);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                String input = usernameField.getText().trim() + "\n" + passwordField.getText().trim() + "\n" +
                        emailField.getText().trim() + "\n" + phoneField.getText().trim() + "\n" +
                        positionField.getText().trim();
                captureOutput(() -> UserModule.updateProfile(new Scanner(input), currentUser));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void captureOutput(Runnable action) {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        try {
            action.run();
            outputArea.setText(outContent.toString());
        } catch (Exception e) {
            outputArea.setText("Error occurred: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        applyButtonStyle(okButton);

        alert.showAndWait();
    }

    private String getIdRange(String role) {
        if (role.equals("Admin")) return "1-100";
        if (role.equals("Employee")) return "100-200";
        return "200-300";
    }

    public static void main(String[] args) {
        launch(args);
    }
}