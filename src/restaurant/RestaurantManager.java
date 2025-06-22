package restaurant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantManager {
    private static final String USERS_FILE = "users.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String EMPLOYEES_FILE = "employees.txt";
    private static final String MEALS_FILE = "meals.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final String BILLS_FILE = "bills.txt";
    private static final String OFFERS_FILE = "offers.txt";
    private static final String SPECIAL_OFFERS_FILE = "special_offers.txt";
    private static final String LOYALTY_FILE = "loyalty_programs.txt";
    private static final String GIFTS_FILE = "gifts.txt";

    public void manageCustomers(Scanner sc) {
        while (true) {
            System.out.println("\n--- Manage Customers ---");
            System.out.println("1. List Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Back to Menu");
            System.out.print("Choose an option: ");
            int choice = getValidChoice(sc, 1, 4);

            switch (choice) {
                case 1:
                    listCustomers();
                    break;
                case 2:
                    addCustomer(sc);
                    break;
                case 3:
                    deleteCustomer(sc);
                    break;
                case 4:
                    return;
            }
        }
    }

    public void manageEmployees(Scanner sc) {
        while (true) {
            System.out.println("\n--- Manage Employees ---");
            System.out.println("1. List Employees");
            System.out.println("2. Add Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Back to Menu");
            System.out.print("Choose an option: ");
            int choice = getValidChoice(sc, 1, 4);

            switch (choice) {
                case 1:
                    listEmployees();
                    break;
                case 2:
                    addEmployee(sc);
                    break;
                case 3:
                    deleteEmployee(sc);
                    break;
                case 4:
                    return;
            }
        }
    }

    public void addCustomer(Scanner sc) {
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id < 200 || id > 300) {
                System.out.println("❌ Invalid ID. It must be between 200 and 300.");
                return;
            }
            if (UserModule.isValidId(id, "Customer")) {
                System.out.println("❌ ID " + id + " is already in use.");
                return;
            }

            String username = sc.nextLine().trim();
            if (!Validation.isAlpha(username)) {
                System.out.println("❌ Name must contain only letters.");
                return;
            }

            String password = sc.nextLine().trim();
            if (UserModule.isPasswordUsed(password)) {
                System.out.println("❌ This password is already used.");
                return;
            }

            String email = sc.nextLine().trim();
            String phone = sc.nextLine().trim();

            String userData = id + "," + username + "," + password + "," + email + "," + phone + ",Customer,none";
            String customerData = id + "," + username + "," + phone + "," + email;
            FileHelper.appendToFile(USERS_FILE, userData);
            FileHelper.appendToFile(CUSTOMERS_FILE, customerData);
            Notification.sendNotification("New customer added: " + username);
            System.out.println("✅ Customer added successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error adding customer: " + e.getMessage());
        }
    }

    public void deleteCustomer(Scanner sc) {
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id < 200 || id > 300) {
                System.out.println("❌ Invalid ID. It must be between 200 and 300.");
                return;
            }
            if (!UserModule.isValidId(id, "Customer")) {
                System.out.println("❌ Customer ID " + id + " not found.");
                return;
            }

            List<String> users = FileHelper.readFromFile(USERS_FILE);
            boolean userRemoved = users.removeIf(line -> line.startsWith(id + ",") && line.contains(",Customer,"));
            List<String> customers = FileHelper.readFromFile(CUSTOMERS_FILE);
            boolean customerRemoved = customers.removeIf(line -> line.startsWith(id + ","));

            if (userRemoved && customerRemoved) {
                FileHelper.overwriteFile(USERS_FILE, users);
                FileHelper.overwriteFile(CUSTOMERS_FILE, customers);
                Notification.sendNotification("Customer ID " + id + " deleted.");
                System.out.println("✅ Customer deleted successfully.");
            } else {
                System.out.println("❌ Error deleting customer.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting customer: " + e.getMessage());
        }
    }

    public void listCustomers() {
        List<String> customers = FileHelper.readFromFile(CUSTOMERS_FILE);
        if (customers.isEmpty()) {
            System.out.println("❗ No customers found.");
        } else {
            System.out.println("--- Customer List ---");
            for (String customer : customers) {
                String[] parts = customer.split(",");
                if (parts.length >= 4) {
                    System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Phone: " + parts[2] + ", Email: " + parts[3]);
                }
            }
        }
    }

    public void addEmployee(Scanner sc) {
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id < 100 || id > 200) {
                System.out.println("❌ Invalid ID. It must be between 100 and 200.");
                return;
            }
            if (UserModule.isValidId(id, "Employee")) {
                System.out.println("❌ ID " + id + " is already in use.");
                return;
            }

            String username = sc.nextLine().trim();
            if (!Validation.isAlpha(username)) {
                System.out.println("❌ Name must contain only letters.");
                return;
            }

            String password = sc.nextLine().trim();
            if (UserModule.isPasswordUsed(password)) {
                System.out.println("❌ This password is already used.");
                return;
            }

            String email = sc.nextLine().trim();
            String phone = sc.nextLine().trim();
            String position = sc.nextLine().trim();
            if (position.isEmpty()) position = "none";

            String userData = id + "," + username + "," + password + "," + email + "," + phone + ",Employee," + position;
            FileHelper.appendToFile(USERS_FILE, userData);
            Notification.sendNotification("New employee added: " + username);
            System.out.println("✅ Employee added successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error adding employee: " + e.getMessage());
        }
    }

    public void deleteEmployee(Scanner sc) {
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id < 100 || id > 200) {
                System.out.println("❌ Invalid ID. It must be between 100 and 200.");
                return;
            }
            if (!UserModule.isValidId(id, "Employee")) {
                System.out.println("❌ Employee ID " + id + " not found.");
                return;
            }

            List<String> users = FileHelper.readFromFile(USERS_FILE);
            boolean removed = users.removeIf(line -> line.startsWith(id + ",") && line.contains(",Employee,"));
            if (removed) {
                FileHelper.overwriteFile(USERS_FILE, users);
                Notification.sendNotification("Employee ID " + id + " deleted.");
                System.out.println("✅ Employee deleted successfully.");
            } else {
                System.out.println("❌ Error deleting employee.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting employee: " + e.getMessage());
        }
    }

    public void listEmployees() {
        List<String> users = FileHelper.readFromFile(USERS_FILE);
        System.out.println("--- Employee List ---");
        boolean found = false;
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 7 && parts[5].equals("Employee")) {
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Position: " + parts[6]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("❗ No employees found.");
        }
    }


    public void addAdmin(Scanner sc) {
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (id < 1 || id > 100) {
                System.out.println("❌ Invalid ID. It must be between 1 and 100.");
                return;
            }
            if (UserModule.isValidId(id, "Admin")) {
                System.out.println("❌ ID " + id + " is already in use.");
                return;
            }

            String username = sc.nextLine().trim();
            if (!Validation.isAlpha(username)) {
                System.out.println("❌ Name must contain only letters.");
                return;
            }

            String password = sc.nextLine().trim();
            if (UserModule.isPasswordUsed(password)) {
                System.out.println("❌ This password is already used.");
                return;
            }

            String email = sc.nextLine().trim();
            String phone = sc.nextLine().trim();
            String position = sc.nextLine().trim();
            if (position.isEmpty()) position = "none";

            String userData = id + "," + username + "," + password + "," + email + "," + phone + ",Admin," + position;
            FileHelper.appendToFile(USERS_FILE, userData);
            Notification.sendNotification("New admin added: " + username);
            System.out.println("✅ Admin added successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error adding admin: " + e.getMessage());
        }
    }

    public void addMeal(Scanner sc) {
        try {
            String name = sc.nextLine().trim();
            double price = Double.parseDouble(sc.nextLine().trim());
            String mealData = name + "," + price;
            FileHelper.appendToFile(MEALS_FILE, mealData);
            Notification.sendNotification("New meal added: " + name);
            System.out.println("✅ Meal added: " + name + " with price $" + price);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price. Enter a valid number.");
        } catch (Exception e) {
            System.out.println("❌ Error adding meal: " + e.getMessage());
        }
    }

    public void deleteMeal(Scanner sc) {
        try {
            String name = sc.nextLine().trim();
            List<String> meals = FileHelper.readFromFile(MEALS_FILE);
            boolean removed = meals.removeIf(line -> line.startsWith(name + ","));
            if (removed) {
                FileHelper.overwriteFile(MEALS_FILE, meals);
                Notification.sendNotification("Meal deleted: " + name);
                System.out.println("✅ Meal deleted: " + name);
            } else {
                System.out.println("❌ Meal not found: " + name);
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting meal: " + e.getMessage());
        }
    }

    public void updateMeal(Scanner sc) {
        try {
            String name = sc.nextLine().trim();
            double price = Double.parseDouble(sc.nextLine().trim());
            List<String> meals = FileHelper.readFromFile(MEALS_FILE);
            boolean updated = false;
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).startsWith(name + ",")) {
                    meals.set(i, name + "," + price);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                FileHelper.overwriteFile(MEALS_FILE, meals);
                Notification.sendNotification("Meal updated: " + name);
                System.out.println("✅ Meal updated: " + name + " with price $" + price);
            } else {
                System.out.println("❌ Meal not found: " + name);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price. Enter a valid number.");
        } catch (Exception e) {
            System.out.println("❌ Error updating meal: " + e.getMessage());
        }
    }

    public Meal searchMealByName(String name) {
        List<String> meals = FileHelper.readFromFile(MEALS_FILE);
        for (String line : meals) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[0].equals(name)) {
                try {
                    return new Meal(parts[0], Double.parseDouble(parts[1]));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid meal entry: " + line);
                }
            }
        }
        return null;
    }

    public void listMeals() {
        List<String> meals = FileHelper.readFromFile(MEALS_FILE);
        if (meals.isEmpty()) {
            System.out.println("❗ No meals found.");
        } else {
            System.out.println("--- Available Meals ---");
            for (String line : meals) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        System.out.println("Meal: " + parts[0] + ", Price: $" + Double.parseDouble(parts[1]));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Skipping invalid meal entry: " + line);
                    }
                }
            }
        }
    }

    public void makeOrder(Scanner sc, int customerId, String role) {
        try {
            if (!UserModule.isValidId(customerId, "Customer")) {
                System.out.println("❌ Invalid customer ID: " + customerId + ". It must belong to a registered customer (200-300).");
                return;
            }

            String mealName = sc.nextLine().trim();
            Meal selectedMeal = searchMealByName(mealName);
            if (selectedMeal == null) {
                System.out.println("❌ Meal not found: " + mealName);
                return;
            }

            String orderId = sc.nextLine().trim();
            if (!Validation.isIdUnique(ORDERS_FILE, orderId)) {
                System.out.println("❌ Order ID already exists: " + orderId);
                return;
            }

            String orderDetails = "Meal: " + selectedMeal.getName();
            double amount = selectedMeal.getPrice();
            String orderData = orderId + "," + customerId + "," + orderDetails + "," + amount;
            FileHelper.appendToFile(ORDERS_FILE, orderData);

            List<String> orders = FileHelper.readFromFile(ORDERS_FILE);
            if (!orders.contains(orderData)) {
                System.out.println("❌ Error: Order data was not saved to orders.txt. Check file permissions or path.");
                return;
            }

            Notification.sendNotification("New order placed for customer ID: " + customerId);
            System.out.println("✅ Order placed successfully: " + orderDetails + ", Amount: $" + amount);
            notifyAdminAndEmployee(customerId, orderData);
        } catch (Exception e) {
            System.out.println("❌ Error placing order: " + e.getMessage());
        }
    }

    public void cancelOrder(Scanner sc) {
        try {
            String orderId = sc.nextLine().trim();
            List<String> orders = FileHelper.readFromFile(ORDERS_FILE);
            boolean removed = orders.removeIf(line -> line.startsWith(orderId + ","));
            if (removed) {
                FileHelper.overwriteFile(ORDERS_FILE, orders);
                Notification.sendNotification("Order canceled: " + orderId);
                System.out.println("✅ Order canceled successfully!");
            } else {
                System.out.println("❌ Order not found: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("❌ Error canceling order: " + e.getMessage());
        }
    }

    public void listOrders() {
        List<String> orders = FileHelper.readFromFile(ORDERS_FILE);
        if (orders.isEmpty()) {
            System.out.println("❗ No orders found.");
        } else {
            System.out.println("--- All Orders ---");
            for (String order : orders) {
                String[] parts = order.split(",");
                if (parts.length >= 4) {
                    try {
                        System.out.println("Order ID: " + parts[0] + ", Customer ID: " + parts[1] + ", Details: " + parts[2] + ", Amount: $" + Double.parseDouble(parts[3]));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Skipping invalid order entry: " + order);
                    }
                }
            }
        }
    }

    public void addBill(Scanner sc) {
        try {
            String id = sc.nextLine().trim();
            if (!Validation.isIdUnique(BILLS_FILE, id)) {
                System.out.println("❌ Bill ID already exists: " + id);
                return;
            }

            int customerId = Integer.parseInt(sc.nextLine().trim());
            if (customerId < 200 || customerId > 300 || !UserModule.isValidId(customerId, "Customer")) {
                System.out.println("❌ Invalid customer ID. It must be between 200 and 300 and registered.");
                return;
            }

            double amount = Double.parseDouble(sc.nextLine().trim());
            String billData = id + "," + customerId + "," + amount;
            FileHelper.appendToFile(BILLS_FILE, billData);

            List<String> bills = FileHelper.readFromFile(BILLS_FILE);
            if (!bills.contains(billData)) {
                System.out.println("❌ Error: Bill data was not saved to bills.txt. Check file permissions or path.");
                return;
            }

            Notification.sendNotification("New bill added for customer ID: " + customerId);
            System.out.println("✅ Bill added successfully for customer ID: " + customerId);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format. Enter valid numbers for ID and amount.");
        } catch (Exception e) {
            System.out.println("❌ Error adding bill: " + e.getMessage());
        }
    }

    public void viewBills() {
        List<String> bills = FileHelper.readFromFile(BILLS_FILE);
        if (bills.isEmpty()) {
            System.out.println("❗ No bills found.");
        } else {
            System.out.println("=== Bill List ===");
            for (String bill : bills) {
                String[] parts = bill.split(",");
                if (parts.length >= 3) {
                    try {
                        System.out.println("Bill ID: " + parts[0] + ", Customer ID: " + parts[1] + ", Amount: $" + Double.parseDouble(parts[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Skipping invalid bill entry: " + bill);
                    }
                }
            }
        }
    }

    public void addOffer(Scanner sc) {
        try {
            String id = sc.nextLine().trim();
            if (!Validation.isIdUnique(OFFERS_FILE, id)) {
                System.out.println("❌ Offer ID already exists: " + id);
                return;
            }

            String title = sc.nextLine().trim();
            String description = sc.nextLine().trim();
            String data = id + "," + title + "," + description;
            FileHelper.appendToFile(OFFERS_FILE, data);
            Notification.sendNotification("New offer added: " + title);
            System.out.println("✅ Offer added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error adding offer: " + e.getMessage());
        }
    }

    public void listOffers() {
        List<String> offers = FileHelper.readFromFile(OFFERS_FILE);
        if (offers.isEmpty()) {
            System.out.println("❗ No offers available.");
        } else {
            System.out.println("--- Current Offers ---");
            for (String offer : offers) {
                String[] parts = offer.split(",");
                if (parts.length >= 3) {
                    System.out.println("Offer ID: " + parts[0] + ", Title: " + parts[1] + ", Description: " + parts[2]);
                }
            }
        }
    }

    public void addSpecialOffer(Scanner sc) {
        try {
            String id = sc.nextLine().trim();
            if (!Validation.isIdUnique(SPECIAL_OFFERS_FILE, id)) {
                System.out.println("❌ Offer ID already exists: " + id);
                return;
            }

            int customerId = Integer.parseInt(sc.nextLine().trim());
            if (customerId < 200 || customerId > 300 || !UserModule.isValidId(customerId, "Customer")) {
                System.out.println("❌ Invalid customer ID. It must be between 200 and 300 and registered.");
                return;
            }

            String title = sc.nextLine().trim();
            String description = sc.nextLine().trim();
            String offer = id + "," + customerId + "," + title + "," + description;
            FileHelper.appendToFile(SPECIAL_OFFERS_FILE, offer);
            Notification.sendNotification("New special offer added for customer ID: " + customerId);
            System.out.println("✅ Special offer added successfully for customer ID: " + customerId);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid customer ID. Enter a valid number.");
        } catch (Exception e) {
            System.out.println("❌ Error adding special offer: " + e.getMessage());
        }
    }

    public void listSpecialOffers() {
        List<String> offers = FileHelper.readFromFile(SPECIAL_OFFERS_FILE);
        if (offers.isEmpty()) {
            System.out.println("❗ No special offers available.");
        } else {
            System.out.println("--- Special Offers ---");
            for (String offer : offers) {
                String[] parts = offer.split(",");
                if (parts.length >= 4) {
                    try {
                        System.out.println("Offer ID: " + parts[0] + ", Customer ID: " + parts[1] + ", Title: " + parts[2] + ", Description: " + parts[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Skipping invalid special offer entry: " + offer);
                    }
                }
            }
        }
    }

    public void addLoyaltyProgram(Scanner sc) {
        try {
            String id = sc.nextLine().trim();
            if (!Validation.isIdUnique(LOYALTY_FILE, id)) {
                System.out.println("❌ Program ID already exists: " + id);
                return;
            }

            int customerId = Integer.parseInt(sc.nextLine().trim());
            if (customerId < 200 || customerId > 300 || !UserModule.isValidId(customerId, "Customer")) {
                System.out.println("❌ Invalid customer ID. It must be between 200 and 300 and registered.");
                return;
            }

            String program = sc.nextLine().trim();
            String loyaltyData = id + "," + customerId + "," + program;
            FileHelper.appendToFile(LOYALTY_FILE, loyaltyData);
            Notification.sendNotification("New loyalty program added for customer ID: " + customerId);
            System.out.println("✅ Loyalty program added successfully for customer ID: " + customerId);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid customer ID. Enter a valid number.");
        } catch (Exception e) {
            System.out.println("❌ Error adding loyalty program: " + e.getMessage());
        }
    }

    public void listLoyaltyPrograms() {
        List<String> programs = FileHelper.readFromFile(LOYALTY_FILE);
        if (programs.isEmpty()) {
            System.out.println("❗ No loyalty programs available.");
        } else {
            System.out.println("--- Loyalty Programs ---");
            for (String program : programs) {
                String[] parts = program.split(",");
                if (parts.length >= 3) {
                    try {
                        System.out.println("Program ID: " + parts[0] + ", Customer ID: " + parts[1] + ", Program: " + parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Skipping invalid loyalty program entry: " + program);
                    }
                }
            }
        }
    }

    public void sendGift(Scanner sc) {
        try {
            int customerId = Integer.parseInt(sc.nextLine().trim());
            if (customerId < 200 || customerId > 300 || !UserModule.isValidId(customerId, "Customer")) {
                System.out.println("❌ Invalid customer ID. It must be between 200 and 300 and registered.");
                return;
            }

            String description = sc.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("❌ Gift description cannot be empty.");
                return;
            }

            String giftData = customerId + "," + description + "," + getCurrentDateTime();
            FileHelper.appendToFile(GIFTS_FILE, giftData);

            List<String> gifts = FileHelper.readFromFile(GIFTS_FILE);
            if (!gifts.contains(giftData)) {
                System.out.println("❌ Error: Gift data was not saved to gifts.txt. Check file permissions or path.");
                return;
            }

            Notification.sendNotification("Gift sent to customer ID: " + customerId);
            System.out.println("✅ Gift sent successfully: " + description + " to customer ID: " + customerId);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid customer ID. Enter a valid number.");
        } catch (Exception e) {
            System.out.println("❌ Error sending gift: " + e.getMessage());
        }
    }

    public void generateCustomerReport() {
        System.out.println("=== Customer Report ===");
        listCustomers();
    }

    public void generateEmployeeReport() {
        System.out.println("=== Employee Report ===");
        listEmployees();
    }

    private void notifyAdminAndEmployee(int customerId, String orderData) {
        List<String> users = FileHelper.readFromFile(USERS_FILE);
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 6 && (parts[5].equals("Admin") || parts[5].equals("Employee"))) {
                Notification.sendNotification("New order for customer ID " + customerId + ": " + orderData);
            }
        }
    }

    private int getValidChoice(Scanner sc, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("❌ Invalid choice. Enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Enter a valid number.");
            }
        }
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}