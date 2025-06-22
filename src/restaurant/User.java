package restaurant;

import java.util.List;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String position;

    public User(int id, String username, String password, String email, String phone, String role, String position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.position = position;
    }

    public abstract void login();
    public abstract void logout();

    public void updateProfile(String username, String password, String email, String phone, String position) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.position = position;
        updateUserFile();
    }

    protected void updateUserFile() {
        List<String> users = FileHelper.readFromFile("users.txt");
        for (int i = 0; i < users.size(); i++) {
            String[] parts = users.get(i).split(",");
            if (parts.length >= 1 && Integer.parseInt(parts[0]) == id) {
                users.set(i, id + "," + username + "," + password + "," + email + "," + phone + "," + role + "," + position);
                break;
            }
        }
        FileHelper.overwriteFile("users.txt", users);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public String getPosition() { return position; }
}