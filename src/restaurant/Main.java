package restaurant;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final RestaurantManager restaurantManager = new RestaurantManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Restaurant Management System ---");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = getValidChoice(1, 4);

            switch (choice) {
                case 1:
                    handleUser("Admin");
                    break;
                case 2:
                    handleUser("Employee");
                    break;
                case 3:
                    handleUser("Customer");
                    break;
                case 4:
                    System.out.println("Exiting the system...");
                    sc.close();
                    return;
            }
        }
    }

    private static int getValidChoice(int min, int max) {
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

    private static void handleUser(String role) {
        while (true) {
            System.out.println("\n--- " + role + " ---");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            int choice = getValidChoice(1, 3);

            switch (choice) {
                case 1:
                    User user = UserModule.login(sc, role);
                    if (user != null) {
                        showMenu(user);
                    }
                    break;
                case 2:
                    registerUser(role);
                    break;
                case 3:
                    return;
            }
        }
    }

    private static void registerUser(String role) {
        int minId = 0, maxId = 0;
        if (role.equals("Admin")) {
            minId = 1;
            maxId = 100;
        } else if (role.equals("Employee")) {
            minId = 100;
            maxId = 200;
        } else if (role.equals("Customer")) {
            minId = 200;
            maxId = 300;
        }

        System.out.print("Enter " + role + " ID (" + minId + " to " + maxId + "): ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
            if (id < minId || id > maxId) {
                System.out.println("❌ Invalid ID. It must be between " + minId + " and " + maxId + ".");
                return;
            }
            if (UserModule.isValidId(id, role)) {
                System.out.println("❌ ID " + id + " is already in use. Try another one.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Enter a valid number.");
            return;
        }

        System.out.print("Enter username: ");
        final String username = sc.nextLine().trim();
        if (!Validation.isAlpha(username)) {
            System.out.println("❌ Username must contain only letters.");
            return;
        }

        System.out.print("Enter password: ");
        final String password = sc.nextLine().trim();
        if (UserModule.isPasswordUsed(password)) {
            System.out.println("❌ This password is already used. Choose a different one.");
            return;
        }

        System.out.print("Enter email: ");
        final String email = sc.nextLine().trim();
        System.out.print("Enter phone: ");
        final String phone = sc.nextLine().trim();

        String position = "none";
        if (!role.equals("Customer")) {
            System.out.print("Enter position: ");
            position = sc.nextLine().trim();
            if (position.isEmpty()) {
                position = "none";
            }
        }
        final String finalPosition = position;

        final String userData = id + "," + username + "," + password + "," + email + "," + phone + "," + role + "," + finalPosition;
        FileHelper.appendToFile("users.txt", userData);


        List<String> users = FileHelper.readFromFile("users.txt");
        final String expectedUserData = userData;
        boolean saved = users.stream().anyMatch(user -> user.equals(expectedUserData));
        if (!saved) {
            System.out.println("❌ Error: User data was not saved to users.txt. Check file permissions or path.");
            return;
        } else {
            System.out.println("✅ User data saved for ID: " + id + ", Username: " + username);
        }

        if (role.equals("Customer")) {
            final String customerData = id + "," + username + "," + phone + "," + email;
            FileHelper.appendToFile("customers.txt", customerData);

            List<String> customers = FileHelper.readFromFile("customers.txt");
            final String expectedCustomerData = customerData;
            saved = customers.stream().anyMatch(customer -> customer.equals(expectedCustomerData));
            if (!saved) {
                System.out.println("❌ Error: Customer data was not saved to customers.txt.");
                return;
            }
        }

        System.out.println("✅ " + role + " registered successfully with ID: " + id + "! You can now log in with username: " + username + " and your password.");
    }

    private static void showMenu(User user) {
        String role = user.getRole();
        while (true) {
            System.out.println("\n--- " + role + " Menu ---");
            if (role.equals("Admin")) {
                System.out.println("1. Manage Customers");
                System.out.println("2. Manage Employees");
                System.out.println("3. Add Meal");
                System.out.println("4. Delete Meal");
                System.out.println("5. Update Meal");
                System.out.println("6. Search Meal");
                System.out.println("7. List Meals");
                System.out.println("8. Add Special Offer");
                System.out.println("9. List Special Offers");
                System.out.println("10. Add Loyalty Program");
                System.out.println("11. List Loyalty Programs");
                System.out.println("12. Generate Reports");
                System.out.println("13. List Orders");
                System.out.println("14. Update Profile");
                System.out.println("15. Log Out");
            } else if (role.equals("Employee")) {
                System.out.println("1. Manage Customers");
                System.out.println("2. Manage Bills");
                System.out.println("3. Make Order");
                System.out.println("4. Cancel Order");
                System.out.println("5. Send Gift");
                System.out.println("6. List Offers");
                System.out.println("7. Generate Reports");
                System.out.println("8. Add Special Offer");
                System.out.println("9. Add Loyalty Program");
                System.out.println("10. List Special Offers");
                System.out.println("11. List Loyalty Programs");
                System.out.println("12. Update Profile");
                System.out.println("13. Employee Report");
                System.out.println("14. List Orders");
                System.out.println("15. Log Out");
            } else if (role.equals("Customer")) {
                System.out.println("1. View Profile");
                System.out.println("2. Make Order");
                System.out.println("3. View Special Offers");
                System.out.println("4. View Loyalty Programs");
                System.out.println("5. Log Out");
            }

            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Enter a valid number.");
                continue;
            }

            if (role.equals("Admin")) {
                switch (choice) {
                    case 1:
                        restaurantManager.manageCustomers(sc);
                        break;
                    case 2:
                        restaurantManager.manageEmployees(sc);
                        break;
                    case 3:
                        restaurantManager.addMeal(sc);
                        break;
                    case 4:
                        restaurantManager.deleteMeal(sc);
                        break;
                    case 5:
                        restaurantManager.updateMeal(sc);
                        break;
                    case 6:
                        System.out.print("Enter meal name to search: ");
                        String mealName = sc.nextLine().trim();
                        Meal meal = restaurantManager.searchMealByName(mealName);
                        System.out.println(meal != null ? "Found: " + meal : "Meal not found.");
                        break;
                    case 7:
                        restaurantManager.listMeals();
                        break;
                    case 8:
                        restaurantManager.addSpecialOffer(sc);
                        break;
                    case 9:
                        restaurantManager.listSpecialOffers();
                        break;
                    case 10:
                        restaurantManager.addLoyaltyProgram(sc);
                        break;
                    case 11:
                        restaurantManager.listLoyaltyPrograms();
                        break;
                    case 12:
                        restaurantManager.generateCustomerReport();
                        restaurantManager.generateEmployeeReport();
                        restaurantManager.listOrders();
                        break;
                    case 13:
                        restaurantManager.listOrders();
                        break;
                    case 14:
                        UserModule.updateProfile(sc, user);
                        break;
                    case 15:
                        user.logout();
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Enter a number between 1 and 15.");
                }
            } else if (role.equals("Employee")) {
                switch (choice) {
                    case 1:
                        restaurantManager.manageCustomers(sc);
                        break;
                    case 2:
                        restaurantManager.addBill(sc);
                        break;
                    case 3:
                        System.out.print("Enter customer ID (200-300): ");
                        int customerId;
                        try {
                            customerId = Integer.parseInt(sc.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Invalid input. Enter a valid number.");
                            break;
                        }
                        restaurantManager.makeOrder(sc, customerId, role);
                        break;
                    case 4:
                        restaurantManager.cancelOrder(sc);
                        break;
                    case 5:
                        restaurantManager.sendGift(sc);
                        break;
                    case 6:
                        restaurantManager.listOffers();
                        break;
                    case 7:
                        restaurantManager.generateCustomerReport();
                        restaurantManager.generateEmployeeReport();
                        break;
                    case 8:
                        restaurantManager.addSpecialOffer(sc);
                        break;
                    case 9:
                        restaurantManager.addLoyaltyProgram(sc);
                        break;
                    case 10:
                        restaurantManager.listSpecialOffers();
                        break;
                    case 11:
                        restaurantManager.listLoyaltyPrograms();
                        break;
                    case 12:
                        UserModule.updateProfile(sc, user);
                        break;
                    case 13:
                        restaurantManager.generateEmployeeReport();
                        break;
                    case 14:
                        restaurantManager.listOrders();
                        break;
                    case 15:
                        user.logout();
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Enter a number between 1 and 15.");
                }
            } else if (role.equals("Customer")) {
                switch (choice) {
                    case 1:
                        viewCustomerProfile(user.getId());
                        break;
                    case 2:
                        restaurantManager.makeOrder(sc, user.getId(), role);
                        break;
                    case 3:
                        restaurantManager.listSpecialOffers();
                        break;
                    case 4:
                        restaurantManager.listLoyaltyPrograms();
                        break;
                    case 5:
                        user.logout();
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Enter a number between 1 and 5.");
                }
            }
        }
    }

    private static void viewCustomerProfile(int customerId) {
        System.out.println("\n--- Your Profile ---");
        List<String> customers = FileHelper.readFromFile("customers.txt");
        boolean found = false;
        for (String customer : customers) {
            String[] parts = customer.split(",");
            if (parts.length >= 4 && Integer.parseInt(parts[0]) == customerId) {
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Phone: " + parts[2] + ", Email: " + parts[3]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("❌ Customer profile not found.");
            return;
        }

        System.out.println("\n--- Your Orders ---");
        List<String> orders = FileHelper.readFromFile("orders.txt");
        boolean hasOrders = false;
        double totalBill = 0.0;
        for (String order : orders) {
            String[] parts = order.split(",");
            if (parts.length >= 4) {
                try {
                    int orderCustomerId = Integer.parseInt(parts[1].trim());
                    double amount = Double.parseDouble(parts[3].trim());
                    if (orderCustomerId == customerId) {
                        System.out.println("Order ID: " + parts[0] + ", Details: " + parts[2] + ", Amount: $" + amount);
                        hasOrders = true;
                        totalBill += amount;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid order entry: " + order);
                }
            }
        }
        if (!hasOrders) {
            System.out.println("No orders found.");
        }

        System.out.println("\n--- Your Payments ---");
        if (hasOrders) {
            System.out.printf("Total Paid: $%.2f%n", totalBill);
        } else {
            System.out.println("No payments recorded.");
        }

        System.out.println("\n--- Your Bills ---");
        List<String> bills = FileHelper.readFromFile("bills.txt");
        boolean hasBills = false;
        double totalBills = 0.0;
        for (String bill : bills) {
            String[] parts = bill.split(",");
            if (parts.length >= 3) {
                try {
                    int billCustomerId = Integer.parseInt(parts[1].trim());
                    double amount = Double.parseDouble(parts[2].trim());
                    if (billCustomerId == customerId) {
                        System.out.println("Bill ID: " + parts[0] + ", Amount: $" + amount);
                        hasBills = true;
                        totalBills += amount;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid bill entry: " + bill);
                }
            }
        }
        if (!hasBills) {
            System.out.println("No bills found.");
        } else {
            System.out.printf("Total Bills: $%.2f%n", totalBills);
        }

        System.out.println("\n--- Your Special Offers ---");
        List<String> offers = FileHelper.readFromFile("special_offers.txt");
        boolean hasOffers = false;
        for (String offer : offers) {
            String[] parts = offer.split(",");
            if (parts.length >= 4) {
                try {
                    int offerCustomerId = Integer.parseInt(parts[1].trim());
                    if (offerCustomerId == customerId) {
                        System.out.println("Offer ID: " + parts[0] + ", Title: " + parts[2] + ", Description: " + parts[3]);
                        hasOffers = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid special offer entry: " + offer);
                }
            }
        }
        if (!hasOffers) {
            System.out.println("No special offers available.");
        }

        System.out.println("\n--- Your Loyalty Programs ---");
        List<String> programs = FileHelper.readFromFile("loyalty_programs.txt");
        boolean hasPrograms = false;
        for (String program : programs) {
            String[] parts = program.split(",");
            if (parts.length >= 3) {
                try {
                    int programCustomerId = Integer.parseInt(parts[1].trim());
                    if (programCustomerId == customerId) {
                        System.out.println("Program ID: " + parts[0] + ", Program: " + parts[2]);
                        hasPrograms = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid loyalty program entry: " + program);
                }
            }
        }
        if (!hasPrograms) {
            System.out.println("No loyalty programs available.");
        }

        System.out.println("\n--- Your Gifts ---");
        List<String> gifts = FileHelper.readFromFile("gifts.txt");
        boolean hasGifts = false;
        for (String gift : gifts) {
            String[] parts = gift.split(",");
            if (parts.length >= 3) {
                try {
                    int giftCustomerId = Integer.parseInt(parts[0].trim());
                    if (giftCustomerId == customerId) {
                        System.out.println("Gift: " + parts[1] + ", Received: " + parts[2]);
                        hasGifts = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid gift entry: " + gift);
                }
            }
        }
        if (!hasGifts) {
            System.out.println("No gifts received.");
        }
    }
}