package tech.acodesigner.entity;

/**
 * Created by 77239 on 2017/3/28/0028.
 */

public class User {
    private int userId;
    private int userType;
    private String username;
    private String password;
    private int imageId;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int imageId) {
        this.username = username;
        this.password = password;
        this.imageId = imageId;
    }

    public User(int userId, String username, String password,  int imageId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
