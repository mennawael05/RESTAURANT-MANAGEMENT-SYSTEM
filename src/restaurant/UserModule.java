package restaurant;

import java.util.List;
import java.util.Scanner;

public class UserModule {
    public static User login(Scanner sc, String role) {
        System.out.print("Enter " + role + " username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter " + role + " password: ");
        String password = sc.nextLine().trim();

        List<String> users = FileHelper.readFromFile("users.txt");
        if (users.isEmpty()) {
            System.out.println("❌ No users found in users.txt. Please register first.");
            return null;
        }

        for (String user : users) {
            String[] parts = user.split(",");

            if (parts.length < 6) {
                System.out.println("⚠️ Skipping invalid user entry in users.txt: " + user);
                continue;
            }

            String storedUsername = parts[1].trim();
            String storedPassword = parts[2].trim();
            String storedRole = parts[5].trim();
            int id;
            try {
                id = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid ID in users.txt entry: " + user);
                continue;
            }
            String email = parts[3].trim();
            String phone = parts[4].trim();
            String position = parts.length > 6 ? parts[6].trim() : "none";


            if (storedRole.equals(role) && storedUsername.equals(username) && storedPassword.equals(password)) {
                System.out.println("✅ " + role + " logged in successfully with ID: " + id + "!");
                return new User(id, storedUsername, storedPassword, email, phone, storedRole, position) {
                    @Override
                    public void login() {
                        System.out.println(role + " " + getUsername() + " (ID: " + getId() + ") logged in successfully.");
                    }

                    @Override
                    public void logout() {
                        if (!isValidId(getId(), role)) {
                            System.out.println("Invalid " + role + " ID: " + getId() + ". Logout failed.");
                            return;
                        }
                        System.out.println(role + " " + getUsername() + " (ID: " + getId() + ") logged out successfully.");
                    }
                };
            } else if (storedRole.equals(role) && storedUsername.equals(username) && !storedPassword.equals(password)) {
                System.out.println("❌ Incorrect password for username: " + username);
                return null;
            }
        }
        System.out.println("❌ No user found with username: " + username + " for role: " + role);
        return null;
    }

    public static void updateProfile(Scanner sc, User user) {
        System.out.print("Enter new username (" + user.getUsername() + "): ");
        String username = sc.nextLine().trim();
        System.out.print("Enter new password (" + user.getPassword() + "): ");
        String password = sc.nextLine().trim();
        if (!password.isEmpty() && isPasswordUsed(password)) {
            System.out.println("❌ This password is already used. Choose a different one.");
            return;
        }
        System.out.print("Enter new email (" + user.getEmail() + "): ");
        String email = sc.nextLine().trim();
        System.out.print("Enter new phone (" + user.getPhone() + "): ");
        String phone = sc.nextLine().trim();
        System.out.print("Enter new position (" + user.getPosition() + "): ");
        String position = sc.nextLine().trim();
        if (position.isEmpty()) {
            position = "none";
        }

        user.updateProfile(
                username.isEmpty() ? user.getUsername() : username,
                password.isEmpty() ? user.getPassword() : password,
                email.isEmpty() ? user.getEmail() : email,
                phone.isEmpty() ? user.getPhone() : phone,
                position.isEmpty() ? user.getPosition() : position
        );
        System.out.println("Profile updated successfully!");
    }

    public static boolean isValidId(int id, String role) {
        List<String> users = FileHelper.readFromFile("users.txt");
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 6 && Integer.parseInt(parts[0].trim()) == id && parts[5].trim().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswordUsed(String password) {
        List<String> users = FileHelper.readFromFile("users.txt");
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 3 && parts[2].trim().equals(password)) {
                return true;
            }
        }
        return false;
    }
}